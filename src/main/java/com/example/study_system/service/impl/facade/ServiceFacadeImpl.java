package com.example.study_system.service.impl.facade;

import com.example.study_system.service.iface.ICommentInfoService;
import com.example.study_system.service.iface.IJPaperQuestionService;
import com.example.study_system.service.iface.IJQuestionOptionService;
import com.example.study_system.service.iface.IJUserPaperService;
import com.example.study_system.service.iface.IJUserQuesAnswerRecordService;
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
	ICommentInfoService commentInfoService;
	@Autowired
	IQuestionInfoService questionInfoService;
	@Autowired
	IJQuestionOptionService jQuestionOptionService;
	@Autowired
	ILoginService userService;
	@Autowired
	ITaskService taskService;
	@Autowired
	IPaperInfoService paperInfoService;
	@Autowired
	IJPaperQuestionService jPaperQuestionService;
	@Autowired
	IJUserPaperService jUserPaperService;
	@Autowired
	IJUserQuesAnswerRecordService jUserQuesAnswerRecordService;

	@Override
	public IJUserQuesAnswerRecordService getJUserQuesAnswerRecordService() {
		return jUserQuesAnswerRecordService;
	}

	@Override
	public IJUserPaperService getJUserPaperService() {
		return jUserPaperService;
	}

	@Override
	public IJPaperQuestionService getJPaperQuestionService() {
		return jPaperQuestionService;
	}

	@Override
	public IPaperInfoService getPaperInfoService() {
		return paperInfoService;
	}

	@Override
	public ICommentInfoService getCommentInfoService() {
		return commentInfoService;
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

	@Override
	public IJQuestionOptionService getJQuestionOptionService() {
		return jQuestionOptionService;
	}

}
