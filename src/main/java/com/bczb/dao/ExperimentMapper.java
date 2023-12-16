package com.bczb.dao;

import com.bczb.pojo.Experiment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;



@Mapper
public interface ExperimentMapper {

    /*通过实验的名称查询实验的所有信息*/
    @Select ("select "
    +" ex_id as exId,  name, start_date as startDate, end_date as endDate, rat_name as ratName, status, owner_id as ownerId"
    +" from experiment where name = #{name}")
    public Experiment selectByName(String name) ;

    /*创建实验(插入新的实验) ex_id[自增] end_date[实验结束时候插入]*/
    @Insert("insert into experiment(name, start_date, rat_name, owner_id) values(#{name}, #{startDate}, #{ratName}, #{ownerId})")
    public void InsertExp(String name,String startDate, String ratName, int ownerId);

    /*查询所有实验的信息*/
    @Select("select "
    +" ex_id as exId,  name, start_date as startDate, end_date as endDate, rat_name as ratName, status, owner_id as ownerId"
    +" from experiment")
    public ArrayList<Experiment> selectExpList();

    @Update("update experiment set name = #{name}, start_date = #{startDate}, rat_name = #{ratName} where ex_id = #{exId}")
    public void updateExp(int exId, String name, String startDate, String ratName);

    @Update("update experiment set end_date = NOW() where ex_id = #{exId}")
    public void updateExpEndDate(Integer exId);

    @Update("update experiment set end_date = NULL where ex_id = #{exId}")
    public void updateExpEndDateNull(Integer exId);

    @Update("update experiment set status = #{status} where ex_id = #{exId}")
    public void updateExpStatus(Integer exId, Integer status);

    @Select("select count(*) from experiment where ex_id = #{exId}")
    public boolean isExpExist(int exId);

    @Select("select count(*) from user where id = #{ownerId}")
    public boolean isOwnerExist(int ownerId);

    @Select("select"
    +" ex_id as exId,  name, start_date as startDate, end_date as endDate, rat_name as ratName, status, owner_id as ownerId"
    +" from experiment where ex_id in (select ex_id from team_mate where u_id = #{uId})")
    public ArrayList<Experiment> selectExpListByUId(Integer uId);

    @Select("select"
    +" ex_id as exId,  name, start_date as startDate, end_date as endDate, rat_name as ratName, status, owner_id as ownerId"
    +" from experiment where ex_id = #{exId}")
    public Experiment selectByExId(Integer exId);

    @Insert("insert into team_mate(ex_id, u_id, join_date) values(#{exId}, #{ownerId}, now())")
    public void insertTeam(int exId, int ownerId);

    @Select("select experiment.name from experiment, `group` where experiment.ex_id = `group`.ex_id and `group`.g_id = #{gId}")
    public String selectNameByGId(String gId);

    
    @Select("select experiment.status from experiment, `group` where experiment.ex_id = `group`.ex_id and `group`.g_id = #{gId}")
    public int selectExpStatusByGid(String gId);
}
