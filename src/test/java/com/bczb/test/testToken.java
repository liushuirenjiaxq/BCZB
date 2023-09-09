package com.bczb.test;

import ch.qos.logback.core.net.SMTPAppenderBase;
import com.bczb.IUserService;
import com.bczb.exceptions.BusinessException;
import com.bczb.impl.UserServiceImpl;
import com.bczb.pojo.User;
import com.bczb.utils.TokenUtils;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

public class testToken {
    private IUserService userService = new UserServiceImpl();
    @Test
    public void test() throws BusinessException {
        User user = this.userService.getUserByName("测试1");
        String token = TokenUtils.createToken(user);
        System.out.println(token);
    }
}
