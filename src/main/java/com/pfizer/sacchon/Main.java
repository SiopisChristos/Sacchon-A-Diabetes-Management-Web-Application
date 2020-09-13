package com.pfizer.sacchon;

import com.pfizer.sacchon.repository.util.JpaUtil;

import javax.persistence.EntityManager;

public class Main {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
    }

}
