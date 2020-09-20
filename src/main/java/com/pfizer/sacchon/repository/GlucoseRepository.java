package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.Glucose;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class GlucoseRepository {

    private EntityManager entityManager;

    public GlucoseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * The patient can store their data blood glucose level (date, time, measured in mg/dL)
     */
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

    /**
     * The patient can view all of their blood glucose entries
     *
     * @return List of all blood glucose entries
     */
    public List<Glucose> findAll() { return entityManager.createQuery("from Glucose g").getResultList(); }

    /**
     * Patients can view their average daily blood glucose level over a user- specified period *REQUIRED*
     *
     * @param startDate User specified date in format "yyy-MM-dd"
     * @param endDate User specified date in format "yyy-MM-dd"
     * @return The average blood glucose level entries per day as list
     */
    public List<Glucose> findAverageGlucoseLevel (Date startDate, Date endDate) {
        List<Glucose> glucose = entityManager.createQuery(
                "SELECT g.dateTime, avg(g.measurement) " +
                        "FROM Glucose g" +
                        "WHERE c.dateTime >= : startDate AND c.dateTime <= :endDate AND dateTime is not null")
                .setParameter("date", startDate)
                .setParameter("date", endDate)
                .getResultList();
        return glucose;
    }
}
