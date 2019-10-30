package com.example.study_system.service.iface;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.study_system.model.ResourceInfo;

public interface IResourceService {
	
//	int selectResource (Integer resId);
//
//	ResourceInfo modifyResourceByResName(String resName);
//
//	int deleteResourceByresId(Integer resId);
	
//	int uploadResourceInfo(ResourceInfo resourceInfo, String video_path, String ffmpeg_path);		//上传资源 (对应Mapper是insert方法)
	
	int deleteResourceInfoByResId(Long resId);		//删除资源 (对应Mapper是deleteByPrimaryKey方法)
	
	int modifyResourceNameByResId(Long resId, String resName);		//修改资源名 (对应Mapper是updateByPrimaryKey方法)
	
	ResourceInfo getResourceDetailByResId(Long resId);		//获取资源详情 (对应Mapper是selectByPrimaryKey方法)
	
	List<ResourceInfo> getResourceList();		//获取资源列表 (对应Mapper是selectList方法)
	
	int getResourceListCount();			//获取资源总数

	int uploadResourceInfo(ResourceInfo resourceInfo, MultipartFile file, String ffmpeg_path);

	
	
	
	
	

	
	
}
