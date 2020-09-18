package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.security.dao.DatabaseCredentials;
import org.restlet.Context;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class DoctorRepository {

    private EntityManager entityManager;

    public DoctorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * Find the patients for a specific doctor_id
     *
     * @param doctor_id id of the doctor who provide advice to patients
     * @return A list of his patients
     */
    public List<Patient> findMyPatients(long doctor_id) {
        List<Patient> results = entityManager.createQuery(
                "from Patient where :id = doctor_id").setParameter("id", doctor_id)
                .getResultList();
        return results;
    }


    public List<Patient> findFreePatients() {
        List<Patient> results = entityManager.createQuery(
                "from Patient where doctor_id is null")
                .getResultList();
        return results;
    }


    /**
     * Checks if a patient is under supervision of a specific doctor
     *
     * @param patient_id PatientId to be checked
     * @param doctor_id  DoctorId to be checked
     * @return true if it's a match, else false;
     */
    public boolean isYourPatient(long patient_id, long doctor_id) {

        List<Patient> results = findMyPatients(doctor_id);
        if (results.isEmpty())
            return false;
        else if (results.get(0).getId() == patient_id)
            return true;
        else
            return false;
    }


    public boolean isFreePatient(long patient_id) {
        Patient patient = (Patient) entityManager.createQuery(
                "from Patient where doctor_id is null and patient_id = :id").
                setParameter("id", patient_id).getSingleResult();
        if (!patient.equals(null))
            return true;
        return false;
    }


    public void doctorAccessPatientData(Long patient_id, Doctor doctor) throws NotAuthorizedException {
        if (!isYourPatient(patient_id, doctor.getId()) || !isFreePatient(patient_id))
            throw new NotAuthorizedException("Not Authorized Doctor");
    }


    /**
     * Select all the inactive Patients from Db
     *
     * @return a List which contains all their personal data
     */
    public List<Patient> findInactivePatients() {
        return entityManager.createQuery(
                "from Patient p where p.doctor_id IS NULL")
                .getResultList();
    }


    public boolean removeDoctor(String username) throws SQLException {

        Context.getCurrentLogger().finer(
                "Method findById() of ApplicationUserPersistence called.");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DatabaseCredentials.URL, DatabaseCredentials.USER, DatabaseCredentials.PASSWORD);

            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from UserTable where username=?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                connection.close();
                return true;
            }
        }
    }



    /**
     * @param username
     * @return
     */
    public Optional<Doctor> findDoctorByUsername(String username) {
        Doctor doctor = (Doctor) entityManager.createQuery(
                "from Doctor d where :username = d.username").setParameter("username", username).getSingleResult();
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }


    /**
     * find doctor by id
     *
     * @param id
     * @return
     */
    public Optional<Doctor> findDoctorById(long id) {
        Doctor doctor = entityManager.find(Doctor.class, id);
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    /**
     * find a Patient record by ID
     *
     * @param patient_id
     * @return Optional of Patient or empty
     */
    public Optional<Patient> findPatientById(long patient_id) {
        Patient patient = entityManager.find(Patient.class, patient_id);
        return patient != null ? Optional.of(patient) : Optional.empty();
    }

}

