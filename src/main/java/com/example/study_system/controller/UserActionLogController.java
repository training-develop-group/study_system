package com.example.study_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.model.UserActionLog;

@RestController
@RequestMapping("/user-action-log")
public class UserActionLogController extends BaseController{
	@RequestMapping(value = "/actionlog",method = RequestMethod.GET)
    public ResultDTO userActionLog(UserActionLog request) {
        if (request == null) {
            return noData();
        }
        int type = 0;
        String url = "";
        int status = 0;
        String remark = "";
        String userId = "";
        String params = "";
        int insertPaper = serviceFacade.getIUserActionLogServive().insert(type , url , status , remark , userId , params);
        return success(request);
    }
}
