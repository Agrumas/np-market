package com.kth.np.market.common.exceptions;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class ItemNotFoundError extends MarketError {
    public ItemNotFoundError() {
        super();
    }
    public ItemNotFoundError(String message) {
        super(message);
    }
}
