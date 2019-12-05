package com.example.study_system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.dto.UserTaskDTO;
import com.example.study_system.model.JUserQuesAnswerRecord;
import com.example.study_system.model.JUserTask;
import com.example.study_system.model.TaskInfo;
import com.example.study_system.util.UserUtil;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/stat")
public class StatController extends BaseController {
    /**
     *	 获取任务类型百分比
     * @return
     */
    @RequestMapping(value = "/task-type", method = RequestMethod.GET)
    public ResultDTO selectTaskTypePercentage(HttpServletRequest request) {
    	String userId = UserUtil.getUser(request).getUserId();
    	String publicUserName = UserUtil.getUser(request).getUserName();
    	String remark = publicUserName + "获取任务类型百分比";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "";
		try {
			TaskInfo res = serviceFacade.getStatService().selectStat();
			if (res == null) {
				return noData();
		    }
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(res);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	TaskInfo res = serviceFacade.getStatService().selectStat();
        	if (res == null) {
				return noData();
		    }
			return success(res);
        }
    }

    /**
     * 	获取学生任务完成百分比
     * @return
     */
    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public ResultDTO select(HttpServletRequest request) {
    	String userId = UserUtil.getUser(request).getUserId();
    	String publicUserName = UserUtil.getUser(request).getUserName();
    	String remark = publicUserName + "获取学生任务完成百分比";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "";
		try {
			JUserTask res = serviceFacade.getStatService().selectJUsePaperPercentage();
			if (res == null) {
				return noData();
		    }
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(res);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	JUserTask res = serviceFacade.getStatService().selectJUsePaperPercentage();
        	if (res == null) {
				return noData();
		    }
			return success(res);
        }
    }

    /**
     * 	获取学生作答正确率
     * @return
     */
    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ResultDTO selectJUserQuesAnswerRecord(HttpServletRequest request) {
    	String userId = UserUtil.getUser(request).getUserId();
    	String publicUserName = UserUtil.getUser(request).getUserName();
    	String remark = publicUserName + "获取学生作答正确率";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "";
		try {
			JUserQuesAnswerRecord res = serviceFacade.getStatService().choiceJUserQuesAnswerRecord();
			if (res == null) {
				return noData();
		    }
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(res);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	JUserQuesAnswerRecord res = serviceFacade.getStatService().choiceJUserQuesAnswerRecord();
        	if (res == null) {
				return noData();
		    }
			return success(res);
        }
    }

    /**
     * 	获取统计列表
     * @param userName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDTO selectTaskInfo(HttpServletRequest request,
    								@RequestParam(value = "userName") String userName,
                                    @RequestParam(value = "pageNum") Integer pageNum,
                                    @RequestParam(value = "pageSize") Integer pageSize) {
    	String userId = UserUtil.getUser(request).getUserId();
    	String publicUserName = UserUtil.getUser(request).getUserName();
    	String remark = publicUserName + "获取统计列表";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "userName:" + userName + "pageNum:" + pageNum + "pageSize:" + pageSize;
		try {
			PageInfo<UserTaskDTO> res = serviceFacade.getStatService().statisticalList(userName, pageNum, pageSize);
			if (res == null) {
				return noData();
		    }
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(res);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	PageInfo<UserTaskDTO> res = serviceFacade.getStatService().statisticalList(userName, pageNum, pageSize);
        	if (res == null) {
				return noData();
		    }
			return success(res);
        }
    }
}
