package com.example.study_system.service.impl.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.study_system.service.iface.ICommentInfoService;
import com.example.study_system.service.iface.IJUserVideoLogService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IPaperInfoService;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.example.study_system.service.iface.IResourceService;
import com.example.study_system.service.iface.IStatService;
import com.example.study_system.service.iface.ITaskService;
import com.example.study_system.service.iface.IUserService;
import com.example.study_system.service.iface.facade.IServiceFacade;

/**
 * author lindan. date 2019/6/4.
 */
@Service
public class ServiceFacadeImpl implements IServiceFacade {
    @Autowired
    ILoginService loginService;

	@Autowired
	IQuestionInfoService questionInfoService;
	@Autowired
	ILoginService userService;
	@Autowired
	ITaskService taskService;
	@Autowired
	IPaperInfoService paperInfoService;
	@Autowired
	IUserService taskUserService;
	@Autowired
	ICommentInfoService commentInfoService;
	
	public IUserService getUseService() {
		return taskUserService;
	};

	
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

	@Override
    public ILoginService getLoginService() {
        return loginService;
    }
    
    @Autowired
    IResourceService resourceService;

    @Override
    public IResourceService getResourceService() {
        return resourceService;
    }
    
    @Autowired
    IJUserVideoLogService userVideoLogService;

    @Override
    public IJUserVideoLogService getJUserVideoLogService() {
        return userVideoLogService;
    }
    
    @Autowired
    IStatService statService;
    @Override
	public IStatService getStatService() {
		return statService;
	}
}
