package com.kth.np.market.server;

import com.kth.np.market.server.market.GlobalMarket;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class TradingMarketApp {
    public TradingMarketApp(String marketName) {
        try {
            GlobalMarket market = new GlobalMarket();

            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            Naming.rebind(marketName, market);
            System.out.println(marketName + " is ready. " + market);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Server");
        new TradingMarketApp(GlobalMarket.NAME);
    }
}
