package com.kth.np.market.common.cli;

/**
 * Created by Algirdas on 11/18/2016.
 */
public abstract class Command {
    protected String[] options;
    protected String name;
    protected CLI cli;

    public Command() { }

    public void init(CLI cli, String name, String[] options) {
        this.cli = cli;
        this.name = name;
        this.options = options;
    }

    public String getName() {
        return this.name;
    }
    public String[] getOptions() {
        return this.options;
    }
    public CLI getCli() {
        return cli;
    }

    public abstract void execute();

    public void error(String message){
        System.out.println(message);
    }

    public void info(String message){
        System.out.println(message);
    }
}