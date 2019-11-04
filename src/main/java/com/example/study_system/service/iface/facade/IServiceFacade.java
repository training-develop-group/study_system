package com.example.study_system.service.iface.facade;

import com.example.study_system.service.iface.ICommentInfoService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IPaperInfoService;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.example.study_system.service.iface.ITaskService;
import com.example.study_system.service.iface.IUserService;

/**
 * author lindan. date 2019/6/4.
 */
public interface IServiceFacade {

	ITaskService getTaskService();


	IQuestionInfoService getQuestionInfoService();

	ILoginService getLoginService();
		
	IPaperInfoService getPaperInfoService();
	
	ICommentInfoService getCommentInfoService();

}