package com.kth.np.market.server.user;

import com.kth.np.market.common.Item;
import com.kth.np.market.common.User;
import com.kth.np.market.common.WishlistItem;
import com.kth.np.market.server.Db;
import com.kth.np.market.server.DbQuery;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class Wishlist {
    Db db;
    LinkedList<WishlistItem> list;

    public Wishlist(Db db) {
        this.db = db;
        this.list = new LinkedList<>();
    }


    public WishlistItem search(WishlistItem searchItem) {
        return list.stream().filter(item -> item.equals(searchItem))
                .findFirst().orElse(null);
    }

    public List<WishlistItem> match(Item item) {
        return list.stream().filter(wishlistItem -> wishlistItem.isMatching(item)).collect(Collectors.toList());
    }

    public List<WishlistItem> getByUser(User user) {
        return getByUser(user.getId());
    }

    public List<WishlistItem> getByUser(String userId) {
        return list.stream().filter(item -> userId.equals(item.getOwner())).collect(Collectors.toList());
    }

    public boolean exists(WishlistItem item) {
        return search(item) != null;
    }

    public synchronized boolean remove(WishlistItem itemToRemove) {
        WishlistItem item = search(itemToRemove);
        if (item == null) {
            return false;
        }
        new DbQuery<WishlistItem>(db).transaction((EntityManager em) -> {
            em.remove(em.contains(item) ? item : em.merge(item));
            return item;
        });
        return list.remove(item);
    }

    public synchronized boolean removeByUser(User user) {
        return removeByUser(user.getId());
    }

    public synchronized boolean removeByUser(String userId) {
        return userId != null && list.removeIf(item -> userId.equals(item.getOwner()));
    }

    public void loadByUser(User user) {
        Collection<WishlistItem> col = (Collection<WishlistItem>) (new DbQuery<Collection<WishlistItem>>(db)).transaction((EntityManager em) -> {
            Query query = em.createQuery("select i from WishlistItem i where i.owner = :owner");
            query.setParameter("owner", user.getId());
            return query.getResultList();
        }).getResult();
        col.forEach(wishlistItem -> list.add(wishlistItem));
    }

    public synchronized WishlistItem add(WishlistItem item) {
        return new DbQuery<WishlistItem>(db).transaction((EntityManager em) -> {
            em.persist(item);
            list.add(item);
            return item;
        }).getResult();
    }
}
