package com.bczb.controller;

import com.bczb.pojo.Group;
import com.bczb.pojo.Group.RatGroup;
import com.bczb.pojo.Result;
import com.bczb.exceptions.BusinessException;
import com.bczb.service.IExperimentService;
import com.bczb.service.IGroupService;
import com.bczb.service.IRatService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("api/group")
public class GroupController {

    @Resource
    private IGroupService groupService;

    @Resource
    private IRatService ratService;

    @Resource
    private IExperimentService experimentService;

    // 添加组别
    @PostMapping("/")
    public Result addGroups(@RequestBody ArrayList<RatGroup> groups) throws BusinessException {
        try {
            for (RatGroup group : groups) {
                String id = this.groupService.generateId();
                this.groupService.addGroup(id,group.getExId(), group.getName());
                group.getRats().forEach(rat -> {
                    this.ratService.addRat(id, rat.getGender(), rat.getCage(), rat.getIndex());
                });
            }
            experimentService.updateExpStatus(groups.get(0).getExId(), 1);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }


    // 查询一个实验的组别
    @GetMapping("/{exId}")
    public Result getExpGroups(@PathVariable("exId") Integer exId) throws BusinessException {
        boolean isExist = this.groupService.isExpExist(exId);
        if (!isExist) {
            return Result.error("实验不存在");
        }
        ArrayList<Group> groups = this.groupService.getGroup(exId);
        return Result.data(groups);
    }

    // 查询所有组别
    @GetMapping("/")
    public Result getAllGroups() {
        ArrayList<Group> groups = this.groupService.getAllGroups();
        return Result.data(groups);
    }

    
}
