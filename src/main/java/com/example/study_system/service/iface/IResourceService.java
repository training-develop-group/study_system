package com.example.study_system.service.iface;

import java.util.List;

import com.example.study_system.model.ResourceInfo;

public interface IResourceService {
	
//	int selectResource (Integer resId);
//
//	ResourceInfo modifyResourceByResName(String resName);
//
//	int deleteResourceByresId(Integer resId);
	
	ResourceInfo selectByPrimaryKey(String resId);
	 
	int updateByPrimaryKey(Long resId);
	
	int deleteByPrimaryKey(Long resId);
	
	List<ResourceInfo> selectList();		//查询全部
	
	int insert(ResourceInfo resourceInfo);		//上传资源
	
	

	
	
}
