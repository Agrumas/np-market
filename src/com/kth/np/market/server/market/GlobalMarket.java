package com.kth.np.market.server.market;

import com.kth.np.market.common.Item;
import com.kth.np.market.common.ItemActionCallback;
import com.kth.np.market.common.Market;
import com.kth.np.market.common.TradingMarket;
import com.kth.np.market.common.exceptions.*;
import com.kth.np.market.server.user.*;

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
    protected TransactionsService transactionsService;

    public GlobalMarket() throws RemoteException {
        super();
        users = new UserList();
        eventList = new EventList();
        marketList = new MarketList();
        wishlist = new Wishlist();
        transactionsService = new TransactionsService();
    }

    @Override
    public Market register(String name, String bank) throws RemoteException, UserIsRegistredError {
        if (users.exists(name)) {
            throw new UserIsRegistredError();
        }
        User user = users.register(name, bank);
        return new UserMarket(this, user);
    }

    @Override
    public Market login(String name, String pass) throws RemoteException, InvalidCredentialsError {
        User user = users.get(name);
        if (user == null || !pass.equals("test")) {
            throw new InvalidCredentialsError();
        }
        return new UserMarket(this, user);
    }

    public void logout(User user) {
        System.out.println("[" + user.getId() + "] logout");
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
        Item itemToBuy = marketList.search(item);
        if (itemToBuy == null) {
            throw new ItemNotFoundError("Item is not found.");
        }

        User owner = users.get(itemToBuy.getOwner());
        transactionsService.transfer(user, owner, itemToBuy.getPrice());

        marketList.remove(itemToBuy);
        itemToBuy.setOwner(user);
        eventList.emit(owner, ItemActionCallback.Types.ITEM_SOLD, itemToBuy);
        return itemToBuy;
    }

    public void addToWishlist(User user, Item item) throws ItemInWishlistError {
        item.setOwner(user);
        if (wishlist.exists(item)) {
            throw new ItemInWishlistError("Item is already in wishlist");
        }
        wishlist.add(item);
    }

    public Collection<Item> listWishlist(User user) {
        return wishlist.getByUser(user);
    }

    public boolean removeFromWishlist(User user, Item item) {
        item.setOwner(user);
        return wishlist.remove(item);
    }


}
