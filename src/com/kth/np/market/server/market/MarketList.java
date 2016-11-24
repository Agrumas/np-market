package com.kth.np.market.server.market;

import com.kth.np.market.common.Item;
import com.kth.np.market.server.Db;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class MarketList {
    Db db;

    public MarketList(Db db) {
        this.db = db;
    }

    public List<Item> find() {
        EntityManager em = db.getEntityManager();
        Query query = em.createQuery("select i from Item i");
        List<Item> items = query.getResultList();
        em.close();
        return items;
    }

    public Item findOne(Item searchItem) {
        EntityManager em = db.getEntityManager();
        Query query = em.createQuery("select i from Item i where i.name like :name and i.price = :price");
        query.setParameter("name", searchItem.getName());
        query.setParameter("price", searchItem.getPrice());
        Item item;
        try {
            item = (Item) query.getSingleResult();
        } catch (NoResultException noResult) {
            item = null;
        }
        em.close();
        return item;
    }

    public boolean exists(Item item) {
        return findOne(item) != null;
    }

    public synchronized void remove(Item item) {
        EntityManager em = db.getEntityManager();
        em.getTransaction().begin();
        em.remove(item);
        em.getTransaction().commit();
        em.close();
    }

    public synchronized Item add(Item item) {
        EntityManager em = db.getEntityManager();
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
        em.close();
        return item;
    }
}
