package com.bczb.pojo.RecordEntities;

import com.bczb.pojo.Result;
import com.bczb.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public class RecType {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Record extends RatElement {
        private Integer id;
        private String date;
        private String insertTime;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class RatElement {
        private Integer rId;
        private String index;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class StateRatType<Ele extends RatElement> {
        private String gender;
        private ArrayList<StateCageType<Ele>> cages;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class StateCageType<Ele extends RatElement> {
        private int number;
        private ArrayList<Ele> data;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Panel<Ele extends RatElement> {
        private String gender;
        private Integer number;
        private ArrayList<Ele> rats;
    }

    public static interface IRecController<Ele extends RatElement, Rec extends Record> {
        Result GetRatElementByGId(String gId) throws BusinessException;

        Result GetRatElementByGIdAndGenderAndCage(String gId, String gender, Integer cage) throws BusinessException;

        Result GetGroupName(String gId) throws BusinessException;

        Result UploadRatElements(ArrayList<Panel<Ele>> paneList, String gId, Integer uid, String date) throws BusinessException;

        Result GetRecords(String gId) throws BusinessException;

        Result UpdateRecords(ArrayList<Rec> records) throws BusinessException;

        Result RemoveRecord(RecRecord<Rec> record) throws BusinessException;
    }

    public static interface IRecService<Ele extends RatElement, Rec extends Record> {
        boolean AuthGid(String gId);

        boolean AuthGender(String gender) throws BusinessException;

        boolean AuthCage(Integer cage) throws BusinessException;

        boolean AuthExpStatus(String gId) throws BusinessException;

        boolean AuthDate(String date, String gId) throws BusinessException;

        ArrayList<StateRatType<Ele>> GetRatElementByGId(String gId) throws BusinessException;

        ArrayList<Ele> GetRatElementByGidAndGenderAndCage(String gId, String gender, Integer cage);

        String GetGroupName(String gId);

        RecRecord<Rec> trans2RecRecord(ArrayList<Panel<Ele>> panel, String date, Integer uid, String gId)
                throws BusinessException;

        void insertRecords(RecRecord<Rec> record) throws BusinessException;

        ArrayList<RecRecord<Rec>> GetRecRecords(String gId) throws BusinessException;

        void UpdateRecords(ArrayList<Rec> records) throws BusinessException;

        void DeleteRecRecord(RecRecord<Rec> record) throws BusinessException;
        void DeleteRecords(ArrayList<Rec> records) throws BusinessException;
    }

    public static interface IRecMapper<Ele extends RatElement, Rec extends Record> {

        @Select("select r_id as rId, r_index as `index`  from rat where g_id=#{gId} and gender=#{gender} and cage=#{cage} ")
        ArrayList<Ele> GetRatElementByGidAndGenderAndCage(String gId, String gender, Integer cage);

        @Select("select name  from `group` where g_id=#{gId}")
        String GetGroupName(String gId);

        

        @Insert("insert into rec_record(gid,rec_type,recer,date) values(#{gid},#{recType},#{recer},#{date})")
        void RecRecord(RecRecord<Rec> record);

        @Select("select count(*) from rec_record where gid=#{gId} and rec_type=#{rec_type} and date=#{date}")
        boolean hasRecRecord(String gId, String rec_type, String date);

        @Select("select id, gid, rec_type as recType, recer, date from rec_record where gid=#{gId} and rec_type=#{rec_type}  order by date desc ")// 按照日期逆序
        ArrayList<RecRecord<Rec>> GetRecRecordHeader(String gId, String rec_type);

        @Delete("delete from rec_record where id=#{recRecordId}")
        void DeleteRecRecord(Integer recRecordId);

        @Delete("delete from ${rec_type} where id=#{recId}")
        void DeleteRecords(Integer recId, String rec_type);

        // 自行实现
        ArrayList<Rec> GetRecRecordData(String date, String gId, String rec_type);

        // 自行实现
        void InsertRecordData(Rec record, String date, String rec_type);

        // 自行实现
        void UpdateRecord(Rec record,String rec_type);

    }

}