package com.example.study_system.service.impl.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.study_system.service.iface.IJUserVideoLogService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IResourceService;
import com.example.study_system.service.iface.facade.IServiceFacade;

/**
 * author lindan.
 * date 2019/6/4.
 */
@Service
public class ServiceFacadeImpl implements IServiceFacade {
    @Autowired
    ILoginService loginService;

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
}
