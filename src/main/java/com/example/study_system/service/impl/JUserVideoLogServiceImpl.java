package com.example.study_system.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.springframework.stereotype.Service;

import com.example.study_system.model.JUserVideoLog;
import com.example.study_system.model.ResourceInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IJUserVideoLogService;

import it.sauronsoftware.jave.Encoder;
@Service
public class JUserVideoLogServiceImpl extends BaseService implements IJUserVideoLogService {

	@Override
	public int getVideoPlaybackTime() {
		
		File source = new File("C:\\Users\\CloudEasyServer\\Desktop\\movie.mp4");

		Encoder encoder = new Encoder();

		long duration = 0;

		try {

			it.sauronsoftware.jave.MultimediaInfo m = encoder.getInfo(source);

			duration = m.getDuration() / 1000;
			
			logger.info("此视频时长为:" + duration + "秒！");
			
		} catch (Exception e) {

			e.printStackTrace();

		}
		System.out.println(duration);
		return userVideoLogMapper.insertVideoPlaybackTime(duration);
	}
}
