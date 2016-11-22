package com.kth.np.market.client.commands;

import com.kth.np.market.client.cli.MarketCommand;
import com.kth.np.market.common.Item;
import com.kth.np.market.common.Market;
import com.kth.np.market.common.exceptions.MarketError;

import java.rmi.RemoteException;

/**
 * Created by Algirdas on 11/21/2016.
 */
public class BuyCommand extends MarketCommand {
    protected String itemName;
    protected int price;

    @Override
    public void execute(Market market) throws MarketError {
        try {
            Item item = market.buy(new Item(itemName, price));
            info("You have bought a item: " + item);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean prepare() {
        if (options.length < 2) {
            return false;
        }
        itemName = options[0].trim();
        price = new Integer(options[1].trim());

        return !itemName.isEmpty() && price > 0;
    }

    @Override
    public void printUsage() {
        System.out.println("Usage: " + getName() + " name price");
    }
}
