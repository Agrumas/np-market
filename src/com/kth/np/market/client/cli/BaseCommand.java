package com.kth.np.market.client.cli;

import com.kth.np.market.client.ClientApp;
import com.kth.np.market.common.cli.CLI;
import com.kth.np.market.common.cli.Command;

/**
 * Created by Algirdas on 11/19/2016.
 */
public abstract class BaseCommand extends Command {
    ClientApp app;
    ClientCLI cCLI;
    @Override
    public void execute() {
        cCLI = (ClientCLI) getCli();
        app = cCLI.getApp();
        boolean prepared = false;
        try {
            prepared = prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(prepared){
            execute(cCLI);
        } else {
            printUsage();
        }
    }

    abstract public void execute(ClientCLI cli);

    public boolean prepare() {
        return true;
    };

    public void printUsage() {
        System.out.println("USAGE: " + getName());
    };

    public ClientApp getApp() {
        return app;
    }

    public ClientCLI getClientCLI() {
        return cCLI;
    }
}
