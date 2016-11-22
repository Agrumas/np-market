package com.kth.np.market.common;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Algirdas on 11/18/2016.
 */
public abstract class ItemSold extends UnicastRemoteObject implements ItemActionCallback {
    public ItemSold() throws RemoteException {
        super( );
    }

    @Override
    public boolean isSameType(Types type) {
        return type == Types.ITEM_SOLD;
    }
}
