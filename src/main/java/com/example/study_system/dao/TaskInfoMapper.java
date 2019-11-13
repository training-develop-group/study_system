package com.example.study_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.study_system.model.JUserTaskInfo;
import com.example.study_system.model.TaskInfo;
import com.example.study_system.model.UserTaskRelationInfo;
@Mapper
public interface TaskInfoMapper {
    int deleteByPrimaryKey(Long taskId);

    Long insert(TaskInfo record);

    int insertSelective(TaskInfo record);

    TaskInfo selectByPrimaryKey(Long taskId);

    int updateByPrimaryKeySelective(TaskInfo record);

    int updateByPrimaryKeyWithBLOBs(TaskInfo record);

    int updateByPrimaryKey(TaskInfo record);
    
    List<TaskInfo> selectTaskAll(@Param("taskName")String taskName);
   
    int updateByIdTaskName(TaskInfo record);
    
    int selectTaskCount();
	List<TaskInfo> selectUserTask (@Param("stutas")Integer stutas,@Param("userId")String userId);
    List<TaskInfo> selectAll();
//    TaskInfo taskDetails(Long taskId);
    
  //获取任务类型百分比
  	TaskInfo typeTaskInfo();
  	//获取统计列表
  	TaskInfo selectTaskInfo(String cUser);
}