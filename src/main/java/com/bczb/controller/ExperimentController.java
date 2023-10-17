package com.bczb.controller;

import com.bczb.pojo.Experiment;
import com.bczb.pojo.Group.RatGroup;
import com.bczb.pojo.Result;
import com.bczb.exceptions.BusinessException;
import com.bczb.service.IExperimentService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("api/exp")

public class ExperimentController {

    @Resource
    private IExperimentService experimentService;

    @RequestMapping("/test")
    public Result hello() throws BusinessException {
        System.out.println("test");
        return Result.data("Hello World!");
    }


    // 创建实验
    // 参数: Json {string, string, int},
    // 示例: {"name": "测试实验3","ratName": "SD 大鼠","ownerId": 45}
    @PostMapping("/")
    public Result createExp(@RequestBody ExpParams expParams) throws BusinessException {
        this.experimentService.createExp(expParams.getName(),expParams.getRatName(), expParams.getOwnerId());
        Experiment experiment = this.experimentService.getExpByName(expParams.getName());
        this.experimentService.joinTeam(experiment.getExId(), expParams.getOwnerId());
        return Result.success();
    }

    // 获取实验列表
    // 无参数
    @GetMapping("/")
    public Result getExpList() {
        ArrayList<Experiment> expList = this.experimentService.getAllExp();
        return Result.data(expList);
    }

    // 通过uId获取实验列表
    // 参数: int, 需为登录的账号的 uId
    @GetMapping("user/{uId}")
    public Result getExpListByUId(@PathVariable("uId") Integer uId, @RequestAttribute("uId") Integer uId2) {
        System.out.println("////// uId " + uId2);
        if(!uId2.equals(uId)) {
            return Result.error("用户信息不匹配");
        }
        if(uId == null) {
            return Result.error("参数错误");
        }
        ArrayList<Experiment> expList = this.experimentService.getExpListByUId(uId);
        return Result.data(expList);
    }

    // 查询实验信息
    // 参数: int, 示例: 23
    @GetMapping("/{exId}")
    public Result getExpByExId(@PathVariable("exId") Integer exId) {
        if(exId == null) {
            return Result.error("参数错误");
        }
        Experiment experiment = this.experimentService.getExpByExId(exId);
        return Result.data(experiment);
    }

    // 修改实验信息
    // 参数: Json {int, String, String, String, String, int, int},
    // 示例: {"exId":23,"name":"测试实验1","startDate":"2023-07-18","endDate":null,"ratName":"SD 大鼠","status":1,"ownerId":45}
    @PutMapping("/")
    public Result updateExp(@RequestBody Experiment experiment, @RequestAttribute("uId") Integer uId) throws BusinessException  {
        if(experiment.getOwnerId() != uId.intValue()){
            return Result.error("只有实验创建者才能修改实验信息");
        }
        this.experimentService.updateRuningExp(experiment.getExId(), experiment.getName(), experiment.getStartDate(),  experiment.getRatName());
        return Result.success();
    }
    // 修改实验状态
    // 参数: int, int , 示例: 23/0
    @PutMapping("/{exId}/{status}")
    public Result updateExpStatus(@PathVariable("exId") Integer exId, @PathVariable("status") Integer status) throws BusinessException  {
        // 判断exId和status是否为数字
        if(exId == null || status == null) {
            return Result.error("参数错误");
        }
        this.experimentService.updateExpStatus(exId, status);
        return Result.success();
    }

    // 删除实验
    // 参数: int, 示例: 23
    @DeleteMapping("/{exId}")
    public Result deleteExp(@PathVariable("exId") Integer exId,@RequestAttribute("uId") Integer uId ) throws BusinessException  {
        if(this.experimentService.getExpByExId(exId).getOwnerId() != uId.intValue()){
            return Result.error("只有实验创建者才能删除实验");
        }
        // 判断exId和status是否为数字
        if(exId == null) {
            return Result.error("参数错误");
        }
        this.experimentService.deleteExp(exId);
        return Result.success();
    }

    //* 没写完的接口
    // 创建实验组, 参数: Json {}
    @PostMapping("/group/{exId}")
    public Result createGroup(@PathVariable("exId") Integer exId, @RequestBody RatGroup ratGroup) throws BusinessException {
        
        return Result.success();
    }

    public static class ExpParams {
        public String name;
        public String ratName;
        public int ownerId;
        
        public ExpParams() {
        }
        
        public ExpParams(String name, String ratName, int ownerId) {
            this.name = name;
            this.ratName = ratName;
            this.ownerId = ownerId;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getRatName() {
            return ratName;
        }
        public void setRatName(String ratName) {
            this.ratName = ratName;
        }
        public int getOwnerId() {
            return ownerId;
        }
        public void setOwnerId(int ownerId) {
            this.ownerId = ownerId;
        }
        
    }



}
