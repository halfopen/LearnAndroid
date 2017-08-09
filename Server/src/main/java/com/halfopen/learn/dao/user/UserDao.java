package com.halfopen.learn.dao.user;

import com.halfopen.learn.model.User;

/**
 * Created by h on 2017/8/9.
 */
public interface UserDao {
    public User getUser(String name, String password);
}
