package com.example.study_system.service.impl;

import com.example.study_system.model.ResourceInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IResourceService;

public class ResourceServiceImpl extends BaseService implements IResourceService {

	@Override
    public int selectResource (Integer resId) {
       return resourceInfoMapper.selectResource(resId);
    }
	
	@Override
	public ResourceInfo modifyResourceByResName(String resName) {
		return resourceInfoMapper.modifyResourceByResName(resName);
	}
	
	@Override
	public int deleteResourceByresId(Integer resId) {
		return resourceInfoMapper.deleteResourceByresId(resId);
	}
	
	
}
