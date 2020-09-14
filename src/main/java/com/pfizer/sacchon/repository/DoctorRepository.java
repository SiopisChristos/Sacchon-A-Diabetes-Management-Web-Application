package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.Note;
import com.pfizer.sacchon.model.Patient;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import java.util.List;

public class DoctorRepository {

    private EntityManager entityManager;

    public DoctorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // TODO: 15/9/2020
    // delete method
    // hasRightToSee method


    /**
     * Find the patients for a specific doctor_id
     *
     * @param doctor_id id of the doctor who provide advice to patients
     * @return A list of his patients
     */
    public List<Patient> findMyPatients(long doctor_id) {
        List results = entityManager.createQuery(
                "SELECT * from Patient WHERE doctor_id = :doctor_id").setParameter("doctor_id", doctor_id)
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
        if (results.isEmpty()) return false;
        else if ((long) results.get(0).getId() == patient_id) return true;
        return false;
    }


    /**
     * Returns all the patient data (Carbs & Glucose measurements) and their consultations in
     * a List structure which contains three Lists.
     * The list structure is: {Carbs, Glucose, Notes}
     *
     * @param patient_id the integer id of a specific patient
     * @return patient data and consultations
     */
    public List[] patientRecord(long patient_id) {
        List carb = entityManager.createQuery(
                "SELECT * from Carb c  WHERE :p.id = c.patient_id")
                .setParameter("p.id", patient_id).getResultList();
        List glucose = entityManager.createQuery(
                "SELECT * from Glucose g  WHERE :p.id = g.patient_id")
                .setParameter("p.id", patient_id).getResultList();
        List consultations = entityManager.createQuery(
                "SELECT * from Note n  WHERE :p.id = n.patient_id").getResultList();

        return new List[]{carb, glucose, consultations};
    }

    /**
     * Select all the inactive Patients
     *
     * @return a List which contains all their personal data
     */
    public List<Patient> findInactivePatients() {
        return entityManager.createQuery(
                "SELECT * from Patient WHERE doctor_id IS NULL")
                .getResultList();
    }


    /**
     * Saving a new note to the Db
     *
     * @param note The note to be stored
     * @return True if saving has been completed, else false
     */
    public boolean saveNote(Note note) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(note);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing note in Db
     *
     * @param note the updated note
     * @return True if updating has been completed, else false
     */
    public boolean updateNote(Note note) {
        Note in = entityManager.find(Note.class, note.getId());
        in.setMessage(note.getMessage());
        in.setDate(note.getDate());
        in.setDoctor(note.getDoctor());
        in.setPatient(note.getPatient());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(in);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
