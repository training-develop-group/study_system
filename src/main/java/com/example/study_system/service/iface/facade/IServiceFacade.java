package com.example.study_system.service.iface.facade;

import com.example.study_system.service.iface.*;

/**
 * author lindan. date 2019/6/4.
 */
public interface IServiceFacade {
    IQuestionInfoService getQuestionInfoService();

    ITaskService getTaskService();

    IPaperInfoService getPaperInfoService();

    ICommentInfoService getCommentInfoService();

    ILoginService getLoginService();

    IResourceService getResourceService();

    IJUserVideoLogService getJUserVideoLogService();

    IStatService getStatService();
}