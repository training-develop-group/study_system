package com.example.study_system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.emun.StRoleEmun;
import com.example.study_system.model.CommentInfo;
import com.example.study_system.model.JUserTask;
import com.example.study_system.model.TaskInfo;
import com.example.study_system.model.UserInfo;
import com.example.study_system.model.UserTaskRelationInfo;
import com.example.study_system.util.UserUtil;
import com.github.pagehelper.PageInfo;

/**
 * author liubo. date 2019/10/17.
 */
@RestController
@RequestMapping("/task")
public class TaskController extends BaseController {
	String userId = "";
	String publicUserName = "";
	/**
	 * 	查询任务详情
	 * 
	 * @param request
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "{taskId}", method = RequestMethod.GET)
	public ResultDTO<TaskInfo> selectTaskDetails(HttpServletRequest request, @PathVariable("taskId") Long taskId) {
		if (taskId == null) {
			return validationError();
		}
		UserInfo userInfo = UserUtil.getUser(request);
		String remark = publicUserName + "查询任务详情";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "taskId:" + taskId;
		try {
			TaskInfo taskDetails = serviceFacade.getTaskService().taskDetails(taskId, userInfo.getUserId());
			if (taskDetails == null) {
				return noData();
			}
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(taskDetails);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	TaskInfo taskDetails = serviceFacade.getTaskService().taskDetails(taskId, userInfo.getUserId());
        	if (taskDetails == null) {
    			return noData();
    		}
        	return success(taskDetails);
        }
	}

	/**
	 *	查询任务列表
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param taskName
	 * @return
	 */

	@RequestMapping(value = "/tasks", method = RequestMethod.GET)
	public ResultDTO<PageInfo<TaskInfo>> selectTaskAll(HttpServletRequest request, @RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize, @RequestParam(value = "taskName", required = false) String taskName,
			@RequestParam(value = "status", required = false) Integer status) {
		userId = UserUtil.getUser(request).getUserId();
		publicUserName = UserUtil.getUser(request).getUserName();
		UserInfo userInfo = UserUtil.getUser(request);
		if (userInfo == null) {
			return noData();
		}
		if (userInfo.getStRoleId() != null && userInfo.getStRoleId() == StRoleEmun.USER.getStRoleId()) {
			String remark = publicUserName + "查询任务列表(用户)";
			String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
			String params = "pageNum:" + pageNum + "pageSize:" + pageSize + "userId:" + userInfo.getUserId() + "status:" + status;
			try {
				PageInfo<TaskInfo> TaskList = serviceFacade.getTaskService().selectUserTask(pageNum, pageSize, status,
						userInfo.getUserId());
				serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
				return success(TaskList);
	        } catch (Exception e){
	        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
	        	PageInfo<TaskInfo> TaskList = serviceFacade.getTaskService().selectUserTask(pageNum, pageSize, status,
						userInfo.getUserId());
	        	return success(TaskList);
	        }
		} else if (userInfo.getStRoleId() != null && userInfo.getStRoleId() == StRoleEmun.MANAGER.getStRoleId()) {
			String remark = publicUserName + "查询任务列表(管理员)";
			String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
			String params = "pageNum:" + pageNum + "pageSize:" + pageSize + "taskName:" + taskName;
			try {
				PageInfo<TaskInfo> TaskList = serviceFacade.getTaskService().selectTaskAll(pageNum, pageSize, taskName);
				if (TaskList == null) {
					return noData();
				}
				serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
				return success(TaskList);
	        } catch (Exception e){
	        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
	        	PageInfo<TaskInfo> TaskList = serviceFacade.getTaskService().selectTaskAll(pageNum, pageSize, taskName);
				if (TaskList == null) {
					return noData();
				}
	        	return success(TaskList);
	        }
		} else {
			return noData();
		}
	}

	/**
	 * 	查询任务总条数
	 *
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResultDTO<Integer> selectTaskCount(HttpServletRequest request) {
		String remark = publicUserName + "查询任务总条数";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "";
		try {
			int taskCount = serviceFacade.getTaskService().selectTaskCount();
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(taskCount);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	int taskCount = serviceFacade.getTaskService().selectTaskCount();
        	return success(taskCount);
        }
	}

	/**
	 * 	查询任务类型
	 *
	 * @return
	 */
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	public ResultDTO<List<String>> selectTaskType(HttpServletRequest request) {
		String remark = publicUserName + "查询任务类型";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "";
		try {
			List<String> taskType = serviceFacade.getTaskService().taskTypeEnum();
			if (taskType == null) {
				return noData();
			}
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(taskType);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	List<String> taskType = serviceFacade.getTaskService().taskTypeEnum();
    		if (taskType == null) {
    			return noData();
    		}
        	return success(taskType);
        }
	}

	/**
	 * 	查询所有任务对象
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResultDTO<List<UserInfo>> selectAllUser(HttpServletRequest request,@RequestParam("userName") String userName) {
		String remark = publicUserName + "查询所有任务对象";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "userName:" + userName;
		try {
			List<UserInfo> allUser = serviceFacade.getLoginService().selectUserByName(userName);
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(allUser);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	List<UserInfo> allUser = serviceFacade.getLoginService().selectUserByName(userName);
        	return success(allUser);
        }
	}

	/**
	 * 查询任务完成度
	 * @param request
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/user-ok", method = RequestMethod.GET)
	public ResultDTO<List<JUserTask>> selectTaskUsers(HttpServletRequest request,@RequestParam("taskId") Long taskId) {
		if (taskId == null) {
			return validationError();
		}
		String remark = publicUserName + "查询任务完成度";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "taskId:" + taskId;
		try {
			List<JUserTask> users = serviceFacade.getTaskService().selectTaskUsers(taskId);
			if (users == null) {
				return noData();
			}
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(users);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	List<JUserTask> users = serviceFacade.getTaskService().selectTaskUsers(taskId);
    		if (users == null) {
    			return noData();
    		}
        	return success(users);
        }
	}

	/**
	 * 	点击删除用Id删除
	 * 
	 * @param request
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
	public ResultDTO<Integer> deleteTask(HttpServletRequest request, @PathVariable("taskId") Long taskId) {
		if (taskId == null) {
			return validationError();
		} else {
			UserInfo userInfo = UserUtil.getUser(request);
			String remark = publicUserName + "删除任务";
			String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
			String params = "taskId:" + taskId;
			try {
				int deleteTask = serviceFacade.getTaskService().deleteTaskById(taskId, userInfo.getUserId());
				serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
				return success(deleteTask);
	        } catch (Exception e){
	        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
	        	int deleteTask = serviceFacade.getTaskService().deleteTaskById(taskId, userInfo.getUserId());
	        	return success(deleteTask);
	        }
		}
	}

	/**
	 * 根据Id修改任务名
	 * 
	 * @param taskId
	 * @param taskName
	 * @return
	 */
	@RequestMapping(value = "/task/{taskId}", method = RequestMethod.POST)
	public ResultDTO<Integer> deleteTask(HttpServletRequest request,
			@PathVariable("taskId") Long taskId,
			@RequestParam("taskName") String taskName) {
		if (taskId == null || StringUtils.isEmpty(taskName)) {
			return validationError();
		}
		String remark = publicUserName + "修改任务名";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "taskId:" + taskId + "taskName:" + taskName;
		try {
			int updateTaskName = serviceFacade.getTaskService().updateTaskById(taskId, taskName);
			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
			return success(updateTaskName);
        } catch (Exception e){
        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
        	int updateTaskName = serviceFacade.getTaskService().updateTaskById(taskId, taskName);
        	return success(updateTaskName);
        }
	}

	/**
	 *	添加任务
	 * 
	 * @param request
	 * @param taskInfo
	 * @return
	 */
	@RequestMapping(value = "/tasks", method = RequestMethod.POST)
	public ResultDTO<Integer> insertTask(HttpServletRequest request, @RequestBody UserTaskRelationInfo taskInfo) {
		if (taskInfo == null) {
			return validationError();
		}
		if (taskInfo.getUserId() != null) {
			UserInfo userInfo = UserUtil.getUser(request);
			taskInfo.setcUser(userInfo.getUserName());
			String remark = publicUserName + "添加任务";
			String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
			String params = "UserTaskRelationInfo:" + taskInfo;
			try {
				Integer result = serviceFacade.getTaskService().insertTask(taskInfo);
				serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
				return success(result);
	        } catch (Exception e){
	        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
	        	Integer result = serviceFacade.getTaskService().insertTask(taskInfo);
	        	return success(result);
	        }
		} else {
			return noData();
		}
	}
	/**
	 *	评论
	 * 
	 * @param taskId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/comments", method = RequestMethod.GET)
	public ResultDTO getComments(HttpServletRequest request,
			@RequestParam("taskId") Long taskId, 
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) {
		if (taskId == null) {
			return validationError();
		} else {
			String remark = publicUserName + "查询评论列表";
			String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
			String params = "taskId:" + taskId + "pageNum:" + pageNum + "pageSize:" + pageSize;
			try {
				PageInfo<CommentInfo> conmment = serviceFacade.getCommentInfoService().selectCommentByTaskId(taskId,
						pageNum, pageSize);
				if (conmment == null) {
					return noData();
				}
				serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
				return success(conmment);
	        } catch (Exception e){
	        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
	        	PageInfo<CommentInfo> conmment = serviceFacade.getCommentInfoService().selectCommentByTaskId(taskId,
						pageNum, pageSize);
				if (conmment == null) {
					return noData();
				}
	        	return success(conmment);
	        }
		}
	}

	/**
	 * 	添加心得
	 * 
	 * @param request
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public ResultDTO addComments(HttpServletRequest request, @RequestBody CommentInfo record) {
		if (record == null) {
			return validationError();
		}
		UserInfo userInfo = UserUtil.getUser(request);
		if (userInfo == null) {
			return noData();
		}
		record.setCommentUserId(userInfo.getUserId());
		if (record.getTaskId() == null || record.getCommentUserId() == null) {
			return validationError();
		} else {
			String remark = publicUserName + "添加心得";
			String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
			String params = "CommentInfo:" + record;
			try {
				int result = serviceFacade.getCommentInfoService().insertSelective(request, record);
				serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
				return success(result);
	        } catch (Exception e){
	        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
	        	int result = serviceFacade.getCommentInfoService().insertSelective(request, record);
	        	return success(result);
	        }
		}
	}

	/**
	 *	 修改心得
	 * 
	 * @param ref
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/comment/{commentId}", method = RequestMethod.POST)
	public ResultDTO updataComments(HttpServletRequest request,@PathVariable("commentId") Long ref, @RequestBody CommentInfo record) {
		if (ref == null || record.getTaskId() == null || record.getCommentUserId() == null) {
			return validationError();
		} else {
			record.setRef(ref);
			String remark = publicUserName + "修改心得";
			String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
			String params = "commentId:" + ref + "CommentInfo:" + record;
			try {
				int result = serviceFacade.getCommentInfoService().updateByPrimaryKeySelective(record);
				serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
				return success(result);
	        } catch (Exception e){
	        	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
	        	int result = serviceFacade.getCommentInfoService().updateByPrimaryKeySelective(record);
	        	return success(result);
	        }
		}
	}
}