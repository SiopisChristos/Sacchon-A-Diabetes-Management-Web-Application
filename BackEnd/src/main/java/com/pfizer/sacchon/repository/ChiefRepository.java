package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.*;
import com.pfizer.sacchon.repository.util.DateConverter;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class ChiefRepository {

    private EntityManager entityManager;

    public ChiefRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Finds the entities of Doctors who are not deleted
     * We define that the Doctors that have creationDate bigger than the Date parameter to, are not
     * included in the Set
     * @param to
     * @return  a Set of Doctors
     */
    public Set<Doctor> findDoctors_active(Date to) {
        List<Doctor> doctors = entityManager.createQuery(
                "select DISTINCT d from Doctor d where isActive = 1 and creationDate <= :to")
                .setParameter("to", to)
                .getResultList();
        return new HashSet<Doctor>(doctors);
    }

    /**
     * Find the Doctors who have activities (== have notes) over a time range
     * @param from
     * @param to
     * @return a Set of Doctors
     */
    public Set<Doctor> findDoctorsWithActivity(Date from, Date to) {
        List<Doctor> doctorWithActivity = entityManager.createQuery(
                "select DISTINCT d  from Doctor as d, Note as n where d.id = n.doctor and ( :from <= n.date) AND (:to >= n.date)")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
        return new HashSet<Doctor>(doctorWithActivity);
    }

    /**
     * Find the Doctors who have no activities (==  have no notes) over a time range
     * @param from
     * @param to
     * @return a Set of Doctors
     */
    public Set<Doctor> findDoctorsWithNoActivity(Date from, Date to) {
        Set doctorsActive = findDoctors_active(to);
        Set doctorsWithActivity = findDoctorsWithActivity(from, to);
        doctorsActive.removeAll(doctorsWithActivity);
        return doctorsActive;
    }

    /**
     * Find the Patients that are not deleted
     * We define that the Patients that have creationDate bigger than the parameter to, are not
     * included in the Set
     * @param to
     * @return a Set of active Patients
     */
    public Set<Patient> findPatients_active(Date to) {
        List<Patient> patients = entityManager.createQuery(
                "select DISTINCT p from Patient p where isActive = 1 and creationDate <= :to")
                .setParameter("to", to)
                .getResultList();
        return new HashSet<Patient>(patients);
    }

    /**
     * Find the Patients who have activities (== have measurements) over a time range
     * @param from
     * @param to
     * @return a Set of Patients
     */
    public Set<Patient> findPatientsWithActivity(Date from, Date to) {
        List<Patient> patientsWithActivity = entityManager.createQuery(
                "select DISTINCT p from Patient as p, Carb as c, Glucose as g where " +
                        "(p.id = g.patient and :from <= g.dateTime AND :to >= g.dateTime)" +
                        " OR ( p.id=c.patient  and  :from <= c.date AND :to >= c.date)")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();

        return new HashSet<>(patientsWithActivity);
    }

    /**
     * Find the Patients who have no activity (== no measurements) over a time range
     * @param from
     * @param to
     * @return a Set of Patients
     */
    public Set<Patient> findPatientsWithNoActivity(Date from, Date to) {
        Set patientsActive = findPatients_active(to);
        Set patientsWithActivity = findPatientsWithActivity(from, to);
        patientsActive.removeAll(patientsWithActivity);
        return patientsActive;
    }

    /**
     * Finding the Notes of a Doctor over a time range
     * @param doctor
     * @param startDate
     * @param endDate
     * @return  a list of Notes
     */
    public List<Note> findDoctorNotes(Doctor doctor, Date startDate, Date endDate) {
        List<Note> notes = entityManager.createQuery("select distinct n from Note as n, Doctor d where d.isActive = 1 and n.doctor = :id and :startDate <= n.date AND :endDate >= n.date")
                .setParameter("id", doctor)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        return notes;
    }

    /**
     * Finding the carb data of a Patient over a time range
     * @param patient
     * @param startDate
     * @param endDate
     * @return  a List of Carb data
     */
    public List<Carb> findCarbs(Patient patient, Date startDate, Date endDate) {
        List<Carb> carb = entityManager.createQuery("select distinct c from Carb as c, Patient p where p.isActive = 1 and c.patient = :id and :startDate <= c.date AND :endDate >= c.date")
                .setParameter("id", patient)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        return carb;
    }

    /**
     * Finding the glucose data of a Patient over a time range
     * @param patient
     * @param startDate
     * @param endDate
     * @return  a List of Glucose data
     */
    public List<Glucose> findGlucose(Patient patient, Date startDate, Date endDate) {
        List<Glucose> glucose = entityManager.createQuery("select distinct g from Glucose as g, Patient as p where p.isActive = 1 and g.patient = :id and :startDate <= g.dateTime AND :endDate >= g.dateTime")
                .setParameter("id", patient)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        return glucose;
    }

    /**
     * Finding the date of the last note of a patient
     * @param patient
     * @return  the Date of the last note
     */
    public Date findLastNoteDate(Patient patient) {
        Date lastNoteDate = (Date) entityManager.createQuery("select MAX(date) from Note n where n.patient = :patient")
                .setParameter("patient", patient)
                .getSingleResult();
        return lastNoteDate;
    }

    /**
     * Finding the Patients that wait for a consultation and their waiting time
     * @return a List with 2 Lists inside it. The List structure is: {Patients, countDaysOfPatient}
     * The countDaysOfPatient is a List of long types
     */
    public List[] calculatePatientsDatesWithoutDoctor() {
        List<Long> countDaysOfPatient = new ArrayList<>();
        long countDays;
        List<Patient> patients = entityManager.createQuery(
                "select p from Patient p where isActive = 1 and p.doctor is null")
                .getResultList();
        List<Patient> patientsInactive = new ArrayList<>();

        for (Patient p : patients) {
            Date today = new Date();
            LocalDate dateTimeStart;
            LocalDate dateTimeEnd = today.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            ;
            Date patientCanHaveDoctor = DateConverter.nextMonthDate(p.getCreationDate());
            if (patientCanHaveDoctor.after(today)) {
                //NOT ADD THE PATIENT
            }
            else {
                patientsInactive.add(p);
                Date lastNoteDate = findLastNoteDate(p);
                if (lastNoteDate == null)
                    dateTimeStart = patientCanHaveDoctor.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                else
                    dateTimeStart = lastNoteDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                countDays = DAYS.between(dateTimeStart, dateTimeEnd);
                countDaysOfPatient.add(countDays);
            }

        }
        return new List[]{patientsInactive, countDaysOfPatient};
    }


}
