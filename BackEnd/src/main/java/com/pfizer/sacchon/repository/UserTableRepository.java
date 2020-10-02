package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.model.UserTable;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Optional;

public class UserTableRepository {

    private EntityManager entityManager;

    public UserTableRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * A registration method for doctors
     * @param doctor
     * @param userTable
     * @return true if it's completed, else false
     */
    public boolean register(Doctor doctor,  UserTable userTable){
        doctor.setCreationDate(new Date());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(doctor);
            entityManager.persist(userTable);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * A registration method for patients
     * @param patient
     * @param userTable
     * @return true if it's completed, else false
     */
    public boolean register(Patient patient, UserTable userTable){
        patient.setCreationDate(new Date());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patient);
            entityManager.persist(userTable);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     *  Find a UserTable Entity by its username
     * @param username
     * @return the UserTable Entity
     */
    public UserTable findAccountById(String username) throws NotFoundException {
        UserTable user = entityManager.find(UserTable.class, username);
        if (user != null)
            return user;
        throw new NotFoundException("There is not a user");
    }

    /**
     * A function for matching username with it's password
     * @param username
     * @param password
     * @return  The user's Role as String if their matching
     * @throws NotFoundException
     */
    public String matchUsernameAndPassword(String username, String password) throws NotFoundException{
        UserTable user = findAccountById(username);
        if (user.getPassword().equals(password))
            return user.getRole();
        throw new NotFoundException("The password is not the same");
    }

    /**
     * Deleting a doctor from the Db UserTable
     *
     * @param user The doctor to be deleted
     * @return True if deleting has been completed, else false
     */
    public boolean removeAccount(UserTable user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if an existing Doctor has his account Active
     * @param username
     * @return true if it's active, else false
     */
    public boolean doctorIsActive(String username){
        Doctor doctor = (Doctor) entityManager.createQuery(
                    "from Doctor d where :username = d.username")
                .setParameter("username", username).getSingleResult();
        if (doctor.isActive())
            return true;
        return false;

    }

    /**
     * Checks if an existing Patient has his account Active
     * @param username
     * @return  true if it's active, else false
     */
    public boolean patientIsActive(String username){
        Patient patient = (Patient) entityManager.createQuery(
                "from Patient p where :username = p.username")
                .setParameter("username", username).getSingleResult();
        if (patient.isActive())
            return true;
        return false;

    }

}
