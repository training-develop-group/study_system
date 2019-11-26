package com.example.study_system.service.impl.facade;

import com.example.study_system.service.iface.*;
import com.example.study_system.service.iface.facade.IServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    ITaskService taskService;
    @Autowired
    IPaperInfoService paperInfoService;
    @Autowired
    ICommentInfoService commentInfoService;
    @Autowired
    IStatService statService;
    @Autowired
    IJUserVideoLogService userVideoLogService;
    @Autowired
    IUserActionLogService userActionLogService;
    @Autowired
    IResourceService resourceService;

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
    public IQuestionInfoService getQuestionInfoService() {
        return questionInfoService;
    }

    @Override
    public ILoginService getLoginService() {
        return loginService;
    }

    @Override
    public IResourceService getResourceService() {
        return resourceService;
    }

    @Override
    public IJUserVideoLogService getJUserVideoLogService() {
        return userVideoLogService;
    }

    @Override
    public IUserActionLogService getIUserActionLogServive() {
        return userActionLogService;
    }
    
    @Override
    public IStatService getStatService() {
        return statService;
    }
}
