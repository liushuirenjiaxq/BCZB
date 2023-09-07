package com.bczb.controller;

import com.bczb.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bczb.pojo.Result;
import com.bczb.pojo.User;
import com.bczb.exceptions.BusinessException;
import com.bczb.exceptions.SqlException;
import com.bczb.service.IUserService;
import com.bczb.utils.TokenUtils;
import javax.annotation.Resource;

@RestController
@RequestMapping("api/signin")
public class SignInController {

    @GetMapping("/test")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping("/test2")
    public String enco(@RequestBody LoginParam loginParam) throws BusinessException {
        System.out.println(loginParam.getName());
        System.out.println(loginParam.getPassword());

        User user = this.userService.getUserInfo(45);
        System.out.println(user.toString());
        //User user = this.userService.getUserByName(loginParam.name);
        //this.userService.equalPassword(loginParam.getPassword(), user.getPassword());
        //String token = TokenUtils.createToken(user);

        return "OK";
    }

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public Result signIn(@RequestBody LoginParam loginParam) throws BusinessException {
        User user = this.userService.getUserByName(loginParam.name);
        this.userService.equalPassword(loginParam.getPassword(), user.getPassword());
        String token = TokenUtils.createToken(user);
        return Result.data(token);
    }

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
