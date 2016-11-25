package com.kth.np.market.client;

import com.kth.np.market.client.cli.ClientCLI;
import com.kth.np.market.client.commands.*;
import com.kth.np.market.common.*;
import com.kth.np.market.common.cli.CLI;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class ClientApp {

    Market market;
    TradingMarket globalMarket;
    EventSubscriber eventSubscriber;

    public ClientApp() {
        eventSubscriber = new EventSubscriber();
        try {
            globalMarket = (TradingMarket) Naming.lookup(TradingMarket.NAME);
        } catch (Exception e) {
            System.out.println("Starting ClientApp failed: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to market");
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Market getMarket() {
        return market;
    }

    public TradingMarket getGlobalMarket() {
        return globalMarket;
    }

    public EventSubscriber getEventSubscriber() {
        return eventSubscriber;
    }

    public static void main(String[] args) {
        ClientApp app = new ClientApp();
        CLI cli = new ClientCLI(app);
        cli.register(RegisterCommand.class);
        cli.register(LoginCommand.class);
        cli.register(ExitCommand.class);
        cli.register("logout", ExitCommand.class);
        cli.register(SellCommand.class);
        cli.register(BuyCommand.class);
        cli.register(StatsCommand.class);
        cli.register(ListCommand.class);
        cli.register(WishlistAddCommand.class);
        cli.register(WishlistCommand.class);
        cli.register(WishlistRemoveCommand.class);

        try {
            app.eventSubscriber.subscribe(new ItemSold() {
                @Override
                public void invoke(Item item) throws RemoteException {
                    System.out.println("Item has been sold: " + item.toStr() + " to " + item.getOwner());
                }
            });

            app.eventSubscriber.subscribe(new WishlistItemAvailable() {
                @Override
                public void invoke(Item item) throws RemoteException {
                    System.out.println("Wishlist item available: " + item.toStr() + " by " + item.getOwner());
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        cli.handle();
    }
}
