package com.example.study_system.dao;

import org.apache.ibatis.annotations.Mapper;

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
}