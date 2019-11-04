package com.example.study_system.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.emun.ResultEmun;
import com.example.study_system.model.UserInfo;
import com.example.study_system.util.UserUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * author lindan.
 * date 2019/10/10.
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {
    @RequestMapping(method = RequestMethod.GET)
    public ResultDTO login(HttpSession session,
                           @RequestParam("userName") String userName,
                           @RequestParam("password") String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return new ResultDTO(ResultEmun.VALIDATION_ERROR);
        }
        logger.info("用户名："+userName+ ",密码: " +password);
        UserInfo userInfo = serviceFacade.getUserService().selectUser(userName,password);
        if (userInfo == null) {
            return noData();
        }
        userInfo.setPassword("");
        String sessionUser = JSONObject.toJSONString(userInfo);
        session.setAttribute("user",sessionUser);
        return success(userInfo);
    }
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public ResultDTO getUser(HttpServletRequest request) {
        UserInfo userInfo = UserUtil.getUser(request);
        if (userInfo == null) {
            return noData();
        }
        return success(userInfo);
    }
}
