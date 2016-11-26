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
public class SellCommand extends MarketCommand {
    protected Item item = null;

    @Override
    public void execute(Market market) throws MarketError {
        try {
            market.sell(item);
            info("Item has been put on sale.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean prepare() {
        item = ItemHelper.readFromOptions(options);

        return item != null;
    }

    @Override
    public void printUsage() {
        System.out.println("Usage: " + getName() + " name price [amount]");
    }
}
