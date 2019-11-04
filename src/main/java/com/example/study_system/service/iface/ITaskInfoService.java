package com.example.study_system.service.iface;

import java.util.List;

import com.example.study_system.model.JQuestionOption;
import com.example.study_system.model.TaskInfo;

public interface ITaskInfoService {
	
	List<TaskInfo> selectTaskInfo();
}
