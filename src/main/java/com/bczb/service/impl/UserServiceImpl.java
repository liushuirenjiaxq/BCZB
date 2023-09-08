package com.bczb.service.impl;

import com.bczb.dao.UserMapper;
import com.bczb.exceptions.BusinessException;
import com.bczb.exceptions.SqlException;
import com.bczb.pojo.User;
import com.bczb.service.IUserService;

import com.bczb.utils.SaltMD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isExist(String name) {
        int count = this.userMapper.countByName(name);
        return count > 0;
    }



    @Override
    public void addUser(User user) throws SqlException {
        try {
            this.userMapper.InsertUser(user);
        } catch (Exception e) {
            throw new SqlException("数据库错误");
        }
    }

    @Override
    public User getUserByName(String name) throws BusinessException {
        System.out.println("service_1: ok");
        User user = this.userMapper.selectByName(name);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }

    @Override
    public String addSalt(String password) {
        return SaltMD5Util.generateSaltPassword(password);
    }

    @Override
    public void equalPassword(String formPassword, String userPassword) throws BusinessException {
        if (!SaltMD5Util.verifySaltPassword(formPassword, userPassword)) {
            throw new BusinessException("密码错误");
        }
    }

    @Override
    public User getUserInfo(Integer uId) {
        User user = this.userMapper.selectById(uId);
        user.setPassword(null);
        return user;
    }

    @Override
    public void updateUserInfo(User user) throws BusinessException {
        User oldUser = this.userMapper.selectByName(user.getName());
        if( oldUser != null && !oldUser.getId().equals(user.getId())) {
            throw new BusinessException("用户名已存在");
        }
        this.userMapper.updateUser(user.getId(), user.getName(), user.getTele());
    }
}
