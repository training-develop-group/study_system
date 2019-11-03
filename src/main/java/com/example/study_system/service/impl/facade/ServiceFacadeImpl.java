package com.example.study_system.service.impl.facade;

<<<<<<< HEAD
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
=======
import com.example.study_system.service.iface.ICommentInfoService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IPaperInfoService;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.example.study_system.service.iface.ITaskService;
>>>>>>> dev-wtq
import com.example.study_system.service.iface.IUserService;
>>>>>>> remotes/origin/dev-wtq
import com.example.study_system.service.iface.facade.IServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author lindan. date 2019/6/4.
 */
@Service
public class ServiceFacadeImpl implements IServiceFacade {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
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
=======

=======

>>>>>>> remotes/origin/dev-wtq
	@Autowired
	IQuestionInfoService questionInfoService;
	@Autowired
	IJQuestionOptionService jQuestionOptionService;
	@Autowired
	ILoginService userService;

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

<<<<<<< HEAD
>>>>>>> remotes/origin/dev-wtq
=======
>>>>>>> remotes/origin/dev-wtq
=======

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
>>>>>>> dev-wtq
}
