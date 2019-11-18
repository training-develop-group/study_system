package com.example.study_system.controller.base;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.emun.ResultEmun;
import com.example.study_system.service.iface.facade.IServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * author lindan.
 * date 2019/6/4.
 */
@Component
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected IServiceFacade serviceFacade;

    protected <T> ResultDTO<T> noData() {
        return new ResultDTO<>(ResultEmun.NO_DATA);
    }

    protected <T> ResultDTO<T> success(T data) {
        return new ResultDTO<>(ResultEmun.SUCCESS, data);
    }
    
    protected <T> ResultDTO<T> validationError() {
        return new ResultDTO<>(ResultEmun.VALIDATION_ERROR);
    }
    
    protected <T> ResultDTO<T> uploadFileIsEmpty() {
        return new ResultDTO<>(ResultEmun.UPLOAD_FILE_ISEMPTY);
    }
    
    protected <T> ResultDTO<T> thisFileFormatIsNotSupported() {
        return new ResultDTO<>(ResultEmun.THIS_FILE_FORMAT_IS_NOT_SUPPORTED);
    }
}
