package com.bczb.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bczb.pojo.Result;
import com.bczb.pojo.User;
import com.bczb.exceptions.BusinessException;
import com.bczb.service.IUserService;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Resource
    private IUserService userService;

    // 获取用户信息
    @GetMapping("/")
    public Result getUserInfo(@RequestAttribute("uId") Integer uId) {
        if (uId == null) {
            return Result.error("用户未登录");
        }
        User user = userService.getUserInfo(uId);
        return Result.data(user);
    }

    // 修改用户信息
    @PutMapping("/")
    public Result updateUserInfo(@RequestAttribute("uId") Integer uId, @RequestBody User user) throws BusinessException {
        if (uId == null) {
            return Result.error("用户未登录");
        }
        if(uId != user.getId()) {
            return Result.error("用户信息不匹配");
        }
        userService.updateUserInfo(user);
        return Result.success();
    }

    // 根据uId获取用户信息
    @GetMapping("/{uId}")
    public Result getUserInfoByUId(@PathVariable("uId") Integer uId) {
        User user = userService.getUserInfo(uId);
        return Result.data(user);
    }

}

