package com.bczb.dao;

import com.bczb.pojo.vo.Idname;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bczb.pojo.User;

import java.util.ArrayList;

@Mapper
public interface UserMapper {

    @Insert("insert into user (password, name, tele, power, status) values (#{password}, #{name}, #{tele}, #{power}, #{status})")
    public void InsertUser(User user);


    @Select("select count(*) from user where name = #{name} and status != 1")
    public int countByName(String name);

    @Select("select "+
            " id, name,password,power, tele,status"+
            " from user where name = #{name} and status != 1")
    public User selectByName(String name);

    // 查询所有用户id和姓名
    @Select("select id,name from user where status != 1")
    public ArrayList<Idname> selectNames();

    // 通过 id 获取用户信息
    @Select("select "+
            " id, name,password,power, tele,status"+
            " from user where id = #{id} and status != 1")
    public User selectById(Integer id);

    // 通过 id 判断用户是否存在
    @Select("select count(*) from user where id = #{id} and status != 1")
    public int countById(int id);

    @Update("update user set name = #{name}, tele = #{tele} where id = #{id}")
    public void updateUser(Integer id,String name, String tele);

    // 修改密码
    @Update("update user set password = #{password} where name = #{name}")
    public void updatePass(String name, String password);
}
