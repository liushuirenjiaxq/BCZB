package com.bczb.controller;

import com.bczb.exceptions.BusinessException;
import com.bczb.pojo.Medicine;
import com.bczb.pojo.Result;
import com.bczb.service.IMedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("api/med")
public class MedicineController {

    @Resource
    private IMedicineService medicineService;

    @GetMapping("/test")
    public Result test(){
        return Result.success();
    }

    /* 查询所有medicine信息 */
    @GetMapping("/")
    public Result SelectMedicine(){
        ArrayList<Medicine> list = this.medicineService.selectMedicineList();
        return Result.data(list);
    }

    /* 插入一条数据 */
    @PostMapping("/")
    public Result InsertMedicine(@RequestBody Medicine medicine) throws BusinessException {
        //System.out.println(medicine);
        int id = this.medicineService.InsertMedicine(medicine);
        return Result.data(id);
    }

    /* 修改信息 */
    @PutMapping("/")
    public Result EditMedicine(@RequestBody Medicine medicine) throws BusinessException {
        this.medicineService.UpdateMedicine(medicine);
        return Result.success();
    }

    /* 删除信息 */
    @DeleteMapping("/{id}")
    public Result DelMedicine(@PathVariable("id") Integer id) throws BusinessException {
        //System.out.println(id);
        this.medicineService.DeleteMedicine(id);
        return Result.success();
    }
}
