package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.model.Glucose;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.util.DateConverter;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class GlucoseRepository {

    private EntityManager entityManager;

    public GlucoseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * The patient can store their data blood glucose level
     * @param glucose
     * @return true if it's saved in Db else false
     */
    public boolean save(Glucose glucose){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(glucose);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
     * @param startDate User specified date
     * @param endDate User specified date
     * @return The average blood glucose level entries per day as list
     */
    public List<Double> findAverageGlucoseLevel(Patient patient, Date startDate, Date endDate) {
        Date current = startDate;

        List<Double> glucose = new ArrayList<>();
        while (current.before(endDate) || current.equals(endDate)) {

            Date startOfCurrentDate = DateConverter.startAndEndOfDate(current).get(0);
            Date endOfCurrentDate = DateConverter.startAndEndOfDate(current).get(1);


            Double glucoseAvg = (Double) entityManager.createQuery(
                    "SELECT avg(g.measurement) " +
                            "FROM Glucose g " +
                            "WHERE (g.dateTime between :startDate  and  :endDate) and g.dateTime is not null AND g.patient = :patient")
                    .setParameter("startDate", startOfCurrentDate)
                    .setParameter("endDate",endOfCurrentDate)
                    .setParameter("patient", patient)
                    .getSingleResult();

            glucose.add(glucoseAvg);

            //Next date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.DATE, 1);
            current = calendar.getTime();
        }
        return glucose;
    }

    /**
     * Update data of an existing Glucose entry
     *
     * @param glucose
     * @return boolean if database has been updated
     */
    public Boolean updateGlucose(Glucose glucose) {

        Glucose in = entityManager.find(Glucose.class, glucose.getId());
        in.setMeasurement(glucose.getMeasurement());
//        in.setDateTime(glucose.getDateTime());
        in.setPatient(glucose.getPatient());
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
     * Deletes a Glucose entry completely
     *
     * @param id
     * @return true if db has been updated else false
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
