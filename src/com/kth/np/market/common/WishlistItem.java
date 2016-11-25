package com.kth.np.market.common;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Algirdas on 11/24/2016.
 */
@Entity
@Table(name = "wishlist_items", schema = "market", catalog = "")
@IdClass(WishlistItemPK.class)
public class WishlistItem implements Serializable {
    private String name;
    private int price;
    private String owner;
    transient private User user;


    public WishlistItem() {
    }

    public WishlistItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public WishlistItem(String owner, String name, int price) {
        this.name = name;
        this.price = price;
        this.owner = owner;
    }

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Id
    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setOwner(User owner) {
        setOwner(owner.getId());
    }

    public boolean isMatching(Item item) {
        return name.equals(item.getName()) && price >= item.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WishlistItem that = (WishlistItem) o;

        if (price != that.price) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + price;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
