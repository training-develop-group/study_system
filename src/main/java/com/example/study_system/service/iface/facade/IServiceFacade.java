package com.example.study_system.service.iface.facade;

<<<<<<< HEAD

import com.example.study_system.service.iface.IJPaperQuestionService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IPaperInfoService;
import com.example.study_system.service.iface.IQuestionInfoService;
=======
import com.example.study_system.service.iface.IJQuestionOptionService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.example.study_system.service.iface.IUserService;
>>>>>>> remotes/origin/dev-wtq

/**
 * author lindan. date 2019/6/4.
 */
public interface IServiceFacade {
<<<<<<< HEAD
    ILoginService getUserService();
    
    IPaperInfoService getPaperInfoService();
    
    IJPaperQuestionService getPaperQuestionService();
    
    IQuestionInfoService getQuestionInfoService();
=======
	ILoginService getUserService();

	IQuestionInfoService getQuestionInfoService();

	IJQuestionOptionService getJQuestionOptionService();
>>>>>>> remotes/origin/dev-wtq
}