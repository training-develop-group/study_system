package com.example.study_system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.study_system.model.TaskInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.ITaskInfoService;

@Service
public class TaskInfoServiceImpl extends BaseService implements ITaskInfoService{

	@Override
    public List<TaskInfo> selectTaskInfo() {
        return taskInfoMapper.selectAll();
    }
}