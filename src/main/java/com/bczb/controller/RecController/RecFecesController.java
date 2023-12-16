package com.bczb.controller.RecController;

import com.bczb.exceptions.BusinessException;
import com.bczb.pojo.RecordEntities.Feces.FecesElement;
import com.bczb.pojo.RecordEntities.Feces.FecesRecord;
import com.bczb.pojo.RecordEntities.RecRecord;
import com.bczb.pojo.RecordEntities.RecType.IRecController;
import com.bczb.pojo.RecordEntities.RecType.Panel;
import com.bczb.pojo.Result;
import com.bczb.service.Rec.RecFecesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("api/rec/feces")
public class RecFecesController implements IRecController<FecesElement, FecesRecord> {

    @Resource
    private RecFecesService recIFecesService;

    // 查询某组别某性别的一个笼子
    // 参数: int/string/int
    // 示例: 878609010496425984/female/1
    @Override
    @GetMapping("/{gId}/{gender}/{cage}")
    public Result GetRatElementByGIdAndGenderAndCage(
            @PathVariable String gId,
            @PathVariable String gender,
            @PathVariable Integer cage) throws BusinessException {
        this.recIFecesService.AuthGid(gId);/*判断是否这个组*/
        this.recIFecesService.AuthGender(gender);/*判断是否合法*/
        this.recIFecesService.AuthCage(cage);/*判断是否合法*/
        return Result.data(this.recIFecesService.GetRatElementByGidAndGenderAndCage(gId, gender, cage));
    }

    // 查询组别的名称
    // 参数: int
    // 示例: 878609010496425984
    @Override
    @GetMapping("/{gId}/name")
    public Result GetGroupName(@PathVariable String gId) throws BusinessException {
        this.recIFecesService.AuthGid(gId);
        return Result.data(this.recIFecesService.GetGroupName(gId));
    }

    // 查询组别中小鼠信息
    // 参数: int
    // 示例: 878609010496425984
    @Override
    @GetMapping("/{gId}")
    public Result GetRatElementByGId(@PathVariable String gId) throws BusinessException {
        this.recIFecesService.AuthGid(gId);
        return Result.data(this.recIFecesService.GetRatElementByGId(gId));
    }
    
    @Override
    @PostMapping("/rats/{gId}/{date}")
    public Result UploadRatElements(@RequestBody ArrayList<Panel<FecesElement>> paneList, @PathVariable String gId,
            @RequestAttribute("uId") Integer uid, @PathVariable String date) throws BusinessException {
        this.recIFecesService.AuthGid(gId);
        this.recIFecesService.AuthExpStatus(gId);
        this.recIFecesService.AuthDate(date, gId);
        this.recIFecesService.insertRecords(this.recIFecesService.trans2RecRecord(paneList, date, uid, gId));
        return Result.success();
    }

    // 查询某个组别(摄食量)添加记录数据
    // 参数: int
    // 示例: 878609010496425984
    @Override
    @GetMapping("/{gId}/records")
    public Result GetRecords(@PathVariable String gId) throws BusinessException {
        this.recIFecesService.AuthGid(gId);
        return Result.data(this.recIFecesService.GetRecRecords(gId));
    }

    // 修改多条摄食量记录
    @Override
    @PutMapping("/records")
    public Result UpdateRecords(@RequestBody ArrayList<FecesRecord> records) throws BusinessException {
        this.recIFecesService.UpdateRecords(records);
        return Result.success();
    }

    // 删除记录
    @Override
    @PostMapping("/records")
    public Result RemoveRecord(@RequestBody RecRecord<FecesRecord> record) throws BusinessException {
        System.out.println(record);
        this.recIFecesService.DeleteRecRecord(record);
        this.recIFecesService.DeleteRecords(record.getData());
        return Result.success();
    }
}
