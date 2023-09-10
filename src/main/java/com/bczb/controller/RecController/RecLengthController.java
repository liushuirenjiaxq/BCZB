package com.bczb.controller.RecController;

import com.bczb.pojo.RecordEntities.Length.LengthElement;
import com.bczb.pojo.RecordEntities.Length.LengthRecord;
import com.bczb.pojo.RecordEntities.RecRecord;
import com.bczb.pojo.RecordEntities.RecType.IRecController;
import com.bczb.pojo.RecordEntities.RecType.Panel;
import com.bczb.pojo.Result;
import com.bczb.exceptions.BusinessException;
import com.bczb.service.Rec.RecLengthService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("api/rec/length")
public class RecLengthController implements IRecController<LengthElement, LengthRecord> {

    @Resource
    private RecLengthService recLengthService;

    @Override
    @GetMapping("/{gId}/{gender}/{cage}")
    public Result GetRatElementByGIdAndGenderAndCage(
            @PathVariable String gId,
            @PathVariable String gender,
            @PathVariable Integer cage) throws BusinessException {
        this.recLengthService.AuthGid(gId);
        this.recLengthService.AuthGender(gender);
        this.recLengthService.AuthCage(cage);
        return Result.data(this.recLengthService.GetRatElementByGidAndGenderAndCage(gId, gender, cage));
    }

    @Override
    @GetMapping("/{gId}/name")
    public Result GetGroupName(@PathVariable String gId) throws BusinessException {
        this.recLengthService.AuthGid(gId);
        return Result.data(this.recLengthService.GetGroupName(gId));
    }

    @Override
    @GetMapping("/{gId}")
    public Result GetRatElementByGId(@PathVariable String gId) throws BusinessException {
        this.recLengthService.AuthGid(gId);
        return Result.data(this.recLengthService.GetRatElementByGId(gId));
    }

    @Override
    @PostMapping("/rats/{gId}/{date}")
    public Result UploadRatElements(@RequestBody ArrayList<Panel<LengthElement>> paneList, @PathVariable String gId,
            @RequestAttribute("uId") Integer uid, @PathVariable String date) throws BusinessException {
        this.recLengthService.AuthGid(gId);
        this.recLengthService.AuthExpStatus(gId);
        this.recLengthService.AuthDate(date, gId);
        this.recLengthService.insertRecords(this.recLengthService.trans2RecRecord(paneList, date, uid, gId));
        return Result.success();
    }

    @Override
    @GetMapping("/{gId}/records")
    public Result GetRecords(@PathVariable String gId) throws BusinessException {
        this.recLengthService.AuthGid(gId);
        return Result.data(this.recLengthService.GetRecRecords(gId));
    }

    @Override
    @PutMapping("/records")
    public Result UpdateRecords(@RequestBody ArrayList<LengthRecord> records) throws BusinessException {
        this.recLengthService.UpdateRecords(records);
        return Result.success();
    }

    @Override
    @PostMapping("/records")
    public Result RemoveRecord(@RequestBody RecRecord<LengthRecord> record) throws BusinessException {
        System.out.println(record);
        this.recLengthService.DeleteRecRecord(record);
        this.recLengthService.DeleteRecords(record.getData());
        return Result.success();
    }
}
