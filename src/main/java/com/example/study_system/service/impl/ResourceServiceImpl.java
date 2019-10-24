package com.example.study_system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.study_system.model.ResourceInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IResourceService;
@Service
public class ResourceServiceImpl extends BaseService implements IResourceService {

	/**
	 * 重写上传资源方法
	 * @param resourceInfo
	 * @return
	 */
	@Override
	public int uploadResourceInfo(ResourceInfo resourceInfo) {
		return resourceInfoMapper.insert(resourceInfo);
	}
	
	/**
	 * 重写删除资源方法
	 * @param resId
	 * @return
	 */
	@Override
	public int deleteResourceInfoByResId(Long resId) {
		return resourceInfoMapper.deleteByPrimaryKey(resId);
	}
	
	/**
	 * 重写修改资源名方法
	 * @param resId
	 * @return
	 */
	@Override
	public int modifyResourceNameByResId(Long resId) {
		return resourceInfoMapper.updateByPrimaryKey(resId);
	}
	
	/**
	 * 重写获取资源详情方法
	 * @param resId
	 * @return
	 */
	@Override
    public ResourceInfo getResourceDetailsByResId(String resId) {
       return resourceInfoMapper.selectByPrimaryKey(resId);
    }
	
	/**
	 * 重写获取资源列表方法
	 * @return
	 */
	@Override
	public List<ResourceInfo> getResourceList() {
		return resourceInfoMapper.selectList();
	}
	
	@Override
	public int getResourceListCount() {
		return resourceInfoMapper.selectListCount();
	}
	
	
	
	
	
	
	

   
    
    
}
