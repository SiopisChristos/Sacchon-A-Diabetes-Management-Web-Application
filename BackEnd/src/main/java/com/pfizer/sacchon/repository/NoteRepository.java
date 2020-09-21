package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.model.Note;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class NoteRepository {

    private EntityManager entityManager;

    public NoteRepository(EntityManager entityManager) {this.entityManager = entityManager;}

    public Optional<Note> save(Note note){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (note);
            entityManager.getTransaction().commit();
            return Optional.of(note);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * The patient can view the current and past consultations from doctors
     *
     * @return List of all notes entries
     */
    public List<Note> findAllConsultations() {
        return entityManager.createQuery("from Note n").getResultList();
    }

}
