package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.*;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Set<Doctor> findDoctorsWithNoActivity(Date from, Date to){
        Set doctorsActive = findDoctors_active(to);
        Set doctorsWithActivity = findDoctorsWithActivity(from,to);
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

    public Set<Patient> findPatientsWithActivity(Date from, Date to){
        List<Patient> patientsWithActivity = entityManager.createQuery(
                "select DISTINCT p from Patient as p, Carb as c, Glucose as g where " +
                        "(p.id = g.patient and :from <= g.dateTime AND :to >= g.dateTime)" +
                        " OR ( p.id=c.patient  and  :from <= c.date AND :to >= c.date)")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();

        return new HashSet<Patient>(patientsWithActivity);
    }

    public Set<Patient> findPatientsWithNoActivity(Date from, Date to){
        Set patientsActive = findPatients_active(to);
        Set patientsWithActivity = findPatientsWithActivity(from,to);
        patientsActive.removeAll(patientsWithActivity);
        return patientsActive;
    }


    public List<Note> findDoctorNotes(Long doctor_id, Date startDate, Date endDate){
        List<Note> notes = entityManager.createQuery("select n from Note as n, Doctor d where d.isActive = 1 and d n.doctor = :id and :startDate <= n.date AND :endDate >= n.date")
                .setParameter("id",doctor_id)
                .setParameter("startDate",startDate)
                .setParameter("endDate",endDate)
                .getResultList();
        return notes;
    }

    public List<Carb> findCarbs(long patient_id,Date startDate, Date endDate) {
        List<Carb> carb = entityManager.createQuery("select c from Carb as c, Patient p where p.isActive = 1 and c.patient = :id and :startDate <= c.date AND :endDate >= c.date")
                .setParameter("id",patient_id)
                .setParameter("startDate",startDate)
                .setParameter("endDate",endDate)
                .getResultList();
        return carb;
    }

    public List<Glucose> findGlucose(long patient_id, Date startDate, Date endDate) {
        List<Glucose> glucose = entityManager.createQuery("select g from Glucose as g, Patient as p where p.isActive = 1 and g.patient = :id and :startDate <= g.dateTime AND :endDate >= g.dateTime")
                .setParameter("id",patient_id)
                .setParameter("startDate",startDate)
                .setParameter("endDate",endDate)
                .getResultList();
        return glucose;
    }


}
