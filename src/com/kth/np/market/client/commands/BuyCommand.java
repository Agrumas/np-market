package com.kth.np.market.client.commands;

import com.kth.np.market.client.cli.ItemHelper;
import com.kth.np.market.client.cli.MarketCommand;
import com.kth.np.market.common.Item;
import com.kth.np.market.common.Market;
import com.kth.np.market.common.exceptions.MarketError;

import java.rmi.RemoteException;

/**
 * Created by Algirdas on 11/21/2016.
 */
public class BuyCommand extends MarketCommand {
    protected Item inputItem = null;

    @Override
    public void execute(Market market) throws MarketError {
        try {
            Item item = market.buy(inputItem);
            info("You have bought a item: " + item.getName() + "($" + item.getPrice() + ")");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean prepare() {
        inputItem = ItemHelper.readFromOptions(options);

        return inputItem != null;
    }

    @Override
    public void printUsage() {
        System.out.println("Usage: " + getName() + " name price");
    }
}
