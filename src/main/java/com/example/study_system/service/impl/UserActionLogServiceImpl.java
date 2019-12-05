package com.example.study_system.service.impl;

import org.springframework.stereotype.Service;

import com.example.study_system.model.UserActionLog;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IUserActionLogService;

@Service
public class UserActionLogServiceImpl extends BaseService implements IUserActionLogService{
	@Override
	public int insert(int type , String url , int status , String remark , String userId , String params) {
		UserActionLog insertUserActionLog = new UserActionLog();
		insertUserActionLog.setType(1);
		insertUserActionLog.setUrl(url);
		insertUserActionLog.setRemark(remark);
		insertUserActionLog.setUserId(userId);
		insertUserActionLog.setStatus(status);
		insertUserActionLog.setParams(params);
		return userActionLogMapper.insert(insertUserActionLog);
	}
}
