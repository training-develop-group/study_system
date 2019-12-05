package com.example.study_system.service.impl;

import org.springframework.stereotype.Service;

import com.example.study_system.model.UserInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IUserService;
import com.example.study_system.util.UserUtil;
@Service
public class UserPassWordServiceImpl extends BaseService implements IUserService{
	public String updateUserPassWord(String originalPassword,String newPassWord,String passwordValidation,UserInfo userInfo) {
		//password 加密
		newPassWord = UserUtil.EncoderByMd5(newPassWord);
		int UpdatePassWord = userInfoMapper.updateUserPassWord(newPassWord, userInfo.getUserId());
		if(UpdatePassWord>0) {
			return "修改成功";
		}else {
			return "操作失败";
		}
	}
}
