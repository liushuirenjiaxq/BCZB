package com.bczb.dao.RecMapper;

import com.bczb.pojo.RecordEntities.Feces.FecesElement;
import com.bczb.pojo.RecordEntities.Feces.FecesRecord;
import com.bczb.pojo.RecordEntities.RecType.IRecMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

@Mapper
public interface RecFecesMapper extends IRecMapper<FecesElement, FecesRecord> {
    @Override
    @Select("select ${rec_type}.id, ${rec_type}.r_id as rId, ${rec_type}.photo as photo, ${rec_type}.description as description, ${rec_type}.date , ${rec_type}.insert_time as insertTime, rat.r_index as `index` "
            + " from ${rec_type} left join rat on rat.r_id = ${rec_type}.r_id "
            + " where ${rec_type}.date=#{date} and rat.g_id=#{gId} ")
    ArrayList<FecesRecord> GetRecRecordData(String date, String gId, String rec_type);

    @Override
    @Insert("INSERT INTO ${rec_type} (r_id, photo, description, date , insert_time) VALUES (#{record.rId}, #{record.photo}, #{record.description}, #{date}, now())")
    void InsertRecordData(FecesRecord record, String date, String rec_type);

    @Override
    @Update("UPDATE ${rec_type} SET photo=#{record.photo}, description=#{record.description} WHERE id=#{record.id}")
    void UpdateRecord(FecesRecord record, String rec_type);
}
