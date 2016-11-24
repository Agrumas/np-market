package com.kth.np.market.common;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Algirdas on 11/18/2016.
 */
@Entity
@Table(name = "items", schema = "market", catalog = "")
public class Item implements Serializable {
    protected String id;
    protected String name;
    protected int price;
    protected String owner;
    private int amount;

    public Item() {

    }
    public Item(String name, int price) {
        this(name, price, null);
    }

    public Item(String name, int price, String owner) {
        this.name = name;
        this.price = price;
        this.owner = owner;
        id = UUID.randomUUID().toString();
    }

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "owner")
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

    public String toStr(){
        return name + "($"+price+")";
    }

    @Basic
    @Column(name = "amount")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (price != item.price) return false;
        if (amount != item.amount) return false;
        if (id != null ? !id.equals(item.id) : item.id != null) return false;
        if (name != null ? !name.equals(item.name) : item.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + amount;
        return result;
    }
}
