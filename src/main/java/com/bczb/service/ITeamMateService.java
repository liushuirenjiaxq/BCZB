package com.bczb.service;

import com.bczb.pojo.TeamMate;
import com.bczb.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface ITeamMateService {

    void joinTeam(Integer exId, String name) throws BusinessException;

    void quitTeam(Integer id) throws BusinessException;

    ArrayList<TeamMate> getTeamMates(Integer exId) throws BusinessException;

    ArrayList<TeamMate> getAllTeamMates();
    
}
