package com.bczb.service;

import com.bczb.pojo.Rat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface IRecPanelService {

    boolean isGroupExist(String gId);

    String getExNameByGId(String gId);

    String getGroupNameByGId(String gId);

    ArrayList<Rat> getRatsByGId(String gId);
    
}
