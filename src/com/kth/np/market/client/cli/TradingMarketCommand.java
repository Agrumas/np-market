package com.kth.np.market.client.cli;

import com.kth.np.market.common.TradingMarket;
import com.kth.np.market.common.exceptions.MarketError;

/**
 * Created by Algirdas on 11/19/2016.
 */
public abstract class TradingMarketCommand extends BaseCommand {
    @Override
    public void execute(ClientCLI cli) {
        TradingMarket globalMarket = cli.getApp().getGlobalMarket();
        try {
            execute(globalMarket);
        } catch (MarketError e) {
            error(e.getMessage());
        }
    }

    abstract public void execute(TradingMarket cli) throws MarketError;
}
