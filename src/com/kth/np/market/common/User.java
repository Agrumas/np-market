package com.kth.np.market.common;

import javax.persistence.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

/**
 * Created by Algirdas on 11/18/2016.
 */
@Entity
@Table(name = "users", schema = "market", catalog = "")
public class User {
    protected String name;
    protected String bank;
    protected String bankAccount;
    private String id;
    transient private String password;
    private UserActivity userActivity;
    private Collection<WishlistItem> wishlistItems;

    public User() {
    }

    public User(String name, String password, String bank) {
        this.id = name;
        this.name = name;
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        this.password = String.format("%064x", new BigInteger(1, hash));
        this.bank = bank;
        this.bankAccount = name;
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
    @Column(name = "bank")
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Basic
    @Column(name = "bankAccount")
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordTheSame(String plain) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(plain.getBytes(StandardCharsets.UTF_8));
        return this.password.equals(String.format("%064x", new BigInteger(1, hash)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (bank != null ? !bank.equals(user.bank) : user.bank != null) return false;
        if (bankAccount != null ? !bankAccount.equals(user.bankAccount) : user.bankAccount != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (bank != null ? bank.hashCode() : 0);
        result = 31 * result + (bankAccount != null ? bankAccount.hashCode() : 0);
        return result;
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch=FetchType.EAGER)
    public UserActivity getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(UserActivity userActivity) {
        if (userActivity != null && id != null && !id.equals(userActivity.getId())) {
            userActivity.setId(id);
        }
        this.userActivity = userActivity;
    }

    @OneToMany(mappedBy = "user")
    public Collection<WishlistItem> getWishlistItems() {
        return wishlistItems;
    }

    public void setWishlistItems(Collection<WishlistItem> wishlistItems) {
        this.wishlistItems = wishlistItems;
    }
}
