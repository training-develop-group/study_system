package com.example.study_system.service.iface.facade;

<<<<<<< HEAD
<<<<<<< HEAD

import com.example.study_system.service.iface.IJPaperQuestionService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IPaperInfoService;
import com.example.study_system.service.iface.IQuestionInfoService;
=======
=======
>>>>>>> remotes/origin/dev-wtq
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
<<<<<<< HEAD
    ILoginService getUserService();
    
    IPaperInfoService getPaperInfoService();
    
    IJPaperQuestionService getPaperQuestionService();
    
    IQuestionInfoService getQuestionInfoService();
=======
=======
>>>>>>> remotes/origin/dev-wtq
	ILoginService getUserService();

	IQuestionInfoService getQuestionInfoService();

	IJQuestionOptionService getJQuestionOptionService();
<<<<<<< HEAD
>>>>>>> remotes/origin/dev-wtq
=======
>>>>>>> remotes/origin/dev-wtq
}