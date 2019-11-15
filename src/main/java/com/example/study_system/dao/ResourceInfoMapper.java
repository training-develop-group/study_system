package com.example.study_system.dao;

import com.example.study_system.model.ResourceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResourceInfoMapper {

    int insert(ResourceInfo resourceInfo);

    int deleteByPrimaryKey(Long resId);

    int updateByPrimaryKey(Long resId, String resName);

    ResourceInfo selectByPrimaryKey(Long resId);

    List<ResourceInfo> selectList(@Param("resName") String resName, @Param("resType") Integer resType);        //获取资源列表

    int selectListCount();

    int updateStatus(Long resId,@Param("status")int status);

}