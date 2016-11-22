package com.kth.np.market.client.commands;

import com.kth.np.market.client.cli.MarketCommand;
import com.kth.np.market.common.Market;
import com.kth.np.market.common.exceptions.MarketError;

import java.rmi.RemoteException;

/**
 * Created by Algirdas on 11/21/2016.
 */
public class ListCommand extends MarketCommand {

    @Override
    public void execute(Market market) throws MarketError {
        try {
            info("Items in market:");
            String rowFormat = "| %-15s | %-7d | %-10s |%n";
            System.out.format("+-----------------+---------+------------+%n");
            System.out.format("| Name            | Price   | Owner      |%n");
            System.out.format("+-----------------+---------+------------+%n");
            market.list().forEach(item -> System.out.format(rowFormat, item.getName(), item.getPrice(), item.getOwner()));
            System.out.format("+-----------------+---------+------------+%n");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
