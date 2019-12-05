package com.example.study_system.dao;

import com.example.study_system.model.TaskInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskInfoMapper {
    int deleteByPrimaryKey(Long taskId);

    Long insert(TaskInfo record);

    int insertSelective(TaskInfo record);

    TaskInfo selectByPrimaryKey(Long taskId,@Param("userId")String userId);

    int updateByPrimaryKeySelective(TaskInfo record);

    int updateByPrimaryKeyWithBLOBs(TaskInfo record);

    int updateByPrimaryKey(TaskInfo record);

    List<TaskInfo> selectTaskAll(@Param("taskName") String taskName);

    int updateByIdTaskName(TaskInfo record);

    int selectTaskCount();

    List<TaskInfo> selectUserTask(@Param("stutas") Integer stutas, @Param("userId") String userId);

    List<TaskInfo> selectAll();
    
    List<TaskInfo> selectPaperAndRes(@Param("paperId")Long paperId,@Param("resId")Long resId);

    //获取任务类型百分比
    TaskInfo typeTaskInfo();

    //获取统计列表
    TaskInfo selectTaskInfo(String userId);
}