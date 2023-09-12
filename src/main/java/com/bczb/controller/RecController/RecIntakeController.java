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

    @Override
    @GetMapping("/{gId}/name")
    public Result GetGroupName(@PathVariable String gId) throws BusinessException {
        this.recIntakeService.AuthGid(gId);
        return Result.data(this.recIntakeService.GetGroupName(gId));
    }

    @Override
    @GetMapping("/{gId}")
    public Result GetRatElementByGId(@PathVariable String gId) throws BusinessException {
        this.recIntakeService.AuthGid(gId);
        return Result.data(this.recIntakeService.GetRatElementByGId(gId));
    }

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

    @Override
    @GetMapping("/{gId}/records")
    public Result GetRecords(@PathVariable String gId) throws BusinessException {
        this.recIntakeService.AuthGid(gId);
        return Result.data(this.recIntakeService.GetRecRecords(gId));
    }

    @Override
    @PutMapping("/records")
    public Result UpdateRecords(@RequestBody ArrayList<IntakeRecord> records) throws BusinessException {
        this.recIntakeService.UpdateRecords(records);
        return Result.success();
    }

    @Override
    @PostMapping("/records")
    public Result RemoveRecord(@RequestBody RecRecord<IntakeRecord> record) throws BusinessException {
        System.out.println(record);
        this.recIntakeService.DeleteRecRecord(record);
        this.recIntakeService.DeleteRecords(record.getData());
        return Result.success();
    }
}
