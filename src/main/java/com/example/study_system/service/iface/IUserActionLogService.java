package com.example.study_system.service.iface;

import com.example.study_system.model.UserActionLog;

public interface IUserActionLogService {
	int insert(int type , String url , int status , String remark , String userId , String params);
}
