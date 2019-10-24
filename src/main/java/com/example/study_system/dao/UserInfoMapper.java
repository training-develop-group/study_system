package com.example.study_system.dao;

import com.example.study_system.model.UserInfo;
import com.example.study_system.model.UserInfoWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserInfoMapper {
    int deleteByPrimaryKey(String userId);

    int insert(UserInfoWithBLOBs record);

    int insertSelective(UserInfoWithBLOBs record);

    UserInfoWithBLOBs selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(UserInfoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UserInfoWithBLOBs record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo selectByUserNameAndPassword(@Param("userName") String userName,@Param("password") String password);
}