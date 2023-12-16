package com.bczb.service.Rec;

import com.bczb.dao.ExperimentMapper;
import com.bczb.dao.GroupMapper;
import com.bczb.dao.RatMapper;
import com.bczb.dao.RecMapper.RecFecesMapper;
import com.bczb.exceptions.BusinessException;
import com.bczb.pojo.RecordEntities.Feces.FecesElement;
import com.bczb.pojo.RecordEntities.Feces.FecesRecord;
import com.bczb.pojo.RecordEntities.RecRecord;
import com.bczb.pojo.RecordEntities.RecType.IRecService;
import com.bczb.pojo.RecordEntities.RecType.Panel;
import com.bczb.pojo.RecordEntities.RecType.StateCageType;
import com.bczb.pojo.RecordEntities.RecType.StateRatType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;

@Service
public class RecFecesService implements IRecService<FecesElement, FecesRecord> {

    private final String rec_type = "rec_feces";

    @Resource
    private GroupMapper groupMapper;
    @Resource
    private RecFecesMapper recFecesMapper;

    @Resource
    private ExperimentMapper experimentMapper;

    @Resource
    private RatMapper ratMapper;

    @Override
    public boolean AuthGid(String gId) {
        return this.groupMapper.isGroupExist(gId);
    }

    @Override
    public boolean AuthGender(String gender) throws BusinessException {
        if (gender.equals("female") || gender.equals("male")) {
            return true;
        }
        throw new BusinessException("性别参数错误");
    }

    @Override
    public boolean AuthCage(Integer cage) throws BusinessException {
        if (cage >= 1 && cage <= 2) {
            return true;
        }
        throw new BusinessException("笼号参数错误");
    }

    @Override
    public ArrayList<FecesElement> GetRatElementByGidAndGenderAndCage(String gId, String gender, Integer cage) {
        return recFecesMapper.GetRatElementByGidAndGenderAndCage(gId, gender, cage);
    }

    @Override
    public String GetGroupName(String gId) {
        return this.recFecesMapper.GetGroupName(gId);
    }

    @Override
    public ArrayList<StateRatType<FecesElement>> GetRatElementByGId(String gId) throws BusinessException {
        String[] genders = { "female", "male" };
        int[] cages = { 1, 2 };
        ArrayList<StateRatType<FecesElement>> stateRats = new ArrayList<StateRatType<FecesElement>>();
        for (int i = 0; i < genders.length; i++) {
            StateRatType<FecesElement> stateRat = new StateRatType<FecesElement>();
            stateRat.setGender(genders[i]);
            ArrayList<StateCageType<FecesElement>> stateCageTypes = new ArrayList<StateCageType<FecesElement>>();
            for (int j = 0; j < cages.length; j++) {
                StateCageType<FecesElement> stateCageType = new StateCageType<FecesElement>();
                stateCageType.setNumber(cages[j]);
                ArrayList<FecesElement> RatElements = this.recFecesMapper.GetRatElementByGidAndGenderAndCage(gId,
                        genders[i], cages[j]);
                stateCageType.setData(RatElements);
                stateCageTypes.add(stateCageType);
            }
            stateRat.setCages(stateCageTypes);
            stateRats.add(stateRat);
        }
        return stateRats;
    }

    @Override
    public RecRecord<FecesRecord> trans2RecRecord(ArrayList<Panel<FecesElement>> panel, String date, Integer uid,
            String gId) throws BusinessException {
        RecRecord<FecesRecord> recRecord = new RecRecord<FecesRecord>();
        recRecord.setRecType(this.rec_type);
        recRecord.setDate(date);
        recRecord.setRecer(uid);
        recRecord.setGid(gId);
        ArrayList<FecesRecord> recordList = new ArrayList<FecesRecord>();
        for (Panel<FecesElement> panelElement : panel) {
            for (FecesElement ratElement : panelElement.getRats()) {
                FecesRecord record = new FecesRecord();
                record.setDate(date);
                record.setRId(ratElement.getRId());
                record.setIndex(ratElement.getIndex());
                record.setPhoto(ratElement.getPhoto());
                record.setDescription(ratElement.getDescription());
                recordList.add(record);
            }
        }
        recRecord.setData(recordList);
        System.out.println(recRecord);
        return recRecord;
    }

    @Override
    public void insertRecords(RecRecord<FecesRecord> record) throws BusinessException {
        this.recFecesMapper.RecRecord(record);
        record.getData().forEach(rec -> {
            this.recFecesMapper.InsertRecordData(rec, record.getDate(), record.getRecType());
        });
        return;
    }

    @Override
    public boolean AuthDate(String date, String gId) throws BusinessException {
        // YYYY-MM-DD
        if (date.length() != 10) {
            throw new BusinessException("日期格式错误");
        }
        String[] dateArray = date.split("-");
        if (dateArray.length != 3) {
            throw new BusinessException("日期格式错误");
        }
        // 不得晚于今天
        // 生成当前日期 yyyy-MM-dd
        String today = new Date(System.currentTimeMillis()).toString();
        if(date.compareTo(today) > 0){
            throw new BusinessException("日期不得晚于今天");
        }

        boolean status = this.recFecesMapper.hasRecRecord(gId, rec_type, date);
        if (status) {
            throw new BusinessException("当日记录已存在");
        }
        return true;
    }

    @Override
    public ArrayList<RecRecord<FecesRecord>> GetRecRecords(String gId) throws BusinessException {
        ArrayList<RecRecord<FecesRecord>> recRecords = this.recFecesMapper.GetRecRecordHeader(gId, this.rec_type);
        recRecords.forEach(recRecord -> {
            recRecord.setRecType(this.rec_type);
            recRecord.setData(
                    this.recFecesMapper.GetRecRecordData(recRecord.getDate(), recRecord.getGid(), this.rec_type));
        });
        return recRecords;
    }

    @Override
    public void UpdateRecords(ArrayList<FecesRecord> records) throws BusinessException {
        records.forEach(record -> {
            this.recFecesMapper.UpdateRecord(record, this.rec_type);
        });
    }

    @Override
    public void DeleteRecRecord(RecRecord<FecesRecord> record) throws BusinessException {
        this.recFecesMapper.DeleteRecRecord(record.getId());
    }

    @Override
    public void DeleteRecords(ArrayList<FecesRecord> records) throws BusinessException {
        records.forEach(record -> {
            this.recFecesMapper.DeleteRecords(record.getId(), this.rec_type);
        });
    }

    @Override
    public boolean AuthExpStatus(String gId) throws BusinessException {
        int status = this.experimentMapper.selectExpStatusByGid(gId) ;
        if (status ==2) {
            throw new BusinessException("实验已结束");
        }
        if(status == 0){
            throw new BusinessException("实验未开始");
        }
        return true;
    }

}
