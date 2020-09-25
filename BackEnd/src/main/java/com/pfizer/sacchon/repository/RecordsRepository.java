package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.model.Glucose;
import com.pfizer.sacchon.model.Note;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.GlucoseRepresentation;
import com.pfizer.sacchon.representation.NoteRepresentation;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class RecordsRepository {
    private EntityManager entityManager;

    public RecordsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
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
     * Updates the seen property of a note to True
     *
     * @param note the updated note
     * @return True if updating has been completed, else false
     */
    public boolean updateNoteSeen(Note note) {
        Note in = entityManager.find(Note.class, note.getId());
        in.setSeen(true);
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
        in.setSeen(false);
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


    /**
     * Returns all the patient data (Carbs & Glucose measurements) and their consultations from Db
     * in a array List structure which contains three Lists.
     * The List structure is: {Carbs, Glucose, Notes}
     *
     * @param patient_id the integer id of a specific patient
     * @return patient data and consultations
     */
    public List[] getPatientRecord(long patient_id) {
        return new List[]{getPatientCarbs(patient_id), getPatientGlucose(patient_id), getPatientNotes(patient_id)};
    }

    /**
     * Returns a list of Carbs of a specific patient in CarbRepresentation form
     *
     * @param patient_id The patient_id of a specific patient
     * @return A list of Carbs in CarbRepresentation form
     */
    public List<CarbRepresentation> getPatientCarbs(long patient_id) {
        List<Carb> carb = entityManager.createQuery(
                "from Carb where :id = patient_id")
                .setParameter("id", patient_id).getResultList();
        List<CarbRepresentation> carbRepresentation = new ArrayList<>();
        carb.forEach(c -> carbRepresentation.add(new CarbRepresentation(c)));
        return carbRepresentation;
    }

    /**
     * Returns a list of Glucose of a specific patient in GlucoseRepresentation form
     *
     * @param patient_id The patient_id of a specific patient
     * @return A list of Glucose in GlucoseRepresentation form
     */
    public List<GlucoseRepresentation> getPatientGlucose(long patient_id) {
        List<Glucose> glucose = entityManager.createQuery(
                "from Glucose where :id = patient_id")
                .setParameter("id", patient_id).getResultList();

        List<GlucoseRepresentation> glucoseRepresentation = new ArrayList<>();
        glucose.forEach(g -> glucoseRepresentation.add(new GlucoseRepresentation(g)));

        return glucoseRepresentation;

    }

    /**
     * Returns a list of Notes of a specific patient in NoteRepresentation form
     *
     * @param patient_id The patient_id of a specific patient
     * @return A list of Notes in NoteRepresentation form
     */
    public List<NoteRepresentation> getPatientNotes(long patient_id) {

        List<Note> note = entityManager.createQuery(
                "from Note where :id = patient_id")
                .setParameter("id", patient_id).getResultList();

        List<NoteRepresentation> noteRepresentation = new ArrayList<>();
        note.forEach(n -> noteRepresentation.add(new NoteRepresentation(n)));

        return noteRepresentation;
    }

    /**
     * The patient can view the current and past consultations from doctors
     *
     * @return List of all notes entries
     */
    public List<Note> findAllConsultations(Patient patient) {
        return entityManager.createQuery("from Note where patient = :patient ")
                .setParameter("patient", patient).getResultList();
    }


}
