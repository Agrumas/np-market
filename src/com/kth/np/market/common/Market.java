package com.kth.np.market.common;

import com.kth.np.market.common.exceptions.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by Algirdas on 11/18/2016.
 */
public interface Market extends Remote {
    public void sell(Item item) throws RemoteException, ItemOnSaleError;
    public Item buy(Item item) throws RemoteException, InsufficientFundsError, ItemNotFoundError, BankAccountNotAccesibleError;
    public void wishlistAdd(WishlistItem item) throws RemoteException, ItemInWishlistError;
    public void wishlistRemove(WishlistItem item) throws RemoteException;
    public Collection<WishlistItem> getWishlist() throws RemoteException;
    public void subscribeEvents(Collection <ItemActionCallback> callbacks) throws RemoteException;
    public Collection<Item> list() throws RemoteException;
    public UserActivity getStats() throws RemoteException;
    public void logout() throws RemoteException;
}
