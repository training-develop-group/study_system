package com.example.study_system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.study_system.dao.JUserTaskInfoMapper;
import com.example.study_system.emun.TaskEnum;
import com.example.study_system.model.JUserTask;
import com.example.study_system.model.JUserTaskInfo;
import com.example.study_system.model.TaskInfo;
import com.example.study_system.model.UserInfo;
import com.example.study_system.model.UserTaskRelationInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.ITaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TaskServiceImpl extends BaseService implements ITaskService {

	// 查询from
	@Override
	public TaskInfo taskDetails(Long taskId) {
		TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskId);
		return taskInfo;
	}
	// 查询任务全部
	@Override
	public PageInfo<TaskInfo> selectTaskAll(Integer pageNum, Integer pageSize,String taskName) {
		PageHelper.startPage(pageNum, pageSize);
		List<TaskInfo> TaskList = taskInfoMapper.selectTaskAll(taskName);
		PageInfo<TaskInfo> result = new PageInfo<>(TaskList);
		return result;
	}
	@Override
	public PageInfo<TaskInfo> selectUserTask(Integer pageNum, Integer pageSize,Integer status,String userId){
		PageHelper.startPage(pageNum, pageSize);
		List<TaskInfo> TaskList = taskInfoMapper.selectUserTask(status, userId);
		PageInfo<TaskInfo> result = new PageInfo<>(TaskList);
		return result;
	}
	@Override
	public int selectTaskCount() {
		return taskInfoMapper.selectTaskCount();
	}

	// 获取任务类型
	public List<String> taskTypeEnum() {
		List<String> taskType = new ArrayList<String>();
		taskType.add(TaskEnum.COMPREHENSIVE_TASK.getPath());
		taskType.add(TaskEnum.LEARNING_TASK.getPath());
		taskType.add(TaskEnum.TEST_TASK.getPath());

		return taskType;
	}

	public List<UserInfo> selectAllUser(String userName) {
		return userInfoMapper.selectUserAll(userName);
	}

	// 查询查看任务对像
	public List<JUserTaskInfo> selectTaskUsers(Long taskId) {
		return jUserTaskInfoMapper.selectByTaskIdusers(taskId);
	}
	// 查询end

	// 添加任务
	public int insertTask(UserTaskRelationInfo taskInfo) {
		Long insertTask = taskInfoMapper.insert(taskInfo);
//		System.out.println(insertTask);
		String[] userId = taskInfo.getUserId().split(",");

		for (int u = 1; u < userId.length; u++) {
			if (userId[u] != null||userId[u] !="") {
				JUserTask record = new JUserTask();
				record.setTaskId(taskInfo.getTaskId());
				record.setUserId(userId[u]);

				jUserTaskMapper.insert(record);

			}
			taskInfo.getResId();
			taskInfo.getPaperId();
		}
		if(taskInfo.getPaperId() != null) {
			paperInfoMapper.updateStatus(taskInfo.getPaperId());
		}
		if(taskInfo.getResId() != null) {
			resourceInfoMapper.updateStatus(taskInfo.getResId());
		}
		return 1;
	}

	// 删除from
	// 删除任务根据任务id
	@Override
	public int deleteTaskById(long taskId) {
		return taskInfoMapper.deleteByPrimaryKey(taskId);
	}
	// 删除end

	// 修改from
	// 根据id修改任务名
	@Override
	public int updateTaskById(long taskId, String taskName) {
		TaskInfo record = new TaskInfo();
		record.setTaskId(taskId);
		record.setTaskName(taskName);
		return taskInfoMapper.updateByIdTaskName(record);
	}
	// 修改end
}
