package com.example.study_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    
//    TaskInfo taskDetails(Long taskId);
}