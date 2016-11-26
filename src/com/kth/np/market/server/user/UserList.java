package com.kth.np.market.server.user;

import com.kth.np.market.common.User;
import com.kth.np.market.common.UserActivity;
import com.kth.np.market.server.Db;
import com.kth.np.market.server.DbQuery;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class UserList {
    Db db;

    public UserList(Db db) {
        this.db = db;
    }

    public User get(String id) {
        return new DbQuery<User>(db).execute((EntityManager em) -> em.find(User.class, id)).getResult();
    }

    public User getByName(String name) {
        return new DbQuery<User>(db).execute((EntityManager em) -> {
            Query query = em.createQuery("select u from User u where u.name = :name");
            query.setParameter("name", name);
            try {
                return (User) query.getSingleResult();
            } catch (NoResultException noResult) {
                return null;
            }
        }).getResult();
    }

    public boolean exists(String name) {
        return new DbQuery<Boolean>(db).execute((EntityManager em) -> {
            Query query = em.createNativeQuery("select 1 from users u where u.name like :name");
            query.setParameter("name", name);
            return !query.getResultList().isEmpty();
        }).getResult();
    }

    public synchronized User register(String name, String password, String bank) {
        return new DbQuery<User>(db).transaction((EntityManager em) -> {
            User user = new User(name, password, bank);
            user.setUserActivity(new UserActivity());
            em.persist(user);
            return user;
        }).getResult();
    }

    public synchronized void updateStats(User buyer, User seller) {
        new DbQuery<UserActivity>(db).transaction((EntityManager em) -> {
            UserActivity sA = seller.getUserActivity();
            UserActivity bA = !seller.equals(buyer) ? buyer.getUserActivity() : sA;
            sA.setSold(sA.getSold() + 1);
            bA.setBought(bA.getBought() + 1);
            em.persist(em.contains(sA) ? sA : em.merge(sA));
            if (sA != bA) {
                em.persist(em.contains(bA) ? bA : em.merge(bA));
            }
            return null;
        }).getResult();
    }

    public UserActivity getActivity(User user) {
        return new DbQuery<UserActivity>(db).transaction((EntityManager em) -> {
            Query query = em.createQuery("select a from UserActivity a where a.id = :id");
            query.setParameter("id", user.getId());
            try {
                return (UserActivity) query.getSingleResult();
            } catch (NoResultException noResult) {
                return null;
            }
        }).getResult();
    }
}
