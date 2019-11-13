package com.example.study_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.study_system.model.JUserTask;

@Mapper
public interface JUserTaskMapper {
	int deleteByPrimaryKey(Long ref);

	int insert(JUserTask record);

	int insertSelective(JUserTask record);

	JUserTask selectByPrimaryKey(Long ref);

	int updateByPrimaryKeySelective(JUserTask record);

	int updateByPrimaryKey(JUserTask record);

	int updateStatus(String userId, Long taskId);
	
	JUserTask selectByTaskIdAndUserId(@Param("userId") String userId,@Param("taskId")Long taskId);
	
	List<JUserTask> selectByTaskIdusers(@Param("taskId") Long taskId);
	
	JUserTask selectUserTaskProbability();

	float selectJUsePaperPercentage(String userId);

	JUserTask selectJUsePaperPercentage();
	
	float selectJUsePaper(String userId);
}