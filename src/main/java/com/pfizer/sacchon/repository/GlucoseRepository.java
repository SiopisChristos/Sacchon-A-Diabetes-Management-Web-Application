package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.Glucose;
import javax.persistence.EntityManager;
import java.util.Optional;

public class GlucoseRepository {

    private EntityManager entityManager;

    public GlucoseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //The patient can store their data blood glucose level (date, time, measured in mg/dL)
    public Optional<Glucose> save(Glucose glucose){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (glucose);
            entityManager.getTransaction().commit();
            return Optional.of(glucose);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
