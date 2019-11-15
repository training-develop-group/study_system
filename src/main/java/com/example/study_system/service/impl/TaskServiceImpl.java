package com.example.study_system.service.impl;

import com.example.study_system.emun.TaskTypeEnum;
import com.example.study_system.model.*;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.ITaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl extends BaseService implements ITaskService {

    /**
     * 查询详情
     *
     * @param taskId
     * @return
     */
    @Override
    public TaskInfo taskDetails(Long taskId) {
        TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskId);
        return taskInfo;
    }

    /**
     * 查询任务全部
     *
     * @param pageNum
     * @param pageSize
     * @param taskName
     * @return
     */
    @Override
    public PageInfo<TaskInfo> selectTaskAll(Integer pageNum, Integer pageSize, String taskName) {
        PageHelper.startPage(pageNum, pageSize);
        List<TaskInfo> TaskList = taskInfoMapper.selectTaskAll(taskName);
        PageInfo<TaskInfo> result = new PageInfo<>(TaskList);
        return result;
    }

    @Override
    public PageInfo<TaskInfo> selectUserTask(Integer pageNum, Integer pageSize, Integer status, String userId) {
        PageHelper.startPage(pageNum, pageSize);
        List<TaskInfo> TaskList = taskInfoMapper.selectUserTask(status, userId);
        PageInfo<TaskInfo> result = new PageInfo<>(TaskList);
        return result;
    }

    @Override
    public int selectTaskCount() {
        return taskInfoMapper.selectTaskCount();
    }

    /**
     * 获取任务类型
     *
     * @return
     */
    public List<String> taskTypeEnum() {
        List<String> taskType = new ArrayList<String>();
        taskType.add(TaskTypeEnum.COMPREHENSIVE_TASK.getPath());
        taskType.add(TaskTypeEnum.LEARNING_TASK.getPath());
        taskType.add(TaskTypeEnum.TEST_TASK.getPath());

        return taskType;
    }

    public List<UserInfo> selectAllUser(String userName) {
        return userInfoMapper.selectUserByName(userName);
    }

    /**
     * 查询查看任务对像
     *
     * @param taskId
     * @return
     */
    public List<JUserTask> selectTaskUsers(Long taskId) {
        List<JUserTask> TaskUserList = jUserTaskMapper.selectByTaskIdusers(taskId);
        for (JUserTask TaskUser : TaskUserList) {
            if (TaskUser.getStatus() == 1) {
                JUserPaper userPaper = jUserPaperMapper.selectByUserIdAndTaskId("1", (long) 3);
                if (userPaper == null) {
                    userPaper = new JUserPaper();
                    userPaper.setScore((float) 0.0);
                }
                TaskUser.setScore(userPaper.getScore());
            }
        }
        return TaskUserList;
    }

    /**
     * 添加任务
     *
     * @param taskInfo
     * @return
     */
    public int insertTask(UserTaskRelationInfo taskInfo) {
        Long insertTask = taskInfoMapper.insert(taskInfo);
        String[] userId = taskInfo.getUserId().split(",");

        for (int u = 1; u < userId.length; u++) {
            if (userId[u] != null || userId[u] != "") {
                JUserTask record = new JUserTask();
                record.setTaskId(taskInfo.getTaskId());
                record.setUserId(userId[u]);

                jUserTaskMapper.insert(record);

            }
            taskInfo.getResId();
            taskInfo.getPaperId();
        }
        if (taskInfo.getPaperId() != null) {
            paperInfoMapper.updateStatus(taskInfo.getPaperId(),1);
        }
        if (taskInfo.getResId() != null) {
            resourceInfoMapper.updateStatus(taskInfo.getResId(),1);
        }
        return 1;
    }

    /**
     * 删除任务根据任务id
     *
     * @param taskId
     * @return
     */
    @Override
    public int deleteTaskById(long taskId) {
    	 TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskId);
    	  resourceInfoMapper.updateStatus(taskInfo.getResId(),0);
    	paperInfoMapper.updateStatus(taskInfo.getPaperId(),0);
        return taskInfoMapper.deleteByPrimaryKey(taskId);
    }

    /**
     * 根据id修改任务名
     *
     * @param taskId
     * @param taskName
     * @return
     */
    @Override
    public int updateTaskById(long taskId, String taskName) {
        TaskInfo record = new TaskInfo();
        record.setTaskId(taskId);
        record.setTaskName(taskName);
        return taskInfoMapper.updateByIdTaskName(record);
    }
}
