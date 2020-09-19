package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Carb;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CarbRepository {

    private EntityManager entityManager;

    public CarbRepository(EntityManager entityManager) { this.entityManager = entityManager; }

    /**
     * The patient can store their data carb intake (measured in grams)
     *
     * @param
     * @return
     */
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

    /**
     * The patient can view all of their carb entries
     *
     * @return List of all carb entries
     */
    public List<Carb> findAll() { return entityManager.createQuery("from Carb c").getResultList(); }

    /**
     * The patient can view their average carb intake over a user-specified period *REQUIRED*
     *
     * @param startDate User specified date in format "yyy-MM-dd"
     * @param endDate User specified date in format "yyy-MM-dd"
     * @return The average grams of carb entries per day as list
     */
    public List<Carb> findAverageCarbIntake(Date startDate, Date endDate) {
        List<Carb> carb = entityManager.createQuery(
                     "SELECT c.date, avg(c.gram) " +
                        "FROM Carb c" +
                        "WHERE c.date >= : startDate AND c.date <= :endDate AND date is not null")
                .setParameter("date", startDate)
                .setParameter("date", endDate)
                .getResultList();
        return carb;
    }
}
