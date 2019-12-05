package com.example.study_system.service.iface;

import com.example.study_system.model.UserInfo;

public interface IUserService {
	String updateUserPassWord(String originalPassword,String newPassWord,String passwordValidation,UserInfo userInfo);
}
