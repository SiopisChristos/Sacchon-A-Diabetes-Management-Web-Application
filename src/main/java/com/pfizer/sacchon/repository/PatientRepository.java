package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.Patient;

import javax.persistence.EntityManager;
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
     * Search for patient with specific lastname
     *
     * @param lastname
     * @return Patient as Optional
     */
    public Optional<Patient> findByLastname(String lastname) {
        Patient patient = entityManager.find(Patient.class, lastname);
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
     * Find all patient of the system
     *
     * @return a list of patients
     */
    public List<Patient> findAll() {
        return entityManager.createQuery("from Patient").getResultList();
    }


    /**
     * Save a patient into system(db)
     *
     * @param patient
     * @return empty Optional
     */
    public Optional<Patient> save(Patient patient) {

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
     * @return boolean if database has been updated
     */
    public boolean updatePatient(Patient patient) {

        Patient in = entityManager.find(Patient.class, patient.getId());
        if (patient.getFirstName() != null) {
            in.setFirstName(patient.getFirstName());
        }
        if (patient.getLastName() != null) {
            in.setLastName(patient.getLastName());
        }
        if (patient.getUsername() != null) {
            in.setUsername(patient.getUsername());
        }
        if (patient.getPhoneNumber() != null) {
            in.setPhoneNumber(patient.getPhoneNumber());
        }
        if (patient.getAddress() != null) {
            in.setAddress(patient.getAddress());
        }
        if (patient.getDateOfBirth() != null) {
            in.setDateOfBirth(patient.getDateOfBirth());
        }
        if(patient.getDoctor()!=null){
            in.setDoctor(patient.getDoctor());
        }
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



    public boolean removePatient(long id) {
        Patient in = entityManager.find(Patient.class, id);
        if (in != null) {
            in.setActive(false);
            try {
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
     * Remove a patient account-Set it as Inactive
     *
     * @param id
     * @return true if db has been updated
     */
    public boolean removeFromSystem(Long id) {
        Optional<Patient> opatient = findById(id);
        if (opatient.isPresent()) {
            Patient p = opatient.get();
             p.setActive(false);
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(p);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
