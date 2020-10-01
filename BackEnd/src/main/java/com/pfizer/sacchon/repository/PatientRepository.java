package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Patient;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PatientRepository {
    private EntityManager entityManager;


    public PatientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Search for patient with specific id
     *
     * @param id
     * @return Patient as Optional
     */
    public Optional<Patient> findById(Long id) {
        Patient patient = entityManager.find(Patient.class, id);
        return patient != null ? Optional.of(patient) : Optional.empty();
    }

    /**
     * Find a Patient by his Username
     * @param username
     * @return  Optional Patient
     */
    public Optional<Patient> findPatientByUsername(String username) {
        Patient patient = (Patient) entityManager.createQuery(
                "from Patient p where :username = p.username").setParameter("username", username).getSingleResult();
        return patient != null ? Optional.of(patient) : Optional.empty();
    }

    /**
     * Search for patient with specific doctor_Id
     *
     * @param doctor_Id
     * @return Patient as Optional
     */
    public Optional<Patient> findByDoctor(int doctor_Id) {
        Patient patient = entityManager.find(Patient.class, doctor_Id);
        return patient != null ? Optional.of(patient) : Optional.empty();
    }



    /**
     * Save a patient into system (db)
     *
     * @param patient
     * @return Optional of Patient or Empty
     */
    public Optional<Patient> save(Patient patient) {
        patient.setCreationDate(new Date());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patient);
            entityManager.getTransaction().commit();
            return Optional.of(patient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Update data of an existing Patient
     *
     * @param patient
     * @return boolean true if database has been updated, else false
     */
    public boolean updatePatient(Patient patient) {

        Patient in = entityManager.find(Patient.class, patient.getId());
        if (patient.getFirstName() != null)
            in.setFirstName(patient.getFirstName());

        if (patient.getLastName() != null)
            in.setLastName(patient.getLastName());

        if (patient.getPhoneNumber() != null)
            in.setPhoneNumber(patient.getPhoneNumber());

        if (patient.getAddress() != null)
            in.setAddress(patient.getAddress());

        if (patient.getDateOfBirth() != null)
            in.setDateOfBirth(patient.getDateOfBirth());

        if (patient.getDiabetesType() != null)
            in.setDiabetesType(patient.getDiabetesType());

        if (patient.getDoctor() != null)
            in.setDoctor(patient.getDoctor());

        if (patient.getZipCode() != null)
            in.setZipCode(patient.getZipCode());

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(in);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Setting a Patient as inactive from the system
     * @param patient
     * @return
     */
    public boolean removePatient(Patient patient) {
        Patient in = entityManager.find(Patient.class, patient.getId());
        in.setActive(false);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(in);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }


}

