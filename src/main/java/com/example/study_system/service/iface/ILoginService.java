package com.example.study_system.service.iface;

import com.example.study_system.model.UserInfo;

/**
 * author lindan.
 * date 2019/6/4.
 */
public interface ILoginService {

    UserInfo selectUser(String userName, String password);
}
