package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Note;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.model.UserTable;
import com.pfizer.sacchon.security.dao.DatabaseCredentials;
import org.restlet.Context;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.print.Doc;
import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DoctorRepository {

    private EntityManager entityManager;

    public DoctorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // TODO: 17/9/2020
    // hasRightToSee method


    /**
     * Find the patients for a specific doctor_id
     *
     * @param doctor_id id of the doctor who provide advice to patients
     * @return A list of his patients
     */
    public List<Patient> findMyPatients(long doctor_id) {
        List<Patient> results = entityManager.createQuery(
                "from Patient where :id = doctor_id").setParameter("id", doctor_id)
                .getResultList();
        return results;
    }


    public List<Patient> findFreePatients() {
        List<Patient> results = entityManager.createQuery(
                "from Patient where doctor_id is null")
                .getResultList();
        return results;
    }




    /**
     * Checks if a patient is under supervision of a specific doctor
     *
     * @param patient_id PatientId to be checked
     * @param doctor_id  DoctorId to be checked
     * @return true if it's a match, else false;
     */
    public boolean isYourPatient(long patient_id, long doctor_id) {

        List<Patient> results = findMyPatients(doctor_id);
        Set<Patient> resultsId = results.stream().filter(x -> x.getId() == patient_id).collect(Collectors.toSet());

        if (resultsId.isEmpty())
            return false;
        else
            return true;
    }


    public boolean isFreePatient(long patient_id) {
               List<Patient> patientList = entityManager.createQuery(
                "from Patient where doctor_id is null and id = :id").
                setParameter("id",patient_id).getResultList();
        if (patientList.isEmpty()) return false;
        else if (patientList.size() == 1) return true;
        return false;
    }


    public void doctorAccessPatientData(Long patient_id, Doctor doctor) throws NotAuthorizedException {
        if(!isFreePatient(patient_id)) {
            if (!isYourPatient(patient_id, doctor.getId()))
                throw new NotAuthorizedException("Not Authorized Doctor");
        }
    }


    public boolean removeDoctor(long id) {
        Doctor in = entityManager.find(Doctor.class, id);
        if (in != null) {
            in.setActive(false);
            try{
                entityManager.getTransaction().begin();
                entityManager.persist(in);
                entityManager.getTransaction().commit();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Remove a doctor account-Set it as Inactive
     *
     * @param id
     * @return true if db has been updated
     */
    public boolean removeFromSystem(Long id) {
        Optional<Doctor> odoctor = findDoctorById(id);
        if (odoctor.isPresent()) {
            Doctor d = odoctor.get();
            d.setActive(false);
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(d);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    /**
     * Select all the inactive Patients from Db
     *
     * @return a List which contains all their personal data
     */
    public List<Patient> findInactivePatients() {
        return entityManager.createQuery(
                "from Patient p where p.doctor_id IS NULL")
                .getResultList();
    }


    /**
     * Deleting a doctor from the Db UserTable
     *
     * @param user The doctor to be deleted
     * @return True if deleting has been completed, else false
     */
    public boolean removeAccount(UserTable user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserTable findAccountById(String username){
       return entityManager.find(UserTable.class,username);
    }



    /**
     * @param username
     * @return
     */
    public Optional<Doctor> findDoctorByUsername(String username) {
        Doctor doctor = (Doctor) entityManager.createQuery(
                "from Doctor d where :username = d.username").setParameter("username", username).getSingleResult();
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }


    /**
     * find doctor by id
     *
     * @param id
     * @return
     */
    public Optional<Doctor> findDoctorById(long id) {
        Doctor doctor = entityManager.find(Doctor.class, id);
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    /**
     * find a Patient record by ID
     *
     * @param patient_id
     * @return Optional of Patient or empty
     */
    public Optional<Patient> findPatientById(long patient_id) {
        Patient patient = entityManager.find(Patient.class, patient_id);
        return patient != null ? Optional.of(patient) : Optional.empty();
    }


    public boolean updatePatientDoctor(Doctor doctor, Patient patient){
        Patient p = entityManager.find(Patient.class, patient.getId());
        p.setDoctor(doctor);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(p);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}

