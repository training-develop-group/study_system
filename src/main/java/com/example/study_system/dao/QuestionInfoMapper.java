package com.example.study_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.study_system.model.QuestionInfo;
import com.example.study_system.model.QuestionInfoWithBLOBs;

@Mapper
public interface QuestionInfoMapper {
	int deleteByPrimaryKey(Long questionId);

	int insert(QuestionInfoWithBLOBs record);

	int insertSelective(QuestionInfoWithBLOBs record);

	QuestionInfoWithBLOBs selectByPrimaryKey(Long questionId);

	int updateByPrimaryKeySelective(QuestionInfoWithBLOBs record);

	int updateByPrimaryKeyWithBLOBs(QuestionInfoWithBLOBs record);

	int updateByPrimaryKey(QuestionInfo record);

	List<QuestionInfoWithBLOBs> selectAllQuestion(@Param("content") String content,
			@Param("questionType") Integer type);

	int selectQuestionCount(@Param("questionType") Integer questionType);

	List<QuestionInfoWithBLOBs> selectAnalysisById(@Param("questionId") Long questionId);

	int updateQuestionScore(@Param("score") Float score, @Param("questionId") Long questionId);
}