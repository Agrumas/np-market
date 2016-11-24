package com.kth.np.market.server.user;

import com.kth.np.market.common.Item;
import com.kth.np.market.common.User;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class Wishlists {
    LinkedList<Item> list;

    public Wishlists() {
        this.list = new LinkedList<>();
    }


    public Item search(Item searchItem) {
        return list.stream().filter(item -> item.isSameItem(searchItem) && item.getOwner().equals(searchItem.getOwner()))
                .findFirst().orElse(null);
    }

    public List<Item> match(Item wantedItem) {
        return list.stream().filter(item -> wantedItem.isSameCheaperThan(item)).collect(Collectors.toList());
    }

    public List<Item> getByUser(User user) {
        return getByUser(user.getId());
    }

    public List<Item> getByUser(String userId) {
        return list.stream().filter(item -> userId.equals(item.getOwner())).collect(Collectors.toList());
    }

    public boolean exists(Item item) {
        return search(item) != null;
    }

    public synchronized boolean remove(Item itemToRemove) {
        return list.removeIf(item -> item.isSameItem(itemToRemove) && item.getOwner().equals(itemToRemove.getOwner()));
    }

    public synchronized boolean removeByUser(User user) {
        return removeByUser(user.getId());
    }

    public synchronized boolean removeByUser(String userId) {
        return userId != null && list.removeIf(item -> userId.equals(item.getOwner()));
    }

    public synchronized Item add(Item item) {
        list.add(item);
        return item;
    }
}
