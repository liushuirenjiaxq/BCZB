package com.bczb.service.Rec;

import com.bczb.pojo.RecordEntities.Length.LengthElement;
import com.bczb.pojo.RecordEntities.Length.LengthRecord;
import com.bczb.pojo.RecordEntities.RecRecord;
import com.bczb.pojo.RecordEntities.RecType.IRecService;
import com.bczb.pojo.RecordEntities.RecType.Panel;
import com.bczb.pojo.RecordEntities.RecType.StateCageType;
import com.bczb.pojo.RecordEntities.RecType.StateRatType;
import com.bczb.exceptions.BusinessException;
import com.bczb.dao.ExperimentMapper;
import com.bczb.dao.GroupMapper;
import com.bczb.dao.RatMapper;
import com.bczb.dao.RecMapper.RecLengthMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;

@Service
public class RecLengthService implements IRecService<LengthElement, LengthRecord> {

    private final String rec_type = "rec_length";

    @Resource
    private GroupMapper groupMapper;
    @Resource
    private RecLengthMapper recLengthMapper;

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
    public ArrayList<LengthElement> GetRatElementByGidAndGenderAndCage(String gId, String gender, Integer cage) {
        return recLengthMapper.GetRatElementByGidAndGenderAndCage(gId, gender, cage);
    }

    @Override
    public String GetGroupName(String gId) {
        return this.recLengthMapper.GetGroupName(gId);
    }

    @Override
    public ArrayList<StateRatType<LengthElement>> GetRatElementByGId(String gId) throws BusinessException {
        String[] genders = { "female", "male" };
        int[] cages = { 1, 2 };
        ArrayList<StateRatType<LengthElement>> stateRats = new ArrayList<StateRatType<LengthElement>>();
        for (int i = 0; i < genders.length; i++) {
            StateRatType<LengthElement> stateRat = new StateRatType<LengthElement>();
            stateRat.setGender(genders[i]);
            ArrayList<StateCageType<LengthElement>> stateCageTypes = new ArrayList<StateCageType<LengthElement>>();
            for (int j = 0; j < cages.length; j++) {
                StateCageType<LengthElement> stateCageType = new StateCageType<LengthElement>();
                stateCageType.setNumber(cages[j]);
                ArrayList<LengthElement> RatElements = this.recLengthMapper.GetRatElementByGidAndGenderAndCage(gId,
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
    public RecRecord<LengthRecord> trans2RecRecord(ArrayList<Panel<LengthElement>> panel, String date, Integer uid,
            String gId) throws BusinessException {
        RecRecord<LengthRecord> recRecord = new RecRecord<LengthRecord>();
        recRecord.setRecType(this.rec_type);
        recRecord.setDate(date);
        recRecord.setRecer(uid);
        recRecord.setGid(gId);
        ArrayList<LengthRecord> recordList = new ArrayList<LengthRecord>();
        for (Panel<LengthElement> panelElement : panel) {
            for (LengthElement ratElement : panelElement.getRats()) {
                LengthRecord record = new LengthRecord();

                if(ratElement.getLength() == null){
                    ratElement.setLength(-1.0);
                }
              
                record.setDate(date);
                record.setRId(ratElement.getRId());
                record.setIndex(ratElement.getIndex());

                record.setLength(ratElement.getLength());
                recordList.add(record);
            }
        }
        recRecord.setData(recordList);
        System.out.println(recRecord);
        return recRecord;
    }

    @Override
    public void insertRecords(RecRecord<LengthRecord> record) throws BusinessException {
        this.recLengthMapper.RecRecord(record);
        record.getData().forEach(rec -> {
            this.recLengthMapper.InsertRecordData(rec, record.getDate(), record.getRecType());
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

        boolean status = this.recLengthMapper.hasRecRecord(gId, rec_type, date);
        if (status) {
            throw new BusinessException("当日记录已存在");
        }
        return true;
    }
    @Override
    public  ArrayList<RecRecord<LengthRecord>> GetRecRecords(String gId) throws BusinessException {
        ArrayList<RecRecord<LengthRecord>> recRecords = this.recLengthMapper.GetRecRecordHeader(gId, this.rec_type);
        recRecords.forEach(recRecord -> {
            recRecord.setRecType(this.rec_type);
            recRecord.setData(
                    this.recLengthMapper.GetRecRecordData(recRecord.getDate(), recRecord.getGid(), this.rec_type));
        });
        return recRecords;
    }

    @Override
    public void UpdateRecords(ArrayList<LengthRecord> records) throws BusinessException {
        records.forEach(record -> {
            this.recLengthMapper.UpdateRecord(record, this.rec_type);
        });
    }

    @Override
    public void DeleteRecRecord(RecRecord<LengthRecord> record) throws BusinessException {
        this.recLengthMapper.DeleteRecRecord(record.getId());
    }

    @Override
    public void DeleteRecords(ArrayList<LengthRecord> records) throws BusinessException {
        records.forEach(record -> {
            this.recLengthMapper.DeleteRecords(record.getId(), this.rec_type);
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
