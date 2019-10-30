<<<<<<< HEAD
package com.example.study_system.dao;

import com.example.study_system.model.QuestionInfo;
import com.example.study_system.model.QuestionInfoWithBLOBs;

public interface QuestionInfoMapper {
    int deleteByPrimaryKey(Long questionId);

    int insert(QuestionInfoWithBLOBs record);

    int insertSelective(QuestionInfoWithBLOBs record);

    QuestionInfoWithBLOBs selectByPrimaryKey(Long questionId);

    int updateByPrimaryKeySelective(QuestionInfoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(QuestionInfoWithBLOBs record);

    int updateByPrimaryKey(QuestionInfo record);
=======
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

	List<QuestionInfoWithBLOBs> selectAllQuestion(@Param("content")String content);

	int selectQuestionCount(@Param("questionType")Integer questionType);

	QuestionInfoWithBLOBs selectAnalysisById(@Param("questionId")Long questionId);
>>>>>>> remotes/origin/dev-wtq
}