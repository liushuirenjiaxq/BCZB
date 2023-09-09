package com.bczb.dao;

import com.bczb.pojo.Group;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface GroupMapper {

    @Select("select count(*) from `group` where ex_id = #{exId} and name = #{name} ")
    public boolean isGroupNameExist(Integer exId, String name);

    @Insert("insert into `group`(g_id ,ex_id, name) values(#{id},#{exId}, #{name}) ")
    public void insertGroup(String id ,Integer exId, String name);

    @Select("select count(*) from `group` where g_id = #{gId} ")
    public boolean isGroupExist(String gId);


    @Select("select count(*) from `experiment` where ex_id = #{exId} ")
    public boolean isExpExist(Integer exId);

    @Select("select "
    +" g_id as gId,  ex_id as exId, name"
    +" from `group` where ex_id = #{exId} ")
    public ArrayList<Group> selectGroupByExId(Integer exId);

    @Select("select "
    +"g_id as gId,  ex_id as exId, name"
    +" from `group` where g_id = #{gId} ")
    public boolean isExistByGIdAndName(String gId, String name);

    @Select("update `group` set name = #{name} where g_id = #{gId}")
    public void updateGroup(String gId, String name);

    @Select("select "
    +"g_id as gId,  ex_id as exId, name"
    +" from `group` ")
    public ArrayList<Group> selectAllGroups();

    @Select("select "
    +"g_id as gId,  ex_id as exId, name"
    +" from `group` where g_id = #{gId} ")
    public Group selectByGId(String gId);

}
