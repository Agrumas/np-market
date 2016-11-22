package com.kth.np.market.client.cli;

import com.kth.np.market.common.Market;
import com.kth.np.market.common.exceptions.MarketError;

/**
 * Created by Algirdas on 11/19/2016.
 */
public abstract class MarketCommand extends BaseCommand {
    @Override
    public void execute(ClientCLI cli) {
        Market market = cli.getApp().getMarket();
        if (market == null) {
            System.out.println("Please log-in to Trading Market");
            return;
        }

        try {
            execute(market);
        } catch (MarketError e) {
            error(e.getMessage());
        }
    }

    abstract public void execute(Market market) throws MarketError;
}
