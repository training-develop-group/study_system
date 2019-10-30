<<<<<<< HEAD
package com.example.study_system.service.impl;

import com.example.study_system.model.UserInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.util.UserUtil;
import org.springframework.stereotype.Service;

/**
 * author lindan.
 * date 2019/6/4.
 */
@Service
public class LoginServiceImpl extends BaseService implements ILoginService {

    @Override
    public UserInfo selectUser(String userName, String password) {
        //password 加密
        password = UserUtil.EncoderByMd5(password);
        return userInfoMapper.selectByUserNameAndPassword(userName,password);
    }
}
=======
package com.example.study_system.service.impl;

import com.example.study_system.model.UserInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.ILoginService;
import com.example.study_system.util.UserUtil;
import org.springframework.stereotype.Service;

/**
 * author lindan.
 * date 2019/6/4.
 */
@Service
public class LoginServiceImpl extends BaseService implements ILoginService {

    @Override
    public UserInfo selectUser(String userName, String password) {
        //password 加密
        password = UserUtil.EncoderByMd5(password);
        return userInfoMapper.selectByUserNameAndPassword(userName,password);
    }
}
>>>>>>> remotes/origin/dev-wtq
