package com.example.study_system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.model.CommentInfo;
import com.example.study_system.model.JUserTaskInfo;
import com.example.study_system.model.TaskInfo;
import com.example.study_system.model.UserInfo;
import com.example.study_system.model.UserTaskRelationInfo;
import com.github.pagehelper.PageInfo;
/**
 * author liubo.
 * date 2019/10/17.
 */
@RestController
@RequestMapping("/task")
public class TaskController extends BaseController { 
	//查询from
	@RequestMapping(value = "{taskId}" , method = RequestMethod.GET)
	public ResultDTO<TaskInfo> selectTaskDetails(@PathVariable("taskId")Long taskId){
		TaskInfo taskDetails = serviceFacade.getTaskService().taskDetails(taskId);
		return success(taskDetails);
				
	}
	/**
	    * 查询全部
	 * @param pageNum
	 * @param pageSize
	 * @param taskName
	 * @return
	 */

		@RequestMapping(value = "/tasks", method = RequestMethod.GET)
		public ResultDTO<PageInfo<TaskInfo>> selectTaskAll(@RequestParam("pageNum")int pageNum,@RequestParam("pageSize")int pageSize,@RequestParam("taskName")String taskName){
			PageInfo<TaskInfo> TaskList = serviceFacade.getTaskService().selectTaskAll(pageNum, pageSize,taskName);
			return success(TaskList);
		}
	//查询任务总条数
		@RequestMapping(value = "/count", method = RequestMethod.GET)
		public int selectTaskCount() {
			int taskCount =  serviceFacade.getTaskService().selectTaskCount();
			return taskCount;
		}
	//查询任务类型
		@RequestMapping(value = "/type", method = RequestMethod.GET)
		public List<String> selectTaskType() {
			List<String> taskCount =  serviceFacade.getTaskService().taskTypeEnum();
			return taskCount;
		}
	//查询所有任务对象 
		@RequestMapping(value = "/users", method = RequestMethod.GET)
		public List<UserInfo> selectAllUser(@RequestParam("userName")String userName){
			List<UserInfo> allUser = serviceFacade.getUseService().selectUserAll(userName);
			return allUser;
		}
	//查询任务完成度
		@RequestMapping(value = "/user-ok", method = RequestMethod.GET)
		public List<JUserTaskInfo> selectTaskUsers(@RequestParam("taskId")Long taskId){
			List<JUserTaskInfo> users = serviceFacade.getTaskService().selectTaskUsers(taskId);
			return users;
		}
	//查询end
		
		
	//删除from
	//点击删除用Id删除
		@RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
		public int deleteTask(@PathVariable("taskId")Long taskId) {
			int deleteTask = serviceFacade.getTaskService().deleteTaskById(taskId);
			return deleteTask;
		}
	//删除end
		
		
	//修改from
	//根据Id修改任务名
		@RequestMapping(value = "/task/{taskId}", method = RequestMethod.POST)
		public ResultDTO<Integer> deleteTask( @PathVariable("taskId") Long taskId , @RequestParam("taskName") String taskName) {
			int updateTaskName = serviceFacade.getTaskService().updateTaskById(taskId, taskName);
			return success(updateTaskName);
		}
	//修改end
	//添加from
	//添加任务 (待完善)
		@RequestMapping(value = "/tasks" , method = RequestMethod.POST)
		public ResultDTO<Integer> insertTask(@RequestBody UserTaskRelationInfo taskInfo) {
			if (taskInfo.getUserId()!=null) {
//				System.out.println("aasdasd");
				Integer result=serviceFacade.getTaskService().insertTask(taskInfo);
				return success(result);
			}else {
				return noData();
			}
			
		}



	// ------------------------------------评论↓
	@RequestMapping(value = "/comments", method = RequestMethod.GET)
	public ResultDTO getComments(@RequestParam("taskId") Long taskId) {
		if (taskId == null) {
			return validationError();
		} else {
			System.out.println(serviceFacade.getCommentInfoService().selectCommentByTaskId(taskId).get(0).getCommentType());
			return success(serviceFacade.getCommentInfoService().selectCommentByTaskId(taskId));
		}
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public ResultDTO addComments(@RequestBody CommentInfo record) {
		if (record.getTaskId() == null || record.getCommentUserId() == null) {
			return validationError();
		} else {
			return success(serviceFacade.getCommentInfoService().insertSelective(record));
		}
	}

	@RequestMapping(value = "/comment/{commentId}", method = RequestMethod.POST)
	public ResultDTO updataComments(@PathVariable("commentId") Long ref, @RequestBody CommentInfo record) {
		if (ref == null || record.getTaskId() == null || record.getCommentUserId() == null) {
			return validationError();
		} else {
			record.setRef(ref);
			return success(serviceFacade.getCommentInfoService().updateByPrimaryKeySelective(record));
		}
	}
}
