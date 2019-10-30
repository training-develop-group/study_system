package com.example.study_system.service.iface;


public interface IJUserVideoLogService {

	long recordVideoPlaybackTime(Long seconds);

	long getVideoPlaybackTime(Long ref);
}
