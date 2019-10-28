package com.example.study_system.dao;

import java.util.List;

import com.example.study_system.model.ResourceInfo;

public interface ResourceInfoMapper {
    

    int insert(ResourceInfo resourceInfo);		//上传资源
    
    int deleteByPrimaryKey(Long resId);		//删除资源
    
    int updateByPrimaryKey(Long resId, String resName);		//修改资源名

    ResourceInfo selectByPrimaryKey(Long resId);		//获取资源详情
    
    List<ResourceInfo> selectList();		//获取资源列表
    
    int selectListCount();			//获取资源总数
    
    
    
    
    

}