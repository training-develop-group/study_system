package com.example.study_system.service.base;

import com.example.study_system.dao.UserInfoMapper;
import com.example.study_system.model.UserInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author lindan.
 * date 2019/10/10.
 */
public class BaseService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected UserInfoMapper userInfoMapper;

	public UserInfo selectById(Integer paperId) {
		// TODO Auto-generated method stub
		return null;
	}

}
