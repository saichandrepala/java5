package com.Tenet.MultiDBTenet.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBConfig {

    public EntityManager getEntity(String tenant) {
        EntityManagerFactory enf = Persistence.createEntityManagerFactory(tenant);
        EntityManager em = enf.createEntityManager();

        em.getTransaction().begin();
        
        return em;
    }
    
}
