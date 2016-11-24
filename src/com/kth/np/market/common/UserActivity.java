package com.kth.np.market.common;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Algirdas on 11/22/2016.
 */
@Entity
@Table(name = "user_activity", schema = "market", catalog = "")
public class UserActivity implements Serializable {
    private User user;
    private String id;
    private int sold = 0;
    private int bought = 0;


    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User usersById) {
        this.user = usersById;
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
    @Column(name = "sold")
    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    @Basic
    @Column(name = "bought")
    public int getBought() {
        return bought;
    }

    public void setBought(int bought) {
        this.bought = bought;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserActivity that = (UserActivity) o;

        if (sold != that.sold) return false;
        if (bought != that.bought) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + sold;
        result = 31 * result + bought;
        return result;
    }
}
