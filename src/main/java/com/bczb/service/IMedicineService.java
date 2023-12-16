package com.bczb.service;

import com.bczb.exceptions.BusinessException;
import com.bczb.pojo.Medicine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface IMedicineService {

    Integer InsertMedicine(Medicine medicine) throws BusinessException;

    void UpdateMedicine(Medicine medicine) throws BusinessException;

    void DeleteMedicine(Integer id) throws BusinessException;

    ArrayList<Medicine> selectMedicineList();
}
