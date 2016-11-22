package com.kth.np.market.common.exceptions;

/**
 * Created by Algirdas on 11/21/2016.
 */
public class BankAccountNotAccesibleError extends MarketError {
    public BankAccountNotAccesibleError() {
        super();
    }
    public BankAccountNotAccesibleError(String message) {
        super(message);
    }
}