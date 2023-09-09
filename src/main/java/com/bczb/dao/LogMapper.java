package com.bczb.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    @Insert("insert into log(u_id,action_name,method_name,api_url, ip, action_time,status,message) values(#{uid},#{actionName},#{methodName},#{apiUrl},#{ip},#{actionTime},#{status},#{message})")
    void insertLog(Integer uid, String actionName, String methodName,String apiUrl,String ip , String actionTime, Integer status,
            String message);
    
}
