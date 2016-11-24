package com.kth.np.market.server.market;

import com.kth.np.market.common.*;
import com.kth.np.market.common.exceptions.*;
import com.kth.np.market.common.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class UserMarket extends UnicastRemoteObject implements Market {
    protected GlobalMarket market;
    protected User user;

    public UserMarket(GlobalMarket market, User user) throws RemoteException {
        super();
        this.market = market;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void sell(Item item) throws RemoteException, ItemOnSaleError {
        item.setOwner(user);
        System.out.println("Trying to sell: " + item);
        market.sell(user, item);
    }

    @Override
    public Item buy(Item item) throws RemoteException, InsufficientFundsError, ItemNotFoundError, BankAccountNotAccesibleError {
        System.out.println("Trying to buy: " + item);
        return market.buy(user, item);
    }

    @Override
    public void wishlistAdd(Item item) throws RemoteException, ItemInWishlistError {
        market.addToWishlist(user, item);
    }

    @Override
    public void wishlistRemove(Item item) throws RemoteException {
        market.removeFromWishlist(user, item);
    }

    @Override
    public void subscribeEvents(Collection<ItemActionCallback> callbacks) throws RemoteException {
        market.listenEvents(user, callbacks);
    }

    @Override
    public Collection<Item> list() throws RemoteException {
        return market.marketList.find();
    }

    @Override
    public void logout() throws RemoteException {
        market.logout(user);
    }

    @Override
    public Collection<Item> getWishlist() throws RemoteException {
        return market.listWishlist(user);
    }
}

