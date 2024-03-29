package se.kth.id2212.ex2.bankrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class AccountImpl extends UnicastRemoteObject implements Account {
    private float balance = 0;
    private String name;

    /**
     * Constructs a persistently named object.
     */
    public AccountImpl(String name) throws RemoteException {
        super();
        this.name = name;
    }

    @Override
    public synchronized void deposit(float value) throws RemoteException,
                                                         RejectedException {
        if (value < 0) {
            throw new RejectedException("Rejected: Market " + name + ": Illegal value: " + value);
        }
        balance += value;
        System.out.println("Transaction: Market " + name + ": deposit: $" + value + ", balance: $"
                           + balance);
    }

    @Override
    public synchronized void withdraw(float value) throws RemoteException,
                                                          RejectedException {
        if (value < 0) {
            throw new RejectedException("Rejected: Market " + name + ": Illegal value: " + value);
        }
        if ((balance - value) < 0) {
            throw new RejectedException("Rejected: Market " + name
                                        + ": Negative balance on withdraw: " + (balance - value));
        }
        balance -= value;
        System.out.println("Transaction: Market " + name + ": withdraw: $" + value + ", balance: $"
                           + balance);
    }

    @Override
    public synchronized float getBalance() throws RemoteException {
        return balance;
    }
}
