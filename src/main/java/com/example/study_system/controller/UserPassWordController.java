package com.example.study_system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.model.TaskInfo;
import com.example.study_system.model.UserInfo;
import com.example.study_system.util.UserUtil;

/**
 * 用户
 * 
 * @author liubo
 * 
 */
@RestController
@RequestMapping("/user")
public class UserPassWordController extends BaseController {
	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param originalPassword
	 * @param newPassWord
	 * @param passwordValidation
	 * @return
	 */
	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public ResultDTO<String> userPassWord(HttpServletRequest request,
			@RequestParam("originalPassword") String originalPassword, @RequestParam("newPassWord") String newPassWord,
			@RequestParam("passwordValidation") String passwordValidation) {
		UserInfo userInfo = UserUtil.getUser(request);
		String userId = userInfo.getUserId();
		String remark = userInfo.getUserName() + "修改密码";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "originalPassword:" + originalPassword + "newPassWord:" + newPassWord + "passwordValidation:"
				+ passwordValidation;
		if (userInfo.getPassword().equals(originalPassword)) {
			try {
				String password = serviceFacade.getUserService().updateUserPassWord(originalPassword, newPassWord,
						passwordValidation, userInfo);
				serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
				return success(password);
			} catch (Exception e) {
				serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
				String password = serviceFacade.getUserService().updateUserPassWord(originalPassword, newPassWord,
						passwordValidation, userInfo);
				return success(password);
			}
		} else if (originalPassword.isEmpty() || newPassWord.isEmpty() || passwordValidation.isEmpty()) {
			return noData();
		} else {
			return success("原密码不正确");
		}
	}
}
