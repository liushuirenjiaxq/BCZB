package com.bczb.service.impl;

import com.bczb.pojo.Rat;
import com.bczb.exceptions.BusinessException;
import com.bczb.dao.ExperimentMapper;
import com.bczb.dao.GroupMapper;
import com.bczb.dao.RatMapper;
import com.bczb.service.IRatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class RatServiceImpl implements IRatService {

    @Resource
    private RatMapper ratMapper;

    @Resource
    private ExperimentMapper experimentMapper;

    @Resource
    private GroupMapper groupMapper;

    @Override
    public boolean isGenderOutOfRange( String gId, String gender) throws BusinessException {
        Integer count = this.ratMapper.selectGenderCount( gId, gender);
        if (count == null) {
            throw new BusinessException("查询性别数量失败");
        }
        int max = 10;
        if(count >= max) {
            return true;
        }
        return false;
    }


    @Override
    public boolean isCageOutOfRange( String gId, String gender, Integer cage) {
        Integer count = this.ratMapper.selectCageCount( gId, gender, cage);
        int max = 5;
        if(count >= max) {
            return true;
        }
        return false;
    }




    @Override
    public boolean isIndexExist( String gId, String index) {
        Integer count = this.ratMapper.selectRIndexCount( gId, index);
        if(count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void addRat( String gId, String gender, Integer cage, String index) {
        this.ratMapper.insertRat(gId, gender, cage, index);
    }

    @Override
    public boolean isExpExist(Integer exId) {
        if(this.experimentMapper.isExpExist(exId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isGroupExist(String gId) {
        if(this.groupMapper.isGroupExist(gId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isExpRunning(Integer rId) throws BusinessException {
        Integer status = this.ratMapper.selectStatusByRId(rId);
        if(status == null) {
            throw new BusinessException("查询实验状态失败");
        }
        if(status == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isRatExist(Integer rId) {
        return this.ratMapper.isRatExist(rId);
    }

    @Override
    public boolean isExpFinished(Integer rId) throws BusinessException {
        Integer status = this.ratMapper.selectStatusByRId(rId);
        if(status == null) {
            throw new BusinessException("查询实验状态失败");
        }
        if(status == 2) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteRat(Integer rId) {
        if(this.ratMapper.isRatExist(rId)) {
            this.ratMapper.deleteRat(rId);
        }
    }

    @Override
    public void updateRat(Rat rat) {
        this.ratMapper.updateRat(rat);
    }

    @Override
    public ArrayList<Rat> getAllRatByExId(Integer exId) {
        return this.ratMapper.selectAllRatByExId(exId);
    }

    @Override
    public ArrayList<Rat> getAllRat() {
        return this.ratMapper.selectAllRat();
    }

    @Override
    public Rat getRatByRId(Integer rId) {
        return this.ratMapper.selectRatByRId(rId);
    }



    @Override
    public void addRats( ArrayList<Rat> rats) {
        rats.forEach(rat -> {
            this.ratMapper.insertRat( rat.getGId(), rat.getGender(), rat.getCage(), rat.getRIndex());
        });
    }





    
}
