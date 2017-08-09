package com.halfopen.learn.service;

import com.halfopen.learn.model.User;
import org.springframework.stereotype.Service;

/**
 * Created by h on 2017/8/9.
 */
public interface UserService {

    public User getUser(String name,String password);
}
