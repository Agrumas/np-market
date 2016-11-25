package com.kth.np.market.server.user;

import com.kth.np.market.common.User;
import com.kth.np.market.common.exceptions.BankAccountNotAccesibleError;
import com.kth.np.market.common.exceptions.InsufficientFundsError;
import se.kth.id2212.ex2.bankrmi.Account;
import se.kth.id2212.ex2.bankrmi.Bank;
import se.kth.id2212.ex2.bankrmi.RejectedException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Created by Algirdas on 11/22/2016.
 */
public class BankService {
    HashMap<String, Bank> banks;

    public BankService() {
        banks = new HashMap<>();
    }

    public void transfer(User from, User to, int amount) throws BankAccountNotAccesibleError, InsufficientFundsError {
        Account accFrom;
        float balance,
                transAmount = (float) amount;
        try {
            accFrom = getAccount(from);
            balance = accFrom.getBalance();
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new BankAccountNotAccesibleError("Account balance can not be accessed");
        }
        if (balance < amount) {
            throw new InsufficientFundsError("Not enough money");
        }
        Account accTo = getAccount(to);
        try {
            accFrom.withdraw(transAmount);
            try {
                accTo.deposit(transAmount);
            } catch (RemoteException e) {
                accTo.deposit(transAmount);
                e.printStackTrace();
                throw new BankAccountNotAccesibleError("Can not deposit money");
            } catch (RejectedException e) {
                accTo.deposit(transAmount);
                e.printStackTrace();
                throw new InsufficientFundsError("Can not deposit specified amount");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new BankAccountNotAccesibleError("Can not withdraw money");
        } catch (RejectedException e) {
            e.printStackTrace();
            throw new InsufficientFundsError("Not enough money");
        }

    }

    protected Account getAccount(User user) throws BankAccountNotAccesibleError {
        Bank bank = getBank(user);
        if (bank == null) {
            throw new BankAccountNotAccesibleError("Can not access user account. Bank is offline.");
        }
        Account acc = null;
        try {
            acc = bank.getAccount(user.getBankAccount());
        } catch (RemoteException e) {
            banks.remove(user.getBank());
            e.printStackTrace();
        }
        if (acc == null) {
            throw new BankAccountNotAccesibleError("Can not access user account");
        }
        return acc;
    }

    protected Bank getBank(User user) {
        String bankName = user.getBank();
        Bank bank = banks.get(bankName);
        if (bank != null) {
            return bank;
        }

        try {
            bank = (Bank) Naming.lookup(bankName);
            banks.put(bankName, bank);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return bank;
    }
}
