package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.model.*;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.GlucoseRepresentation;
import com.pfizer.sacchon.representation.NoteRepresentation;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.logging.Logger;
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
        if (results.isEmpty())
            return false;
        else if (results.get(0).getId() == patient_id)
            return true;
        else
            return false;
    }


    public boolean isFreePatient(long patient_id) {
        Patient patient = (Patient) entityManager.createQuery(
                "from Patient where doctor_id is null and patient_id = :id").
                setParameter("id",patient_id).getSingleResult();
        if (!patient.equals(null))
            return true;
        return false;
    }


    public void doctorAccessPatientData(Long patient_id, Doctor doctor) throws NotAuthorizedException {
        if (!isYourPatient(patient_id, doctor.getId()) || !isFreePatient(patient_id))
            throw new NotAuthorizedException("Not Authorized Doctor");
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
     * Deleting a doctor from the Db
     *
     * @param id The doctor to be deleted
     * @return True if deleting has been completed, else false
     */
    public boolean removeDoctor(Long id) {
        Optional<Doctor> oDoctor = findDoctorById(id);
        if (oDoctor.isPresent()) {
            Doctor doctor = oDoctor.get();
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(doctor);
                entityManager.getTransaction().commit();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
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

}

