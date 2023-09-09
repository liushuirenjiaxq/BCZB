package com.bczb.service;

import com.bczb.pojo.Rat;
import com.bczb.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface IRatService {

    boolean isGenderOutOfRange(String gId, String gender) throws BusinessException;

    boolean isCageOutOfRange(String gId, String gender, Integer cage);

    boolean isIndexExist(String gId, String index);

    void addRat(String gId, String gender, Integer cage, String index);

    boolean isExpExist(Integer exId);

    boolean isGroupExist(String gId);

    boolean isExpRunning(Integer rId) throws BusinessException;

    boolean isRatExist(Integer rId);

    boolean isExpFinished(Integer rId) throws BusinessException;

    void deleteRat(Integer rId);

    void updateRat(Rat rat);

    ArrayList<Rat> getAllRatByExId(Integer exId);

    ArrayList<Rat> getAllRat();

    Rat getRatByRId(Integer rId);

    void addRats(ArrayList<Rat> rats);

}
