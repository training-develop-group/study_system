package com.example.study_system.service.impl;


import com.example.study_system.model.JUserVideoLog;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IJUserVideoLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JUserVideoLogServiceImpl extends BaseService implements IJUserVideoLogService {

    @Override
    public long recordVideoPlaybackTime(Long resId, Long seconds) {
        return userVideoLogMapper.insertVideoPlaybackTime(resId, seconds);
    }

    @Override
    public Long getVideoPlaybackTime(Long resId) {
        Long a = userVideoLogMapper.selectByResId(resId);
        return a;
    }
}
