package com.kth.np.market.server.market;

import com.kth.np.market.common.Item;

import java.util.HashMap;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class MarketList {
    HashMap<String, Item> list;

    public MarketList() {
        this.list = new HashMap<>();
    }


    public Item search(Item searchItem) {
        return list.values().stream().filter(item -> item.isSameItem(searchItem))
                .findFirst().orElse(null);
    }

    public boolean exists(Item item) {
        return search(item) != null;
    }

    public synchronized Item remove(Item item) {
        return list.remove(item.getId());
    }

    public synchronized Item add(Item item) {
        list.put(item.getId(), item);
        return item;
    }
}
