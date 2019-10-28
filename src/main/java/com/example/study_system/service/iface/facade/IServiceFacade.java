package com.example.study_system.service.iface.facade;

import com.example.study_system.service.iface.IJQuestionOptionService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.example.study_system.service.iface.ITaskService;
import com.example.study_system.service.iface.IUserService;

/**
 * author lindan. date 2019/6/4.
 */
public interface IServiceFacade {
	ILoginService getUserService();
	
	IQuestionInfoService getQuestionInfoService();

	IJQuestionOptionService getJQuestionOptionService();
	
	ITaskService getTaskService();
}