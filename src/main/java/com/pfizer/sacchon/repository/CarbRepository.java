package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.Carb;
import lombok.SneakyThrows;
import org.restlet.engine.Engine;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.logging.Logger;

public class CarbRepository {

    private EntityManager entityManager;

    public static final Logger LOGGER = Engine.getLogger(CarbRepository.class);

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

    //Returns carb entry id (just for testing)
    public Optional<Carb> findById(Long id) {
        Carb carb = entityManager.find(Carb.class, id);
        return carb != null ? Optional.of(carb) : Optional.empty();
    }

    public Double findAverageCarbIntake(Long id) {
        Double result = (Double) entityManager.createQuery("SELECT avg(gram) FROM Carb where id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }
}
