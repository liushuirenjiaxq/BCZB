package com.bczb.dao;

import com.bczb.pojo.TeamMate;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface TeamMateMapper {

    @Insert("insert into team_mate (ex_id, u_id, join_date) values (#{exId}, #{u_id}, now())")
    void InsertTeam(Integer exId, int u_id);

    @Delete("delete from team_mate where t_id = #{id}")
    void deleteTeamMate(Integer id);

    // 通过 id 判断团队成员是否存在
    @Select("select count(*) from team_mate where t_id = #{id}")
    boolean isTeamMateExist(Integer id);

    @Select("select "
    +" t_id, ex_id as exId, u_id as uId, join_date as joinDate"
    +" from team_mate ")
    ArrayList<TeamMate> selectAllTeamMate();

    @Select("select "
    +" t_id as id, ex_id as exId, u_id as uId, join_date as joinDate"
    +" from team_mate where ex_id = #{exId}")
    public ArrayList<TeamMate> selectAllTeamMateByExId(Integer exId);

    @Select("select count(*) from team_mate where ex_id = #{exId} and u_id = #{u_id}")
    boolean isTeamMateExistByExIdAndUId(Integer exId, Integer u_id);
    
}
