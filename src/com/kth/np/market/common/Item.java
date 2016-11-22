package com.kth.np.market.common;

import com.kth.np.market.server.user.User;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class Item implements Serializable {
    protected String id;
    protected String name;
    protected int price;

    protected String owner;

    public Item(String name, int price) {
        this(name, price, null);
    }

    public Item(String name, int price, String owner) {
        this.name = name;
        this.price = price;
        this.owner = owner;
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setOwner(User owner) {
        this.owner = owner.getId();
    }

    /**
     * Checks if passed item has the same name and price
     *
     * @param item
     * @return
     */
    public boolean isSameItem(Item item) {
        return name.equals(item.getName()) && price == item.getPrice();
    }

    public boolean isSameCheaperThan(Item item) {
        return name.equals(item.getName()) && price <= item.getPrice();
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", owner='" + owner + '\'' +
                '}';
    }
}
