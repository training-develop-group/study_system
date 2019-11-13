package com.example.study_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.dto.UserTaskDTO;
import com.example.study_system.model.JUserQuesAnswerRecord;
import com.example.study_system.model.JUserTask;
import com.example.study_system.model.TaskInfo;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/stat")
public class StatController extends BaseController{
	//获取任务类型百分比
		@RequestMapping(value = "/task-type",method = RequestMethod.GET)
		public ResultDTO selectTaskTypePercentage() {
			TaskInfo res = serviceFacade.getStatService().selectStat();
			return success(res);
		}
		
		//获取学生任务完成百分比
		@RequestMapping(value = "/task",method = RequestMethod.GET)
		public ResultDTO select() {
			JUserTask res = serviceFacade.getStatService().selectJUsePaperPercentage();
			System.out.println(res);
			return success(res);
		}
		
		//获取学生作答正确率
		@RequestMapping(value = "/answer",method = RequestMethod.GET)
		public ResultDTO selectJUserQuesAnswerRecord() {
			JUserQuesAnswerRecord res = serviceFacade.getStatService().choiceJUserQuesAnswerRecord();
			return success(res);
		}
		
		//获取统计列表
		@RequestMapping(value = "/list",method = RequestMethod.GET)
		public ResultDTO selectTaskInfo(@RequestParam(value = "userName") String userName , 
										@RequestParam(value = "pageNum") Integer pageNum , 
										@RequestParam(value = "pageSize") Integer pageSize) {
			System.out.println(userName);
			PageInfo<UserTaskDTO> res = serviceFacade.getStatService().statisticalList(userName , pageNum , pageSize);
			return success(res);
		}
}
