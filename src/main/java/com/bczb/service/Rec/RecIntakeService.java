package com.bczb.service.Rec;

import com.bczb.pojo.RecordEntities.Intake.IntakeElement;
import com.bczb.pojo.RecordEntities.Intake.IntakeRecord;
import com.bczb.pojo.RecordEntities.RecRecord;
import com.bczb.pojo.RecordEntities.RecType.IRecService;
import com.bczb.pojo.RecordEntities.RecType.Panel;
import com.bczb.pojo.RecordEntities.RecType.StateCageType;
import com.bczb.pojo.RecordEntities.RecType.StateRatType;
import com.bczb.exceptions.BusinessException;
import com.bczb.dao.ExperimentMapper;
import com.bczb.dao.GroupMapper;
import com.bczb.dao.RatMapper;
import com.bczb.dao.RecMapper.RecIntakeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;

@Service
public class RecIntakeService implements IRecService<IntakeElement, IntakeRecord> {

    private final String rec_type = "rec_intake";

    @Resource
    private GroupMapper groupMapper;
    @Resource
    private RecIntakeMapper recIntakeMapper;

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
    public ArrayList<IntakeElement> GetRatElementByGidAndGenderAndCage(String gId, String gender, Integer cage) {
        return recIntakeMapper.GetRatElementByGidAndGenderAndCage(gId, gender, cage);
    }

    @Override
    public String GetGroupName(String gId) {
        return this.recIntakeMapper.GetGroupName(gId);
    }

    @Override
    public ArrayList<StateRatType<IntakeElement>> GetRatElementByGId(String gId) throws BusinessException {
        String[] genders = { "female", "male" };
        int[] cages = { 1, 2 };
        ArrayList<StateRatType<IntakeElement>> stateRats = new ArrayList<StateRatType<IntakeElement>>();
        for (int i = 0; i < genders.length; i++) {
            StateRatType<IntakeElement> stateRat = new StateRatType<IntakeElement>();
            stateRat.setGender(genders[i]);
            ArrayList<StateCageType<IntakeElement>> stateCageTypes = new ArrayList<StateCageType<IntakeElement>>();
            for (int j = 0; j < cages.length; j++) {
                StateCageType<IntakeElement> stateCageType = new StateCageType<IntakeElement>();
                stateCageType.setNumber(cages[j]);
                ArrayList<IntakeElement> RatElements = this.recIntakeMapper.GetRatElementByGidAndGenderAndCage(gId,
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
    public RecRecord<IntakeRecord> trans2RecRecord(ArrayList<Panel<IntakeElement>> panel, String date, Integer uid,
            String gId) throws BusinessException {
        RecRecord<IntakeRecord> recRecord = new RecRecord<IntakeRecord>();
        recRecord.setRecType(this.rec_type);
        recRecord.setDate(date);
        recRecord.setRecer(uid);
        recRecord.setGid(gId);
        ArrayList<IntakeRecord> recordList = new ArrayList<IntakeRecord>();
        for (Panel<IntakeElement> panelElement : panel) {
            for (IntakeElement ratElement : panelElement.getRats()) {
                IntakeRecord record = new IntakeRecord();
                if (ratElement.getInitialWeight() == null) {
                    ratElement.setInitialWeight(-1.0);
                }
                if (ratElement.getNextdayWeight() == null) {
                    ratElement.setNextdayWeight(-1.0);
                }
                ratElement.setIntake(ratElement.getNextdayWeight() - ratElement.getInitialWeight());
                record.setDate(date);
                record.setRId(ratElement.getRId());
                record.setIndex(ratElement.getIndex());
                record.setInitialWeight(ratElement.getInitialWeight());
                record.setNextdayWeight(ratElement.getNextdayWeight());
                record.setIntake(ratElement.getIntake());
                recordList.add(record);
            }
        }
        recRecord.setData(recordList);
        System.out.println(recRecord);
        return recRecord;
    }

    @Override
    public void insertRecords(RecRecord<IntakeRecord> record) throws BusinessException {
        this.recIntakeMapper.RecRecord(record);
        record.getData().forEach(rec -> {
            this.recIntakeMapper.InsertRecordData(rec, record.getDate(), record.getRecType());
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

        boolean status = this.recIntakeMapper.hasRecRecord(gId, rec_type, date);
        if (status) {
            throw new BusinessException("当日记录已存在");
        }
        return true;
    }

    @Override
    public ArrayList<RecRecord<IntakeRecord>> GetRecRecords(String gId) throws BusinessException {
        ArrayList<RecRecord<IntakeRecord>> recRecords = this.recIntakeMapper.GetRecRecordHeader(gId, this.rec_type);
        recRecords.forEach(recRecord -> {
            recRecord.setRecType(this.rec_type);
            recRecord.setData(
                    this.recIntakeMapper.GetRecRecordData(recRecord.getDate(), recRecord.getGid(), this.rec_type));
        });
        return recRecords;
    }

    @Override
    public void UpdateRecords(ArrayList<IntakeRecord> records) throws BusinessException {
        records.forEach(record -> {
            this.recIntakeMapper.UpdateRecord(record, this.rec_type);
        });
    }

    @Override
    public void DeleteRecRecord(RecRecord<IntakeRecord> record) throws BusinessException {
        this.recIntakeMapper.DeleteRecRecord(record.getId());
    }

    @Override
    public void DeleteRecords(ArrayList<IntakeRecord> records) throws BusinessException {
        records.forEach(record -> {
            this.recIntakeMapper.DeleteRecords(record.getId(), this.rec_type);
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
