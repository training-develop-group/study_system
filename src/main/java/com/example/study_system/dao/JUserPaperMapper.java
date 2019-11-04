package com.example.study_system.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.study_system.model.JUserPaper;

@Mapper
public interface JUserPaperMapper {
	int deleteByPrimaryKey(Long ref);

	int insert(JUserPaper record);

	int insertSelective(JUserPaper record);

	JUserPaper selectByPrimaryKey(Long ref);

	int updateByPrimaryKeySelective(JUserPaper record);

	int updateByPrimaryKey(JUserPaper record);

	int updateScore(Float score, String userId, Long paperId);
}