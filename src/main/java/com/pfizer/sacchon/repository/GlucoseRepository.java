package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.Carb;
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
     * Search for glucose entry with specific id
     *
     * @param id
     * @return Glucose as Optional
     */
    public Optional<Glucose> findById(Long id) {
        Glucose glucose = entityManager.find(Glucose.class, id);
        return glucose != null ? Optional.of(glucose) : Optional.empty();
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
                "SELECT avg(g.measurement) " +
                        "FROM Glucose g" +
                        "WHERE c.dateTime >= :startDate AND c.dateTime <= :endDate AND g.dateTime is not null")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        return glucose;
    }

    /**
     * Update data of an existing Glucose entry
     *
     * @param glucose
     * @return boolean if database has been updated
     */
    public Optional<Glucose> updateGlucose(Glucose glucose) {

        Glucose in = entityManager.find(Glucose.class, glucose.getId());
        in.setMeasurement(glucose.getMeasurement());
        in.setDateTime(glucose.getDateTime());
        in.setPatient(glucose.getPatient());
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

    /**
     * Deletes a Glucose entry completely
     *
     * @param id
     * @return true if db has been updated
     */
    public boolean removeGlucoseEntry(Long id) {
        Optional<Glucose> optionalGlucose = findById(id);
        if (optionalGlucose.isPresent()) {
            Glucose glucose = optionalGlucose.get();
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(glucose);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
