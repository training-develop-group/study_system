package com.example.study_system.service.impl.facade;

import com.example.study_system.service.iface.ICommentInfoService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IPaperInfoService;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.example.study_system.service.iface.ITaskService;
import com.example.study_system.service.iface.IUserService;
import com.example.study_system.service.iface.facade.IServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author lindan. date 2019/6/4.
 */
@Service
public class ServiceFacadeImpl implements IServiceFacade {

	@Autowired
	IQuestionInfoService questionInfoService;
	@Autowired
	ILoginService userService;
	@Autowired
	ITaskService taskService;
	@Autowired
	IPaperInfoService paperInfoService;
	@Autowired
	ICommentInfoService commentInfoService;

	@Override
	public ICommentInfoService getCommentInfoService() {
		return commentInfoService;
	}

	@Override
	public IPaperInfoService getPaperInfoService() {
		return paperInfoService;
	}

	@Override
	public ITaskService getTaskService() {
		return taskService;
	}

	@Override
	public ILoginService getUserService() {
		return userService;
	}

	@Override
	public IQuestionInfoService getQuestionInfoService() {
		return questionInfoService;
	}
}
