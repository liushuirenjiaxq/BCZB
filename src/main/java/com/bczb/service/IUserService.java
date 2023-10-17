package com.bczb;

import com.bczb.pojo.vo.Idname;
import org.springframework.stereotype.Service;
import com.bczb.pojo.User;
import com.bczb.exceptions.BusinessException;
import com.bczb.exceptions.SqlException;

import java.util.ArrayList;

@Service
public interface IUserService {

    public boolean isExist(String name);

    public void addUser(User user) throws SqlException;

    public User getUserByName(String name) throws BusinessException;

    public String addSalt(String password);

    public void equalPassword(String formPassword, String userPassoword) throws BusinessException;

    public User getUserInfo(Integer uId);

    public void updateUserInfo(User user) throws BusinessException;

    public ArrayList<Idname> selectNames();

    public void updataPass(String name, String pass);
}
