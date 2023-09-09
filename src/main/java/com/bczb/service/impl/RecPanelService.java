package com.bczb.service.impl;

import com.bczb.pojo.Rat;
import com.bczb.dao.ExperimentMapper;
import com.bczb.dao.GroupMapper;
import com.bczb.dao.RatMapper;
import com.bczb.service.IRecPanelService;

import javax.annotation.Resource;
import java.util.ArrayList;

@org.springframework.stereotype.Service
public class RecPanelService implements IRecPanelService {

    @Resource
    private GroupMapper groupMapper;


    @Resource
    private ExperimentMapper experimentMapper;

    @Resource
    private RatMapper ratMapper;

    @Override
    public boolean isGroupExist(String gId) {
        return this.groupMapper.isGroupExist(gId);
    }

    @Override
    public String getExNameByGId(String gId) {
        return this.experimentMapper.selectNameByGId(gId);
    }

    @Override
    public String getGroupNameByGId(String gId) {
        return this.groupMapper.selectByGId(gId).getName();
    }

    @Override
    public ArrayList<Rat> getRatsByGId(String gId) {
        return this.ratMapper.selectByGId(gId);
    }
    
}
