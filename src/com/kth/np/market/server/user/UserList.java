package com.kth.np.market.server.user;

import java.util.HashMap;

/**
 * Created by Algirdas on 11/18/2016.
 */
public class UserList {
    HashMap<String, User> list;

    public UserList() {
        this.list = new HashMap<>();
    }

    public User get(String id){
        return list.get(id);
    }

    public boolean exists(String id){
        return list.containsKey(id);
    }

    public synchronized User register(String name, String bank) {
        User u = new User(name, bank);
        list.put(u.getId(), u);
        return u;
    }
}
