package com.bczb.service.impl;

import com.bczb.pojo.Experiment;
import com.bczb.exceptions.BusinessException;
import com.bczb.dao.ExperimentMapper;
import com.bczb.service.IExperimentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;

@Service
public class ExperimentServiceImpl implements IExperimentService {

    @Resource
    private ExperimentMapper experimentMapper;

    @Override
    public void createExp(String name, String ratName, int ownerId) throws BusinessException {
        Experiment experiment = this.experimentMapper.selectByName(name);
        if(experiment != null) {
            throw new BusinessException("实验已存在");
        }
        if(this.experimentMapper.isOwnerExist(ownerId) == false) {
            throw new BusinessException("用户不存在");
        }
        Date nowDate = new Date(System.currentTimeMillis());
        // yyyy-MM-dd
         String nowDateStr = nowDate.toString();
        this.experimentMapper.InsertExp(name, nowDateStr, ratName, ownerId);
    }

    @Override
    public ArrayList<Experiment> getAllExp() {
        return this.experimentMapper.selectExpList();
    }

    @Override
    public void updateRuningExp(int exId, String name, String startDate, String ratName) throws BusinessException {
        boolean isExist = this.experimentMapper.isExpExist(exId);
        if(isExist == false) {
            throw new BusinessException("实验不存在");
        }
        String nowDateStr = new Date(System.currentTimeMillis()).toString();
        if(startDate.compareTo(nowDateStr) > 0) {
            throw new BusinessException("开始时间不能晚于当前时间");
        }
        this.experimentMapper.updateExp(exId, name, startDate, ratName);
    }

    @Override
    public void updateExpStatus(Integer exId, Integer status) throws BusinessException {
        if(Experiment.judgeStatus(status) == false) {
            throw new BusinessException("状态错误");
        }
        if(this.experimentMapper.isExpExist(exId) == false) {
            throw new BusinessException("实验不存在");
        }
        this.experimentMapper.updateExpStatus(exId, status);
    }

    @Override
    public void deleteExp(Integer exId) throws BusinessException {
        this.updateExpStatus(exId, Experiment.Status.DELETED.ordinal());
    }

    @Override
    public ArrayList<Experiment> getExpListByUId(Integer uId) {
        return this.experimentMapper.selectExpListByUId(uId);
    }

    @Override
    public Experiment getExpByExId(Integer exId) {
        return this.experimentMapper.selectByExId(exId);
    }

    @Override
    public Experiment getExpByName(String name) {
        return this.experimentMapper.selectByName(name);
    }

    @Override
    public void joinTeam(int exId, int ownerId) {
        this.experimentMapper.insertTeam(exId, ownerId);
    }
}
