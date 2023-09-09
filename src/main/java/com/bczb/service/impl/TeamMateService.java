package com.bczb.service.impl;

import com.bczb.pojo.TeamMate;
import com.bczb.pojo.User;
import com.bczb.exceptions.BusinessException;
import com.bczb.dao.ExperimentMapper;
import com.bczb.dao.TeamMateMapper;
import com.bczb.dao.UserMapper;
import com.bczb.service.ITeamMateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class TeamMateService implements ITeamMateService {

    @Resource
    private TeamMateMapper teamMapper;

    @Resource
    private ExperimentMapper experimentMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public void joinTeam(Integer exId, String name) throws BusinessException {
        if (this.experimentMapper.isExpExist(exId) == false) {
            throw new BusinessException("实验不存在");
        }
        User user = this.userMapper.selectByName(name);
        if(user == null) {
            throw new BusinessException("用户不存在");
        }
        if(this.teamMapper.isTeamMateExistByExIdAndUId(exId, user.getId()) == true) {
            throw new BusinessException("团队成员已存在");
        }

        this.teamMapper.InsertTeam(exId, user.getId());
    }

    @Override
    public void quitTeam(Integer id) throws BusinessException {
        if (this.teamMapper.isTeamMateExist(id) == false) {
            throw new BusinessException("团队成员不存在");
        }
        this.teamMapper.deleteTeamMate(id);
    }

    @Override
    public ArrayList<TeamMate> getTeamMates(Integer exId) throws BusinessException {
        if(this.experimentMapper.isExpExist(exId) == false) {
            throw new BusinessException("实验不存在");
        }
        return this.teamMapper.selectAllTeamMateByExId(exId);
    }

    @Override
    public ArrayList<TeamMate> getAllTeamMates() {
        return this.teamMapper.selectAllTeamMate();
    }
}
