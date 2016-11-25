package com.kth.np.market.server.market;

import com.kth.np.market.common.*;
import com.kth.np.market.common.exceptions.*;
import com.kth.np.market.server.Db;
import com.kth.np.market.server.user.*;

import javax.persistence.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class GlobalMarket extends UnicastRemoteObject implements TradingMarket {

    protected UserList users;
    protected MarketList marketList;
    protected EventList eventList;
    protected Wishlist wishlist;
    protected BankService bankService;
    protected EntityManagerFactory emfactory;
    protected Db db;

    public GlobalMarket() throws RemoteException {
        super();
        eventList = new EventList();
        bankService = new BankService();
        emfactory = Persistence.createEntityManagerFactory("MarketDB");
        db = new Db(emfactory);
        users = new UserList(db);
        wishlist = new Wishlist(db);
        marketList = new MarketList(db);
    }

    @Override
    public Market register(String name, String password, String bank) throws RemoteException, UserIsRegistredError, InvalidCredentialsError {
        if (password.length() < 8) {
            throw new InvalidCredentialsError("Password should be at least 8 characters long");
        }
        if (users.exists(name)) {
            throw new UserIsRegistredError();
        }
        User user = users.register(name, password, bank);

        return new UserMarket(this, user);
    }

    @Override
    public Market login(String name, String pass) throws RemoteException, InvalidCredentialsError {
        User user;
        try {
            user = users.getByName(name);
        } catch (NoResultException noresult) {
            throw new InvalidCredentialsError();
        }
        if (!user.isPasswordTheSame(pass)) {
            throw new InvalidCredentialsError();
        }
        wishlist.loadByUser(user);
        return new UserMarket(this, user);
    }

    public void logout(User user) {
        System.out.println("[" + user.getId() + "] logout");
        wishlist.removeByUser(user);
        eventList.unsubscribe(user);
    }

    public void listenEvents(User user, Collection<ItemActionCallback> callbacks) {
        eventList.subscribe(user, callbacks);
    }

    public void sell(User user, Item item) throws ItemOnSaleError {
        item.setOwner(user);
        if (marketList.exists(item)) {
            throw new ItemOnSaleError("Item is already on sale.");
        }
        marketList.add(item);
        wishlist.match(item).forEach(wlItem -> {
            eventList.emit(wlItem.getOwner(), ItemActionCallback.Types.WISHLIST_ITEM_AVAILABLE, item);
        });
    }

    public Item buy(User user, Item item) throws InsufficientFundsError, ItemNotFoundError, BankAccountNotAccesibleError {
        Item itemToBuy = marketList.findOne(item);
        if (itemToBuy == null) {
            throw new ItemNotFoundError("Item is not found.");
        }

        User owner = users.get(itemToBuy.getOwner());
        bankService.transfer(user, owner, itemToBuy.getPrice());
        users.updateStats(user, owner);
        marketList.remove(itemToBuy);
        itemToBuy.setOwner(user);
        eventList.emit(owner, ItemActionCallback.Types.ITEM_SOLD, itemToBuy);
        return itemToBuy;
    }

    public void addToWishlist(User user, WishlistItem item) throws ItemInWishlistError {
        item.setOwner(user);
        if (wishlist.exists(item)) {
            throw new ItemInWishlistError("Item is already in wishlist");
        }
        wishlist.add(item);
    }

    public Collection<WishlistItem> listWishlist(User user) {
        return wishlist.getByUser(user);
    }

    public boolean removeFromWishlist(User user, WishlistItem item) {
        item.setOwner(user);
        return wishlist.remove(item);
    }




}
