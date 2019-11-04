package com.example.study_system.service.iface.facade;

import com.example.study_system.service.iface.ICommentInfoService;

import com.example.study_system.service.iface.IJUserVideoLogService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IPaperInfoService;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.example.study_system.service.iface.ITaskService;
import com.example.study_system.service.iface.IUserService;
import com.example.study_system.service.iface.IResourceService;

/**
 * author lindan. date 2019/6/4.
 */
public interface IServiceFacade {
	ILoginService getUserService();

	IQuestionInfoService getQuestionInfoService();

	ITaskService getTaskService();

	IPaperInfoService getPaperInfoService();
	
	ICommentInfoService getCommentInfoService();
	
	IUserService getUseService();
	
	ILoginService getLoginService();

    
    IResourceService getResourceService();
    
    IJUserVideoLogService getJUserVideoLogService();
}