package com.kth.np.market.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by Algirdas on 11/23/2016.
 */
public class Db {
    protected EntityManagerFactory emfactory;

    public Db(EntityManagerFactory emfactory) {
        this.emfactory = emfactory;
    }


    public EntityManager getEntityManager() {
        return emfactory.createEntityManager();
    }
}
