package com.kth.np.market.client.commands;

import com.kth.np.market.client.cli.MarketCommand;
import com.kth.np.market.common.Market;
import com.kth.np.market.common.UserActivity;
import com.kth.np.market.common.exceptions.MarketError;

import java.rmi.RemoteException;

/**
 * Created by Algirdas on 11/21/2016.
 */
public class StatsCommand extends MarketCommand {

    @Override
    public void execute(Market market) throws MarketError {
        try {
            UserActivity stats = market.getStats();
            System.out.println("You have sell " + stats.getSold() + " and bought " + stats.getBought() + " items");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
