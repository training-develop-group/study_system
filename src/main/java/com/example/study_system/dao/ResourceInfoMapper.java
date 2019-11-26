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

	// 获取资源列表
    List<ResourceInfo> selectList(@Param("resName") String resName, @Param("resType") Integer resType);

    int selectListCount();

    int updateStatus(Long resId,@Param("status")int status);

}