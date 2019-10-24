package com.example.study_system.service.iface.facade;


import com.example.study_system.service.iface.IJUserVideoLogService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IResourceService;

/**
 * author lindan.
 * date 2019/6/4.
 */
public interface IServiceFacade {
    ILoginService getUserService();
    
    IResourceService getResourceService();
    
    IJUserVideoLogService getJUserVideoLogService();
}