package com.bczb.dao.RecMapper;

import com.bczb.pojo.RecordEntities.Intake.IntakeElement;
import com.bczb.pojo.RecordEntities.Intake.IntakeRecord;
import com.bczb.pojo.RecordEntities.RecType.IRecMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

@Mapper
public interface RecIntakeMapper extends IRecMapper<IntakeElement, IntakeRecord> {

    @Override
    @Insert("INSERT INTO ${rec_type} (r_id, initial_weight, nextday_weight, intake, date , insert_time) VALUES (#{record.rId}, #{record.initialWeight}, #{record.nextdayWeight}, #{record.intake}, #{date}, now())")
    public void InsertRecordData(IntakeRecord record, String date, String rec_type);

    @Select("select ${rec_type}.id, ${rec_type}.r_id as rId, ${rec_type}.initial_weight as initialWeight, ${rec_type}.nextday_weight as nextdayWeight, ${rec_type}.intake, ${rec_type}.date , ${rec_type}.insert_time as insertTime, rat.r_index as `index` "
    + " from ${rec_type} left join rat on rat.r_id = ${rec_type}.r_id "
    + " where ${rec_type}.date=#{date} and rat.g_id=#{gId} ")
    @Override
    public ArrayList<IntakeRecord> GetRecRecordData(String date, String gId, String rec_type);

    @Override
    @Update("UPDATE ${rec_type} SET initial_weight=#{record.initialWeight}, nextday_weight=#{record.nextdayWeight}, intake=#{record.intake} WHERE id=#{record.id}")
    void UpdateRecord(IntakeRecord record,String rec_type);

}
