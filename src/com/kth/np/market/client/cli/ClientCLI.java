package com.kth.np.market.client.cli;

import com.kth.np.market.client.ClientApp;
import com.kth.np.market.common.TradingMarket;
import com.kth.np.market.common.cli.CLI;

/**
 * Created by Algirdas on 11/19/2016.
 */
public class ClientCLI extends CLI {
    ClientApp app;

    public ClientCLI(ClientApp app) {
        super();
        this.app = app;
    }

    public ClientApp getApp() {
        return app;
    }
}
