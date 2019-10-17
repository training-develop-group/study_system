package com.example.study_system.dao;

import com.example.study_system.model.ResourceInfo;

public interface ResourceInfoMapper {
//    int deleteByPrimaryKey(Long resId);
//
//    int insert(ResourceInfo record);
//
//    int insertSelective(ResourceInfo record);
//
//    ResourceInfo selectByPrimaryKey(Long resId);
//
//    int updateByPrimaryKeySelective(ResourceInfo record);
//
//    int updateByPrimaryKey(ResourceInfo record);
//    
    int selectResource (Integer resId);		//获取资源详情
    
    ResourceInfo modifyResourceByResName(String resName);		//修改资源名
    
    int deleteResourceByresId(Integer resId);		//删除资源
}