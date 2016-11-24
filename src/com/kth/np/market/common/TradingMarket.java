package com.kth.np.market.common;

import com.kth.np.market.common.exceptions.InvalidCredentialsError;
import com.kth.np.market.common.exceptions.UserIsRegistredError;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Algirdas on 11/18/2016.
 */
public interface TradingMarket extends Remote {
    public final String NAME = "GlobalMarket";
    public Market register(String name, String password, String bank) throws RemoteException, UserIsRegistredError, InvalidCredentialsError;
    public Market login(String name, String pass) throws RemoteException, InvalidCredentialsError;

}
