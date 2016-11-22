package com.kth.np.market.common.exceptions;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class MarketError extends Exception {
    public MarketError() {
        super();
    }
    public MarketError(String message) {
        super(message);
    }
}
