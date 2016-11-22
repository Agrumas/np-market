package com.kth.np.market.client.commands;

import com.kth.np.market.client.ClientApp;
import com.kth.np.market.client.cli.TradingMarketCommand;
import com.kth.np.market.common.Market;
import com.kth.np.market.common.TradingMarket;
import com.kth.np.market.common.exceptions.InvalidCredentialsError;
import com.kth.np.market.common.exceptions.MarketError;

import java.rmi.RemoteException;

/**
 * Created by Algirdas on 11/21/2016.
 */
public class LoginCommand extends TradingMarketCommand {
    protected String username = null;
    protected String password = null;

    @Override
    public void execute(TradingMarket tradingMarket) throws MarketError {
        try {
            Market market = tradingMarket.login(username, password);
            ClientApp app = getApp();
            app.setMarket(market);
            market.subscribeEvents(app.getEventSubscriber().getSubscriptions());
            getClientCLI().setPrompt(username);
            info("You have logged in");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InvalidCredentialsError invalidCredentialsError) {
            error("Invalid credentials.");
        }
    }

    @Override
    public boolean prepare() {
        if (options.length < 2) {
            return false;
        }
        username = options[0].trim();
        password = options[1].trim();

        return !username.isEmpty() && !password.isEmpty();
    }

    @Override
    public void printUsage() {
        System.out.println("Usage: " + getName() + " username password");
    }
}
