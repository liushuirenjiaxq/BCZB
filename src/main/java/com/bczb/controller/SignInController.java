package com.bczb.controller;

import com.bczb.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bczb.pojo.User;
import com.bczb.exceptions.BusinessException;
import com.bczb.exceptions.SqlException;
import com.bczb.IUserService;
import com.bczb.utils.TokenUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/signin")
public class SignInController {

    @GetMapping("/test")
    public String hello() {
        System.out.println("test");
        return "Hello World!";
    }

    @Resource
    private IUserService userService;

    // 登录验证
    // 传入参数: Json {String, String}，
    // 示例: {"name": "测试1","password": "123"}
    @PostMapping("/login")
    public Result signIn(@RequestBody LoginParam loginParam) throws BusinessException {
        User user = this.userService.getUserByName(loginParam.name);
        this.userService.equalPassword(loginParam.getPassword(), user.getPassword());
        String token = TokenUtils.createToken(user);
        return Result.data(token);
    }

    // 注册接口
    // 参数: Json {String, String, String, int}
    // 示例: {"name": "测试3","password": "123","tele": "11","power": 0}
    @PostMapping("/register")
    public Result signUp(@RequestBody RegisterParams params) throws SqlException {
        boolean isExist = this.userService.isExist(params.name);
        if (isExist) {
            return Result.error("账号已存在");
        }
        params.password = this.userService.addSalt(params.password);
        User user = new User(null, params.password, params.name, params.power, params.tele, 0);
        this.userService.addUser(user);
        return Result.success();
    }

    // 退出登录
    @PostMapping("/exit")
    public Result logout() {
        return Result.data("成功");
    }

    static class LoginParam {
        public String name;
        public String password;

        public LoginParam(String name, String password) {
            this.name = name;
            this.password = password;
        }

        public LoginParam() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }

    static class RegisterParams {
        public String password;
        public String name;
        public String tele;
        public int power;

        public RegisterParams(String password, String name, String tele, int power) {
            this.password = password;
            this.name = name;
            this.tele = tele;
            this.power = power;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTele() {
            return tele;
        }

        public void setTele(String tele) {
            this.tele = tele;
        }

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }

    }
}
