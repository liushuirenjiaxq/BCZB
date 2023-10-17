package com.bczb.controller;

import com.bczb.pojo.vo.Idname;
import com.bczb.pojo.vo.newoldpass;
import org.springframework.web.bind.annotation.*;
import com.bczb.pojo.Result;
import com.bczb.pojo.User;
import com.bczb.exceptions.BusinessException;
import com.bczb.IUserService;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Resource
    private IUserService userService;

    // 获取用户信息
    // 无参数
    @GetMapping("/")
    public Result getUserInfo(@RequestAttribute("uId") Integer uId) {
        //Integer uId = 45;
        if (uId == null) {
            return Result.error("用户未登录");
        }
        User user = userService.getUserInfo(uId);
        return Result.data(user);
    }

    // 修改用户信息
    // 类型：user
    // 例子：{id: user.id,name: username,tele: telephone}
    @PutMapping("/")
    public Result updateUserInfo(@RequestAttribute("uId") Integer uId, @RequestBody User user) throws BusinessException {
        if (uId == null) {
            return Result.error("用户未登录");
        }
        if(uId != user.getId()) {
            return Result.error("用户信息不匹配");
        }
        System.out.println(user.getId()+""+user.getName()+""+user.getTele());
        userService.updateUserInfo(user);
        return Result.success();
    }

    // 根据uId获取用户信息
    // 参数: int
    @GetMapping("/{uId}")
    public Result getUserInfoByUId(@PathVariable("uId") Integer uId) {
        User user = userService.getUserInfo(uId);
        return Result.data(user);
    }

    // 获取所有用户的姓名
    // 参数: 无
    @GetMapping("/usersName")
    public Result getCreateName() {
        Map<Integer,String> map = new HashMap<Integer,String>();
        ArrayList<Idname> list = userService.selectNames();
        //System.out.println(list);
        list.forEach(e -> {
            map.put(e.id,e.name);
        });
        return Result.data(map);
    }

    // 获取所有用户的姓名
    // 参数: 无
    @PostMapping("/updataPass")
    public Result updataPass(@RequestBody newoldpass data) throws BusinessException {
        //System.out.println(data.name+" "+data.new_pass+" "+data.old_pass);
        User user = this.userService.getUserByName(data.name);
        this.userService.equalPassword(data.getOld_pass(), user.getPassword());
        this.userService.updataPass(data.name, data.new_pass);
        return Result.success();
    }
}

