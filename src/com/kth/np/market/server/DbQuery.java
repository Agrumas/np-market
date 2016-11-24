package com.kth.np.market.server;

import javax.persistence.EntityManager;

/**
 * Created by Algirdas on 11/23/2016.
 */
public class DbQuery<T> {
    protected T result;
    protected Db db;

    public DbQuery(Db db) {
        this.db = db;
    }

    @FunctionalInterface
    public interface Transaction<R> {
        R execute(EntityManager em);
    }


    public DbQuery<T> transaction(Transaction<T> function) {
        EntityManager em = db.getEntityManager();
        em.getTransaction().begin();
        try {
            result = function.execute(em);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return this;
    }

    public DbQuery<T> execute(Transaction<T> function) {
        EntityManager em = db.getEntityManager();
        try {
            result = function.execute(em);
        } finally {
            em.close();
        }
        return this;
    }

    public T getResult() {
        return result;
    }
}
