package com.example.study_system.service.impl.facade;

import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.service.iface.IResourceService;
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

    @Override
    public ILoginService getUserService() {
        return userService;
    }
    
    @Autowired
    IResourceService resourceService;

    @Override
    public IResourceService getResourceService() {
        return resourceService;
    }
}
