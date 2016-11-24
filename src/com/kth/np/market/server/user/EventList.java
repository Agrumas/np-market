package com.kth.np.market.server.user;

import com.kth.np.market.common.Item;
import com.kth.np.market.common.ItemActionCallback;
import com.kth.np.market.common.User;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Algirdas on 11/21/2016.
 */
public class EventList {
    protected HashMap<String, Collection<ItemActionCallback>> list;

    public EventList() {
        this.list = new HashMap<>();
    }

    public void subscribe(User user, Collection<ItemActionCallback> callbacks) {
        list.put(user.getId(), callbacks);
    }

    public void unsubscribe(User user) {
        list.remove(user.getId());
    }

    public void emit(User user, ItemActionCallback.Types event, Item item) {
        emit(user.getId(), event, item);
    }

    public void emit(String userId, ItemActionCallback.Types event, Item item) {
        Collection<ItemActionCallback> callbacks = list.get(userId);
        if (callbacks == null) {
            return;
        }
        ItemActionCallback eventCb = callbacks.stream()
                .filter(cb -> {
                    try {
                        return cb.isSameType(event);
                    } catch (RemoteException e) {
                        list.remove(userId);
                    }
                    return false;
                })
                .findFirst().orElse(null);
        if (eventCb != null) {
            try {
                eventCb.invoke(item);
            } catch (RemoteException e) {
                System.err.println("Event can not be emitted " + userId);
                e.printStackTrace();
                list.remove(userId);
            }
        }
    }
}
