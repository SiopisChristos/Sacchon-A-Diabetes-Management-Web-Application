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

    public Set<Doctor> findDoctors_active(Date to) {
        List<Doctor> doctors = entityManager.createQuery(
                "select DISTINCT d from Doctor d where isActive = 1 and creationDate <= :to")
                .setParameter("to", to)
                .getResultList();
        return new HashSet<Doctor>(doctors);
    }

    public Set<Doctor> findDoctorsWithActivity(Date from, Date to) {
        List<Doctor> doctorWithActivity = entityManager.createQuery(
                "select DISTINCT d  from Doctor as d, Note as n where d.id = n.doctor and ( :from <= n.date) AND (:to >= n.date)")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
        return new HashSet<Doctor>(doctorWithActivity);
    }

    public Set<Doctor> findDoctorsWithNoActivity(Date from, Date to) {
        Set doctorsActive = findDoctors_active(to);
        Set doctorsWithActivity = findDoctorsWithActivity(from, to);
        doctorsActive.removeAll(doctorsWithActivity);
        return doctorsActive;
    }

    public Set<Patient> findPatients_active(Date to) {
        List<Patient> patients = entityManager.createQuery(
                "select DISTINCT p from Patient p where isActive = 1 and creationDate <= :to")
                .setParameter("to", to)
                .getResultList();
        return new HashSet<Patient>(patients);
    }

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

    public Set<Patient> findPatientsWithNoActivity(Date from, Date to) {
        Set patientsActive = findPatients_active(to);
        Set patientsWithActivity = findPatientsWithActivity(from, to);
        patientsActive.removeAll(patientsWithActivity);
        return patientsActive;
    }


    public List<Note> findDoctorNotes(Long doctor_id, Date startDate, Date endDate) {
        List<Note> notes = entityManager.createQuery("select n from Note as n, Doctor d where d.isActive = 1 and d n.doctor = :id and :startDate <= n.date AND :endDate >= n.date")
                .setParameter("id", doctor_id)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        return notes;
    }

    public List<Carb> findCarbs(Patient patient, Date startDate, Date endDate) {
        List<Carb> carb = entityManager.createQuery("select c from Carb as c, Patient p where p.isActive = 1 and c.patient = :id and :startDate <= c.date AND :endDate >= c.date")
                .setParameter("id", patient)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        return carb;
    }

    public List<Glucose> findGlucose(Patient patient, Date startDate, Date endDate) {
        List<Glucose> glucose = entityManager.createQuery("select g from Glucose as g, Patient as p where p.isActive = 1 and g.patient = :id and :startDate <= g.dateTime AND :endDate >= g.dateTime")
                .setParameter("id", patient)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        return glucose;
    }

    public Date findLastNoteDate(Patient patient) {
        Date lastNoteDate = (Date) entityManager.createQuery("select MAX(date) from Note n where n.patient = :patient")
                .setParameter("patient", patient)
                .getSingleResult();
        return lastNoteDate;
    }

    /**
     * Finding the Patients that wait for a consultation and their waiting time
     * @return a List with with 2 List inside it. The List structure is: {Patients, countDaysOfPatient}
     * The countDaysOfPatient is a List of Long data
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
