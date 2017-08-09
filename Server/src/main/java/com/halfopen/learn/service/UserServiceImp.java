package com.halfopen.learn.service;

import com.halfopen.learn.dao.user.UserDao;
import com.halfopen.learn.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImp implements UserService {
    @Resource
    private  UserDao userDao;
    public User getUser(String name, String password) {
        return userDao.getUser(name, password);
    }
}
