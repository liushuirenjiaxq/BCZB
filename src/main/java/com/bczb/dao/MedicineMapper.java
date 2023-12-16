package com.bczb.dao;

import com.bczb.pojo.Medicine;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface MedicineMapper {
    @Insert("insert into medicine (name,latin_name,origin,medicinal_parts,pro_area,harvest_time,flavor,channel_tropism,effect,usage_dosage,identify_xz,identify_xw,identify_lh,identify_hl,primary_application) " +
            "values (#{name}, #{latinName}, #{origin}, #{medicinalParts}, #{proArea}, #{harvestTime}, #{flavor}, #{channelTropism}, #{effect}, #{usageDosage}, #{identifyXz}, #{identifyXw}, #{identifyLh}, #{identifyHl}, #{primaryApplication})")
    public void InsertMedicine(Medicine medicine);

    @Update("update medicine set name = #{name},latin_name = #{latinName},origin = #{origin},medicinal_parts = #{medicinalParts},pro_area = #{proArea},harvest_time = #{harvestTime},flavor = #{flavor}," +
            "channel_tropism = #{channelTropism},effect = #{effect},usage_dosage = #{usageDosage},identify_xz = #{identifyXz},identify_xw = #{identifyXw},identify_lh = #{identifyLh},identify_hl = #{identifyHl},primary_application = #{primaryApplication} where id = #{id}")
    public void UpdateMedicine(Medicine medicine);

    @Delete("delete from medicine where id = #{id}")
    public void DeleteMedicine(Integer id);

    @Select("select"
            +" id, name, latin_name as latinName, origin as origin, medicinal_parts as medicinalParts, pro_area as proArea, harvest_time as harvestTime," +
            " flavor, channel_tropism as channelTropism, effect, usage_dosage as usageDosage, identify_xz as identifyXz, identify_xw as identifyXw, identify_lh as identifyLh, identify_hl as identifyHl, primary_application as primaryApplication"
            +" from medicine")
    public ArrayList<Medicine> selectMedicineList();

    @Select("select id from medicine where name = #{name}")
    public Integer selectMedicineId(String name);
}
