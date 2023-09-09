package com.bczb.service;

import com.bczb.pojo.Group;
import com.bczb.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface IGroupService {
    public void addGroup(String id,Integer exId, String name) throws BusinessException;

    public boolean isGroupNameExist(Integer exId, String name) throws BusinessException;

    public boolean isGroupExist(String gId);

    // public void deleteGroup(String gId) throws BusinessException;

    public boolean isExpExist(Integer exId);

    public ArrayList<Group> getGroup(Integer exId) throws BusinessException;

    public void updateGroup(String gId, String name) throws BusinessException;

    public ArrayList<Group> getAllGroups();

    public String generateId();

}
