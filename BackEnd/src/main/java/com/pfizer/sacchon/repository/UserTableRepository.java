package com.pfizer.sacchon.repository;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.UserTable;

import javax.persistence.EntityManager;

public class UserTableRepository {

    private EntityManager entityManager;

    public UserTableRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     *
     * @param username
     * @return
     */
    public UserTable findAccountById(String username) throws NotFoundException {
        UserTable user = entityManager.find(UserTable.class, username);
        if (user != null)
            return user;
        throw new NotFoundException("There is not a user");
    }

    public String matchUsernameAndPassword(String username, String password) throws NotFoundException{
        UserTable user = findAccountById(username);
        if (user.getPassword().equals(password))
            return user.getRole();
        throw new NotFoundException("There is not a user");
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

}
