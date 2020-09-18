package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.resource.PatientResourceImpl;
import org.restlet.engine.Engine;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class PatientRepository {

    private EntityManager entityManager;

    public PatientRepository(EntityManager entityManager) { this.entityManager = entityManager; }

    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);
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
     * Save a patient into system(db)
     *
     * @param patient
     * @return empty Optional
     */
    public Optional<Patient> save(Patient patient) {
        LOGGER.info("PatientRepository/save is in");
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
        in.setDoctor(patient.getDoctor());
        in.setFirstName(patient.getFirstName());
        in.setLastName(patient.getLastName());
//        in.setUsername(patient.getUsername());
        in.setPhoneNumber(patient.getPhoneNumber());
        in.setAddress(patient.getAddress());
        in.setCity(patient.getCity());
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
     * Find all patient of the system
     * @return a list of patients
     */
    public List<Patient> findAll() {
        return entityManager.createQuery("from Patient").getResultList();
    }


    /**
     * Remove a patient-Set as Inactive
     *
     * @param id
     * @return true if db has been updated
     */
    public boolean remove(Long id) {
        Optional<Patient> opatient = findById(id);
        if (opatient.isPresent()) {
            Patient p = opatient.get();
            //    p.setIsActive(false);
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(p);
                entityManager.getTransaction().commit();


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    public Optional<Carb> findAverageCarbIntake(Date startDate, Date endDate) {
        Carb carb = entityManager.createQuery("SELECT avg(gram) FROM Carb c WHERE c.date >= :startDate AND c.date <= :endDate", Carb.class)
                .setParameter("date", startDate)
                .setParameter("date", endDate)
                .getSingleResult();
        return carb != null ? Optional.of(carb) : Optional.empty();
    }

}
