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

    // 添加组别 参数: Json [{int, String, [{String, int, String}]}]
    // 示例: [{"exId": 25, "name": "三七组", "rats": [{"gender": "male", "cage": 1, "index": "33" }, { "gender": "female", "cage": 2, "index": "11"}]}, {"exId": 25, "name": "藏红花组", "rats": [{"gender": "male", "cage": 1, "index": "22"}, {"gender": "female", "cage": 2, "index": "77"}]}]
    @PostMapping("/")
    public Result addGroups(@RequestBody ArrayList<RatGroup> groups) throws BusinessException {
        System.out.println(groups);
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


    // 查询一个实验的组别 参数: int 示例: 23
    @GetMapping("/{exId}")
    public Result getExpGroups(@PathVariable("exId") Integer exId) throws BusinessException {
        boolean isExist = this.groupService.isExpExist(exId);
        if (!isExist) {
            return Result.error("实验不存在");
        }
        ArrayList<Group> groups = this.groupService.getGroup(exId);
        return Result.data(groups);
    }

    // 查询所有组别 无参数
    @GetMapping("/")
    public Result getAllGroups() {
        ArrayList<Group> groups = this.groupService.getAllGroups();
        return Result.data(groups);
    }

    
}
