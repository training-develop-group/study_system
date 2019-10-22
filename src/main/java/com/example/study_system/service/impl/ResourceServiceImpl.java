package com.example.study_system.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.example.study_system.model.ResourceInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IResourceService;
@Service
public class ResourceServiceImpl extends BaseService implements IResourceService {

	@Override
    public ResourceInfo selectByPrimaryKey(String resId) {
       return resourceInfoMapper.selectByPrimaryKey(resId);
    }
	
	@Override
	public int updateByPrimaryKey(Long resId) {
		return resourceInfoMapper.updateByPrimaryKey(resId);
	}
	
	@Override
	public int deleteByPrimaryKey(Long resId) {
		return resourceInfoMapper.deleteByPrimaryKey(resId);
	}
	
	@Override
	public List<ResourceInfo> selectList() {
		return resourceInfoMapper.selectList();
	}
	
	@Override
	public int insert(ResourceInfo resourceInfo) {
		return resourceInfoMapper.insert(resourceInfo);
	}
	
	
	
	
	

   
    
    
}
