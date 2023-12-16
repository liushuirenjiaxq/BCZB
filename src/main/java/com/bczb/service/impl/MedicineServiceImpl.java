package com.bczb.service.impl;

import com.bczb.dao.MedicineMapper;
import com.bczb.exceptions.BusinessException;
import com.bczb.pojo.Medicine;
import com.bczb.service.IMedicineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class MedicineServiceImpl implements IMedicineService {

    @Resource
    private MedicineMapper medicineMapper;

    @Override
    public Integer InsertMedicine(Medicine medicine) throws BusinessException {
        medicineMapper.InsertMedicine(medicine);
        Integer id = medicineMapper.selectMedicineId(medicine.getName());
        return id;
    }

    @Override
    public void UpdateMedicine(Medicine medicine) throws BusinessException {
        medicineMapper.UpdateMedicine(medicine);
    }

    @Override
    public void DeleteMedicine(Integer id) throws BusinessException {
        medicineMapper.DeleteMedicine(id);
    }

    @Override
    public ArrayList<Medicine> selectMedicineList() {
        return medicineMapper.selectMedicineList();
    }
}
