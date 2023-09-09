package com.bczb.service;

import com.bczb.pojo.Experiment;
import com.bczb.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public interface IExperimentService {

    void createExp(String name, String ratName, int ownerId) throws BusinessException;

    ArrayList<Experiment> getAllExp();

    void updateRuningExp(int exId, String name, String startDate, String ratName) throws BusinessException;

    void updateExpStatus(Integer exId, Integer status) throws BusinessException;

    void deleteExp(Integer exId) throws BusinessException;

    ArrayList<Experiment> getExpListByUId(Integer uId);

    Experiment getExpByExId(Integer exId);

    Experiment getExpByName(String name);

    void joinTeam(int exId, int ownerId);

    
}
