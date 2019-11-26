package com.example.study_system.service.iface;

import java.util.List;

import com.example.study_system.model.JUserVideoLog;

public interface IJUserVideoLogService {

	long recordVideoPlaybackTime(Long resId, Long seconds);

	Long getVideoPlaybackTime(Long resId);
}
