package com.example.study_system.service.impl;

import org.springframework.stereotype.Service;

import com.example.study_system.model.JUserVideoLog;
import com.example.study_system.model.ResourceInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IJUserVideoLogService;
@Service
public class JUserVideoLogServiceImpl extends BaseService implements IJUserVideoLogService {

	
	@Override
	public int getVideoPlaybackTime(JUserVideoLog seconds) {
		return userVideoLogMapper.insertVideoPlaybackTime(seconds);
	}
}
