package com.kth.np.market.client.cli;

import com.kth.np.market.common.Item;

/**
 * Created by Algirdas on 11/26/2016.
 */
public class ItemHelper {
    public static Item readFromOptions(String[] options) {
        if (options.length < 2) {
            return null;
        }
        String itemName = options[0].trim();
        int price = new Integer(options[1].trim().replaceAll(" ", "_"));
        int amount = 1;

        if (options.length == 3) {
            amount = new Integer(options[2].trim());
            if (amount < 1) {
                return null;
            }
        }

        return !itemName.isEmpty() && price > 0 ? new Item(itemName, price, amount) : null;
    }
}
