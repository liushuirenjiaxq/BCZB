package com.bczb.controller.RecController;

import com.bczb.pojo.RecordEntities.Intake.IntakeElement;
import com.bczb.pojo.RecordEntities.Intake.IntakeRecord;
import com.bczb.pojo.RecordEntities.RecRecord;
import com.bczb.pojo.RecordEntities.RecType.IRecController;
import com.bczb.pojo.RecordEntities.RecType.Panel;
import com.bczb.pojo.Result;
import com.bczb.exceptions.BusinessException;
import com.bczb.service.Rec.RecIntakeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("api/rec/intake")
public class RecIntakeController implements IRecController<IntakeElement, IntakeRecord> {

    @Resource
    private RecIntakeService recIntakeService;

    // 查询某组别某性别的一个笼子
    // 参数: int/string/int
    // 示例: 878609010496425984/female/1
    @Override
    @GetMapping("/{gId}/{gender}/{cage}")
    public Result GetRatElementByGIdAndGenderAndCage(
            @PathVariable String gId,
            @PathVariable String gender,
            @PathVariable Integer cage) throws BusinessException {
        this.recIntakeService.AuthGid(gId);
        this.recIntakeService.AuthGender(gender);
        this.recIntakeService.AuthCage(cage);
        return Result.data(this.recIntakeService.GetRatElementByGidAndGenderAndCage(gId, gender, cage));
    }

    // 查询组别的名称
    // 参数: int
    // 示例: 878609010496425984
    @Override
    @GetMapping("/{gId}/name")
    public Result GetGroupName(@PathVariable String gId) throws BusinessException {
        this.recIntakeService.AuthGid(gId);
        return Result.data(this.recIntakeService.GetGroupName(gId));
    }

    // 查询组别中小鼠信息
    // 参数: int
    // 示例: 878609010496425984
    @Override
    @GetMapping("/{gId}")
    public Result GetRatElementByGId(@PathVariable String gId) throws BusinessException {
        this.recIntakeService.AuthGid(gId);
        return Result.data(this.recIntakeService.GetRatElementByGId(gId));
    }

    // 存取小鼠摄食量记录
    // 参数: int String(YY-MM-DD) JSON(Arraylist)
    // 示例: 878609010496425984  2023-10-10
    /*[
        {"gender":"famale",  "number":"1", "rats":[
            {"rId":"1", "index":"11"},
            {"rId":"2", "index":"12"},
            {"rId":"3", "index":"13"},
            {"rId":"4", "index":"14"},
            {"rId":"5", "index":"15"}
        ]},
        {"gender":"famale",  "number":"2", "rats":[
            {"rId":"6", "index":"16"},
            {"rId":"7", "index":"17"},
            {"rId":"8", "index":"18"},
            {"rId":"9", "index":"19"},
            {"rId":"10", "index":"20"}
        ]},
        {"gender":"male",  "number":"1", "rats":[
            {"rId":"11", "index":"21"},
            {"rId":"12", "index":"22"},
            {"rId":"13", "index":"23"},
            {"rId":"14", "index":"24"},
            {"rId":"15", "index":"25"}
        ]},
        {"gender":"male",  "number":"1", "rats":[
            {"rId":"16", "index":"26"},
            {"rId":"17", "index":"27"},
            {"rId":"18", "index":"28"},
            {"rId":"19", "index":"29"},
            {"rId":"20", "index":"30"}
        ]}
    ]*/
    @Override
    @PostMapping("/rats/{gId}/{date}")
    public Result UploadRatElements(@RequestBody ArrayList<Panel<IntakeElement>> paneList, @PathVariable String gId,
            @RequestAttribute("uId") Integer uid, @PathVariable String date) throws BusinessException {
        this.recIntakeService.AuthGid(gId);
        this.recIntakeService.AuthExpStatus(gId);
        this.recIntakeService.AuthDate(date, gId);
        this.recIntakeService.insertRecords(this.recIntakeService.trans2RecRecord(paneList, date, uid, gId));
        return Result.success();
    }

    // 查询某个组别(摄食量)添加记录数据
    // 参数: int
    // 示例: 878609010496425984
    @Override
    @GetMapping("/{gId}/records")
    public Result GetRecords(@PathVariable String gId) throws BusinessException {
        this.recIntakeService.AuthGid(gId);
        return Result.data(this.recIntakeService.GetRecRecords(gId));
    }

    // 修改多条摄食量记录
    // 参数: Json {int, int, int, int}
    // 示例: [{"initialWeight":"12",  "nextdayWeight":"24", "intake":"12" , "id":"181"}]
    @Override
    @PutMapping("/records")
    public Result UpdateRecords(@RequestBody ArrayList<IntakeRecord> records) throws BusinessException {
        this.recIntakeService.UpdateRecords(records);
        return Result.success();
    }

    // 删除记录（未实现）
    // 参数: Json {int, int, int, int}
    // 示例: [{"initialWeight":"12",  "nextdayWeight":"24", "intake":"12" , "id":"181"}]
    @Override
    @PostMapping("/records")
    public Result RemoveRecord(@RequestBody RecRecord<IntakeRecord> record) throws BusinessException {
        System.out.println(record);
        this.recIntakeService.DeleteRecRecord(record);
        this.recIntakeService.DeleteRecords(record.getData());
        return Result.success();
    }
}
