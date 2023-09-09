package com.bczb.dao.RecMapper;

import com.bczb.pojo.RecordEntities.Length.LengthElement;
import com.bczb.pojo.RecordEntities.Length.LengthRecord;
import com.bczb.pojo.RecordEntities.RecType.IRecMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

@Mapper
public interface RecLengthMapper extends IRecMapper<LengthElement, LengthRecord> {

    @Override
    @Insert("INSERT INTO ${rec_type} (r_id, length,  date , insert_time) VALUES (#{record.rId},  #{record.length}, #{date}, now())")
    public void InsertRecordData(LengthRecord record, String date, String rec_type);

    @Select("select ${rec_type}.id, ${rec_type}.r_id as rId,  ${rec_type}.length, ${rec_type}.date , ${rec_type}.insert_time as insertTime, rat.r_index as `index` "
    + " from ${rec_type} left join rat on rat.r_id = ${rec_type}.r_id "
    + " where ${rec_type}.date=#{date} and rat.g_id=#{gId} ")
    @Override
    public ArrayList<LengthRecord> GetRecRecordData(String date, String gId, String rec_type);

    @Override
    @Update("UPDATE ${rec_type} SET  length=#{record.length} WHERE id=#{record.id}")
    void UpdateRecord(LengthRecord record,String rec_type);

}
