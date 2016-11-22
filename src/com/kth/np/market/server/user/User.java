package com.kth.np.market.server.user;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class User {
    protected String name;
    protected String bank;
    protected String bankAccount;

    public User(String name, String bank) {
        this.name = name;
        this.bank = bank;
        this.bankAccount = name;
    }

    public String getId() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getBank() {
        return bank;
    }

    public String getBankAccount() {
        return bankAccount;
    }
}
