package com.bczb.controller;

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
import com.bczb.IUserService;
import javax.annotation.Resource;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Resource
    private IUserService userService;

    // 获取用户信息 无参数
    @GetMapping("/")
    public Result getUserInfo(@RequestAttribute("uId") Integer uId) {
        //Integer uId = 45;
        if (uId == null) {
            return Result.error("用户未登录");
        }
        User user = userService.getUserInfo(uId);
        return Result.data(user);
    }

    // 修改用户信息 无参数
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

    // 根据uId获取用户信息 参数: int
    @GetMapping("/{uId}")
    public Result getUserInfoByUId(@PathVariable("uId") Integer uId) {
        User user = userService.getUserInfo(uId);
        return Result.data(user);
    }

}

