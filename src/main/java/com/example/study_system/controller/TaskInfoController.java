package com.example.study_system.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.model.JQuestionOption;
import com.example.study_system.model.TaskInfo;

@RestController
@RequestMapping("/stat")
public class TaskInfoController extends BaseController{
	
	@RequestMapping(value = "/task-type",method = RequestMethod.GET)
	public ResultDTO getTaskTypePercentage() {
		List<TaskInfo> res = serviceFacade.getTaskInfoService().selectTaskInfo();
		return success(res);
	}
}