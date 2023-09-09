package com.bczb.controller;

import com.bczb.pojo.Result;
import com.bczb.exceptions.BusinessException;
import com.bczb.service.ITeamMateService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("api/team")
public class TeamMateController {
    
    @Resource
    private ITeamMateService teamMateService;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddTeamMateParams {
        private Integer exId;
        private String teamMateName;
    }

    // 加入团队
    @PostMapping("/")
    public Result joinTeam(@RequestBody AddTeamMateParams addTeamMateParams) throws BusinessException  {
        
        this.teamMateService.joinTeam(addTeamMateParams.exId, addTeamMateParams.teamMateName);
        return Result.success();
    }

    // 退出团队
    @DeleteMapping("/{id}")
    public Result quitTeam(@PathVariable("id") Integer id) throws BusinessException  {
        // 判断exId和status是否为数字
        if(id== null) {
            return Result.error("参数错误");
        }
        this.teamMateService.quitTeam(id);
        return Result.success();
    }


    // 获取团队成员信息
    @GetMapping("/{exId}")
    public Result getTeamMates(@PathVariable("exId") Integer exId) throws BusinessException  {
        // 判断exId和status是否为数字
        if(exId == null) {
            return Result.error("参数错误");
        }
        return Result.data(this.teamMateService.getTeamMates(exId));
    }

    // 获取所有团队成员信息
    @GetMapping("/")
    public Result getAllTeamMates() throws BusinessException  {
        return Result.data(this.teamMateService.getAllTeamMates());
    }
}
