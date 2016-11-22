package com.kth.np.market.client;

import com.kth.np.market.common.ItemActionCallback;

import java.util.LinkedList;

/**
 * Created by Algirdas on 11/21/2016.
 */
public class EventSubscriber {
    LinkedList<ItemActionCallback> subscriptions;

    public EventSubscriber() {
        this.subscriptions = new LinkedList<>();
    }

    public void subscribe(ItemActionCallback callback) {
        subscriptions.push(callback);
    }

    public LinkedList<ItemActionCallback> getSubscriptions() {
        return subscriptions;
    }
}
