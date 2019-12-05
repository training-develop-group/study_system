package com.example.study_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.model.UserActionLog;

@RestController
@RequestMapping("/action-log")
public class UserActionLogController extends BaseController{
	/**
	 * 添加操作日志
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "",method = RequestMethod.POST)
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
