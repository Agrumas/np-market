package com.kth.np.market.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Algirdas on 11/18/2016.
 */
public interface ItemActionCallback extends Remote {
    enum Types {ITEM_SOLD, WISHLIST_ITEM_AVAILABLE};
    void invoke(Item item) throws RemoteException;
    boolean isSameType(Types type) throws RemoteException;
}
