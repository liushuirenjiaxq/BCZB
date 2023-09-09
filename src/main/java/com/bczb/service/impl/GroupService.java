package com.bczb.service.impl;

import com.bczb.pojo.Group;
import com.bczb.exceptions.BusinessException;
import com.bczb.dao.GroupMapper;
import com.bczb.service.IGroupService;
import com.bczb.utils.SnowFlake;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class GroupService implements IGroupService {

    @Resource
    private GroupMapper groupMapper;

    @Override
    public void addGroup(String id ,Integer exId, String name) throws BusinessException {
        if(this.groupMapper.isGroupNameExist(exId, name) == true) {
            throw new BusinessException("组名已存在");
        }
        if(this.groupMapper.isExpExist(exId) == false) {
            throw new BusinessException("实验不存在");
        }
        this.groupMapper.insertGroup(id,exId, name);
    }

    @Override
    public boolean isGroupNameExist(Integer exId, String name) throws BusinessException {
        if(this.groupMapper.isExpExist(exId) == false) {
            throw new BusinessException("实验不存在");
        }
        return groupMapper.isGroupNameExist(exId, name);
    }

    @Override
    public boolean isGroupExist(String gId) {
        return this.groupMapper.isGroupExist(gId);
    }

    // @Override
    // public void deleteGroup(String gId) throws BusinessException {
    //     if(this.groupMapper.isGroupExist(gId) == false) {
    //         throw new BusinessException("组别不存在");
    //     }
    //     this.groupMapper.deleteGroup(gId);
    // }

    @Override
    public boolean isExpExist(Integer exId) {
        return this.groupMapper.isExpExist(exId);
    }

    @Override
    public ArrayList<Group> getGroup(Integer exId) throws BusinessException {
        if(this.groupMapper.isExpExist(exId) == false) {
            throw new BusinessException("实验不存在");
        }
        return this.groupMapper.selectGroupByExId(exId);
    }

    @Override
    public void updateGroup(String gId, String name) throws BusinessException {
        if(this.groupMapper.isGroupExist(gId) == false) {
            throw new BusinessException("组别不存在");
        }
        if(this.groupMapper.isExistByGIdAndName(gId, name) == true) {
            throw new BusinessException("组名已存在");
        }
        this.groupMapper.updateGroup(gId, name);
    }

    @Override
    public ArrayList<Group> getAllGroups() {
        return this.groupMapper.selectAllGroups();
    }

    @Override
    public String generateId() {
        return Long.toString(SnowFlake.getSnowFlakeId());
    }
    
}
