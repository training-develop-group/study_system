package com.example.study_system.service.iface.facade;


import com.example.study_system.service.iface.IJPaperQuestionService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IPaperInfoService;
import com.example.study_system.service.iface.IQuestionInfoService;

/**
 * author lindan.
 * date 2019/6/4.
 */
public interface IServiceFacade {
    ILoginService getUserService();
    
    IPaperInfoService getPaperInfoService();
    
    IJPaperQuestionService getJPaperQuestionService();
    
    IQuestionInfoService getIQuestionInfoService();
}