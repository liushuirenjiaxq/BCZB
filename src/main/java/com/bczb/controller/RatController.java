package com.bczb.controller;

import com.bczb.pojo.Rat;
import com.bczb.pojo.Result;
import com.bczb.exceptions.BusinessException;
import com.bczb.service.IRatService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("api/rat")
public class RatController {

    @Resource
    private IRatService ratService;

    // 录入大鼠
    @PostMapping("/{gId}")
    public Result addRat( @PathVariable String gId, @RequestBody Rat rat)
            throws BusinessException {
        // 性别gender 笼号cage 序号index
        String gender = rat.getGender();
        Integer cage = rat.getCage();
        String index = rat.getRIndex();

        // 组别是否存在判断
        if (!this.ratService.isGroupExist(gId)) {
            return Result.error("组别不存在");
        }
        // 同一性别超额判断
        if (this.ratService.isGenderOutOfRange( gId, gender)) {
            return Result.error("同一性别大鼠超额");
        }
        // 同一笼号超额判断
        if (this.ratService.isCageOutOfRange(gId, gender, cage)) {
            return Result.error("同一笼号大鼠超额");
        }
        // 编号重复判断
        if (this.ratService.isIndexExist( gId, index)) {
            return Result.error("编号重复");
        }
        // 添加大鼠
        this.ratService.addRat(gId, gender, cage, index);
        return Result.success();
    }

    // 录入大鼠群体
    @PostMapping("/")
    public Result addRats(@RequestBody ArrayList<Rat> rats) throws BusinessException {
        this.ratService.addRats(rats);
        return Result.success();
    }

    // 删除大鼠
    @Delete("/{rId}")
    public Result deleteRat(@PathVariable Integer rId) throws BusinessException {
        // 大鼠是否存在判断
        if (!this.ratService.isRatExist(rId)) {
            return Result.error("大鼠不存在");
        }
        // 实验是否正在进行判断
        if (!this.ratService.isExpRunning(rId)) {
            return Result.error("实验正在进行");
        }
        // 实验是否已经结束判断
        if (!this.ratService.isExpFinished(rId)) {
            return Result.error("实验已完成");
        }

        // 删除大鼠
        this.ratService.deleteRat(rId);
        return Result.success();
    }

    // 修改大鼠
    @PutMapping("/")
    public Result updateRat(@RequestBody Rat rat) throws BusinessException {
        Integer rId = rat.getRId();
        // 大鼠是否存在判断
        if (!this.ratService.isRatExist(rId)) {
            return Result.error("大鼠不存在");
        }
        // 实验是否正在进行判断
        if (!this.ratService.isExpRunning(rId)) {
            return Result.error("实验正在进行");
        }
        // 实验是否已经结束判断
        if (!this.ratService.isExpFinished(rId)) {
            return Result.error("实验已完成");
        }

        // 修改大鼠
        this.ratService.updateRat(rat);
        return Result.success();
    }

    // 通过exId查询实验的大鼠
    @GetMapping("/exp/{exId}")


    public Result GetAllRatByExId(@PathVariable Integer exId) {
        // 实验是否存在判断
        if (!this.ratService.isExpExist(exId)) {
            return Result.error("实验不存在");
        }
        return Result.data(this.ratService.getAllRatByExId(exId));
    }

    // 查询所有大鼠
    @GetMapping("/")
    public Result getAllRat() {
        return Result.data(this.ratService.getAllRat());
    }

    // 通过rId查询大鼠
    @GetMapping("/{rId}")
    public Result getRatByRId(@PathVariable Integer rId) {
        // 大鼠是否存在判断
        if (!this.ratService.isRatExist(rId)) {
            return Result.error("大鼠不存在");
        }
        return Result.data(this.ratService.getRatByRId(rId));
    }

}
