package com.kth.np.market.client.commands;

import com.kth.np.market.client.cli.MarketCommand;
import com.kth.np.market.common.Market;
import com.kth.np.market.common.exceptions.MarketError;

import java.rmi.RemoteException;

/**
 * Created by Algirdas on 11/21/2016.
 */
public class WishlistCommand extends MarketCommand {

    @Override
    public void execute(Market market) throws MarketError {
        try {
            info("Items in wishlist:");
            market.getWishlist().forEach(item -> info(" " + item));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
