package com.example.study_system.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.study_system.model.JUserVideoLog;
import com.example.study_system.model.ResourceInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IJUserVideoLogService;

import it.sauronsoftware.jave.Encoder;
@Service
public class JUserVideoLogServiceImpl extends BaseService implements IJUserVideoLogService {

	@Override
	public long recordVideoPlaybackTime(Long seconds) {
		return userVideoLogMapper.insertVideoPlaybackTime(seconds);
	}
	
	
	@Override
	public long getVideoPlaybackTime(Long ref) {
		List<JUserVideoLog> a = userVideoLogMapper.selectByPrimaryKey(ref);
		return a.get(0).getSeconds();
	}
	
	
}
