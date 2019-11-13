package com.example.study_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.study_system.model.UserInfo;
import com.example.study_system.model.UserInfoWithBLOBs;

public interface UserInfoMapper {
    int deleteByPrimaryKey(String userId);

    int insert(UserInfoWithBLOBs record);

    int insertSelective(UserInfoWithBLOBs record);

    UserInfoWithBLOBs selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(UserInfoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UserInfoWithBLOBs record);

    int updateByPrimaryKey(UserInfo record);

    List<UserInfo> selectUserAll();
    
    List<UserInfo> selectUserAll(@Param("userName")String userName);
    
    UserInfo selectByUserNameAndPassword(@Param("userName") String userName,@Param("password") String password);

	List<UserInfo> selectAllUserInfo(@Param("userName")String userName);
    
}