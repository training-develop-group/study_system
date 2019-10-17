package com.example.study_system.service.iface;

import com.example.study_system.model.ResourceInfo;

public interface IResourceService {
	
	int selectResource (Integer resId);

	ResourceInfo modifyResourceByResName(String resName);

	int deleteResourceByresId(Integer resId);
	
}
