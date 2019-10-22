package com.example.study_system.dao;

import java.util.List;

import com.example.study_system.model.ResourceInfo;

public interface ResourceInfoMapper {
    int deleteByPrimaryKey(Long resId);		//删除资源

    int insert(ResourceInfo resourceInfo);		//上传资源

    int insertSelective(ResourceInfo record);

    ResourceInfo selectByPrimaryKey(String resId);		//获取资源详情

    int updateByPrimaryKeySelective(ResourceInfo record);

    int updateByPrimaryKey(Long resId);		//修改资源名
    
    List<ResourceInfo> selectList();		//查询全部
    
    
    

}