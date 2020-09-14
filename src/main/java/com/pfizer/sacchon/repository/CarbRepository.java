package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.Carb;
import javax.persistence.EntityManager;
import java.util.Optional;

public class CarbRepository {

    private EntityManager entityManager;

    public CarbRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //The patient can store their data carb intake (measured in grams)
    public Optional<Carb> save(Carb carb){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (carb);
            entityManager.getTransaction().commit();
            return Optional.of(carb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
