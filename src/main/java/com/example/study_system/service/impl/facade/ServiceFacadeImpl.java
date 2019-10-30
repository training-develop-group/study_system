package com.example.study_system.service.impl.facade;

import com.example.study_system.service.iface.IJPaperQuestionService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IPaperInfoService;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.example.study_system.service.iface.facade.IServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author lindan.
 * date 2019/6/4.
 */
@Service
public class ServiceFacadeImpl implements IServiceFacade {
    @Autowired
    ILoginService userService;
    @Autowired
    IPaperInfoService paperInfoService;
    @Autowired
    IJPaperQuestionService jPaperQuestionService;
    @Autowired
    IQuestionInfoService questionInfoService;

    @Override
    public ILoginService getUserService() {
        return userService;
    }
    
    @Override
    public IPaperInfoService getPaperInfoService() {
        return paperInfoService;
    }
    
    @Override
    public IJPaperQuestionService getPaperQuestionService() {
        return jPaperQuestionService;
    }
    
    @Override
    public IQuestionInfoService getQuestionInfoService() {
        return questionInfoService;
    }
}
