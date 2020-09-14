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

    public Optional<Patient> findById(Long id) {
        Patient patient = entityManager.find(Patient.class, id);
        return patient != null ? Optional.of(patient) : Optional.empty();
    }

    public List<Patient> findAll() {
        return entityManager.createQuery("from Patient").getResultList();
    }

    public Optional<Patient> findByName(String username) {
        Patient patient = entityManager.createQuery("SELECT p FROM Patient p WHERE p.username = :username", Patient.class)
                .setParameter("username", username)
                .getSingleResult();
        return patient != null ? Optional.of(patient) : Optional.empty();
    }

    public Optional<Patient> findByNameNamedQuery(String username) {
        Patient patient = entityManager.createNamedQuery("Patient.findByUserName", Patient.class)
                .setParameter("username", username)
                .getSingleResult();
        return patient != null ? Optional.of(patient) : Optional.empty();
    }


    public Optional<Patient> save(Patient patient){

        try {
            entityManager.getTransaction().begin();
            entityManager.persist (patient);
            entityManager.getTransaction().commit();
            return Optional.of(patient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public Optional<Patient> update(Patient patient) {

        Patient in = entityManager.find(Patient.class, patient.getId());
        in.setUsername(patient.getUsername());
        in.setDateOfBirth(patient.getDateOfBirth());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (in);
            entityManager.getTransaction().commit();
            return Optional.of(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
