package com.kth.np.market.common.cli;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class UsageCommand extends Command {
    @Override
    public void execute() {
        info("Command not found! Available commands:");
        info(String.join(", ", getCli().getAvailableCommands()));
    }
}
