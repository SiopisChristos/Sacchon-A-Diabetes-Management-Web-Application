package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.model.Patient;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CarbRepository {

    private EntityManager entityManager;

    public CarbRepository(EntityManager entityManager) { this.entityManager = entityManager; }

    /**
     * The patient can store their data carb intake (measured in grams)
     */
    public boolean save(Carb carb){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (carb);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Search for carb entry with specific id
     *
     * @param id
     * @return Carb as Optional
     */
    public Optional<Carb> findById(Long id) {
        Carb carb = entityManager.find(Carb.class, id);
        return carb != null ? Optional.of(carb) : Optional.empty();
    }

    /**
     * The patient can view all of their carb entries
     *
     * @return List of all carb entries
     */
    public List<Carb> findAll() { return entityManager.createQuery("from Carb c").getResultList(); }

    /**
     * The patient can view their average carb intake over a user-specified period *REQUIRED*
     *
     * @param startDate User specified date in format "yy-MM-dd"
     * @param endDate User specified date in format "yy-MM-dd"
     * @return The average grams of carb entries per day as list
     */
    public Double findAverageCarbIntake(Patient patient, Date startDate, Date endDate) {

        Double carb = (Double) entityManager.createQuery(
                "SELECT avg(c.gram) " +
                        "FROM Carb c " +
                        "WHERE c.date >= :startDate AND c.date <= :endDate AND c.date is not null AND c.patient = :patient")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("patient", patient)
                .getSingleResult();
        return carb;
    }

    /**
     * Update data of an existing Carb entry
     *
     * @param carb
     * @return boolean if database has been updated
     */
    public boolean updateCarb(Carb carb) {
        Carb in = entityManager.find(Carb.class, carb.getId());
        in.setGram(carb.getGram());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (in);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a Carb entry completely
     *
     * @param id
     * @return true if db has been updated
     */
    public boolean removeCarbEntry(Long id) {
        Optional<Carb> optionalCarb = findById(id);
        if (optionalCarb.isPresent()) {
            Carb carb = optionalCarb.get();
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(carb);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
