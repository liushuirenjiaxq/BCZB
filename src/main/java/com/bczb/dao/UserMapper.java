package com.bczb.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bczb.pojo.User;

@Mapper
public interface UserMapper {

    @Insert("insert into user (password, name, tele, power, status) values (#{password}, #{name}, #{tele}, #{power}, #{status})")
    public void InsertUser(User user);


    @Select("select count(*) from user where name = #{name} and status != 1")
    public int countByName(String name);

    @Select("select "+
            " id, name,power,password, tele,status"+
            " from user where name = #{name} and status != 1")
    public User selectByName(String name);

    // 通过 id 获取用户信息
    @Select("select "+
            " id, name,power,password, tele,status"+
            " from user where id = #{id} and status != 1")
    public User selectById(Integer id);

    // 通过 id 判断用户是否存在
    @Select("select count(*) from user where id = #{id} and status != 1")
    public int countById(int id);


    @Update("update user set name = #{name}, tele = #{tele} where id = #{id}")
    public void updateUser(Integer id,String name, String tele);
}
