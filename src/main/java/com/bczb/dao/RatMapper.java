package com.bczb.dao;

import com.bczb.pojo.Rat;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper // Group 为关键字，注意使用 `` 包裹
public interface RatMapper {

        @Insert("insert into `rat`( g_id, gender, cage, r_index) values( #{gId}, #{gender}, #{cage}, #{rIndex})")
        public void insertRat(String gId, String gender, Integer cage, String rIndex);

        @Update("update rat set gender = #{gender}, cage = #{cage}, r_index = #{rIndex} where r_id = #{rId}")
        public void updateRat(Rat rat);

        @Select("select count(*) from rat where  g_id = #{gId} and gender = #{gender} ")
        public Integer selectGenderCount(String gId, String gender);

        @Select("select count(*) from rat where  g_id = #{gId} and gender = #{gender} and cage = #{cage} ")
        public Integer selectCageCount(String gId, String gender, Integer cage);

        @Select("select count(*) from rat where  g_id = #{gId} and r_index = #{rIndex} ")
        public Integer selectRIndexCount(String gId, String rIndex);

        @Select("select experiment.status from rat, `group`, experiment where rat.g_id = `group`.g_id and `group`.ex_id = experiment.ex_id and rat.r_id = #{rId} ")
        public Integer selectStatusByRId(Integer rId);

        @Select("select count(*) from rat where r_id = #{rId} ")
        public boolean isRatExist(Integer rId);

        @Delete("delete from rat where r_id = #{rId} ")
        public void deleteRat(Integer rId);

        @Select("select "
                        + " rat.r_id as rId,  rat.g_id as gId, rat.gender, rat.cage, rat.r_index as rIndex "
                        + " from rat,`group`,experiment  where rat.g_id = `group`.g_id and `group`.ex_id = experiment.ex_id and experiment.ex_id = #{exId}")
        public ArrayList<Rat> selectAllRatByExId(Integer exId);

        @Select("select "
                        + " r_id as rId,  g_id as gId, gender, cage, r_index as rIndex "
                        + " from rat")
        public ArrayList<Rat> selectAllRat();

        @Select("select "
                        + " r_id as rId,  g_id as gId, gender, cage, r_index as rIndex "
                        + " from rat where r_id = #{rId}")
        public Rat selectRatByRId(Integer rId);

        @Select("select"
                        + " r_id as rId,  g_id as gId, gender, cage, r_index as rIndex "
                        + " from rat where g_id = #{gId}")
        public ArrayList<Rat> selectByGId(String gId);
}
