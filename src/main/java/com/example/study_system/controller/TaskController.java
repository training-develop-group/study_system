package com.example.study_system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.example.study_system.model.JUserTaskInfo;
import com.example.study_system.model.TaskInfo;
import com.example.study_system.model.UserInfo;
import com.example.study_system.model.UserTaskRelationInfo;
import com.example.study_system.util.UserUtil;
import com.github.pagehelper.PageInfo;

/**
 * author liubo.
 * date 2019/10/17.
 */
@RestController
@RequestMapping("/task")
public class TaskController extends BaseController {
    //查询from
    @RequestMapping(value = "{taskId}", method = RequestMethod.GET)
    public ResultDTO<TaskInfo> selectTaskDetails(@PathVariable("taskId") Long taskId) {
        TaskInfo taskDetails = serviceFacade.getTaskService().taskDetails(taskId);
        return success(taskDetails);

    }

    /**
     * 查询全部
     *
     * @param pageNum
     * @param pageSize
     * @param taskName
     * @return
     */

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ResultDTO<PageInfo<TaskInfo>> selectTaskAll(HttpServletRequest request,
                                                       @RequestParam("pageNum") int pageNum,
                                                       @RequestParam("pageSize") int pageSize,
                                                       @RequestParam(value = "taskName", required = false) String taskName,
                                                       @RequestParam(value = "status", required = false) Integer status
    ) {
        UserInfo userInfo = UserUtil.getUser(request);

        if (userInfo.getStRoleId() != null && userInfo.getStRoleId() == StRoleEmun.USER.getStRoleId()) {
            PageInfo<TaskInfo> TaskList = serviceFacade.getTaskService().selectUserTask(pageNum, pageSize, status, userInfo.getUserId());
            System.out.println(TaskList);
            return success(TaskList);
        } else if (userInfo.getStRoleId() != null && userInfo.getStRoleId() == StRoleEmun.MANAGER.getStRoleId()) {

            System.err.println(userInfo);

            PageInfo<TaskInfo> TaskList = serviceFacade.getTaskService().selectTaskAll(pageNum, pageSize, taskName);
            return success(TaskList);
        } else {
            return noData();
        }


    }

    /**
     * 查询任务总条数
     *
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResultDTO<Integer> selectTaskCount() {
        int taskCount = serviceFacade.getTaskService().selectTaskCount();
        return success(taskCount);
    }

    /**
     * 查询任务类型
     *
     * @return
     */
    @RequestMapping(value = "/type", method = RequestMethod.GET)
    public ResultDTO<List<String>> selectTaskType() {
        List<String> taskType = serviceFacade.getTaskService().taskTypeEnum();
        return success(taskType);
    }

    //查询所有任务对象
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResultDTO<List<UserInfo>> selectAllUser(@RequestParam("userName") String userName) {
        List<UserInfo> allUser = serviceFacade.getLoginService().selectUserByName(userName);
        return success(allUser);
    }

    //查询任务完成度
    @RequestMapping(value = "/user-ok", method = RequestMethod.GET)
    public ResultDTO<List<JUserTask>> selectTaskUsers(@RequestParam("taskId") Long taskId) {
        List<JUserTask> users = serviceFacade.getTaskService().selectTaskUsers(taskId);
        return success(users);
    }

    //点击删除用Id删除
    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    public ResultDTO<Integer> deleteTask(@PathVariable("taskId") Long taskId) {
        int deleteTask = serviceFacade.getTaskService().deleteTaskById(taskId);
        return success(deleteTask);
    }

    //根据Id修改任务名
    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.POST)
    public ResultDTO<Integer> deleteTask(@PathVariable("taskId") Long taskId, @RequestParam("taskName") String taskName) {

        int updateTaskName = serviceFacade.getTaskService().updateTaskById(taskId, taskName);
        return success(updateTaskName);
    }

    //添加任务 (待完善)
    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public ResultDTO<Integer> insertTask(HttpServletRequest request, @RequestBody UserTaskRelationInfo taskInfo) {
        if (taskInfo.getUserId() != null) {
            UserInfo userInfo = UserUtil.getUser(request);
            taskInfo.setcUser(userInfo.getUserName());
            Integer result = serviceFacade.getTaskService().insertTask(taskInfo);
            return success(result);
        } else {
            return noData();
        }
    }


    //评论
    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public ResultDTO getComments(@RequestParam("taskId") Long taskId, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        if (taskId == null) {
            return validationError();
        } else {
            PageInfo<CommentInfo> conmment = serviceFacade.getCommentInfoService().selectCommentByTaskId(taskId, pageNum, pageSize);
            return success(conmment);
        }
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResultDTO addComments(HttpServletRequest request, @RequestBody CommentInfo record) {
        UserInfo userInfo = UserUtil.getUser(request);
        record.setCommentUserId(userInfo.getUserId());
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