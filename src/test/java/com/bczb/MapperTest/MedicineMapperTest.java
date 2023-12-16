package com.bczb.MapperTest;

import com.bczb.dao.ExperimentMapper;
import com.bczb.dao.MedicineMapper;
import com.bczb.pojo.Medicine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MedicineMapperTest {

    @Resource
    private MedicineMapper medicineMapper;

    @Resource
    private ExperimentMapper experimentMapper;

    @Test
    public void mytest() {
        experimentMapper.updateExpEndDate(60);
    }

    @Test
    public void test(){
        // 查询数据
        System.out.println(medicineMapper.selectMedicineList());
        System.out.println(medicineMapper.selectMedicineId("金银花"));
        // 插入数据
        /*Medicine medicine = new Medicine();
        medicine.setName("李小桥");
        medicineMapper.InsertMedicine(medicine);*/

        // 删除数据
        //medicineMapper.DeleteMedicine(4);

        // 修改数据
        /*Medicine medicine = new Medicine();
        medicine.setId(4);
        medicine.setName("xhh");
        medicineMapper.UpdateMedicine(medicine);*/
    }
}
