package com.example.study_system.dao;

import com.example.study_system.model.QuestionInfo;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionInfoMapper {
    int deleteByPrimaryKey(Long questionId);

    int insert(QuestionInfoWithBLOBs record);

    int insertSelective(QuestionInfoWithBLOBs record);

    QuestionInfoWithBLOBs selectByPrimaryKey(Long questionId);

    int updateByPrimaryKeySelective(QuestionInfoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(QuestionInfoWithBLOBs record);

    //	试卷详情(用)
    List<QuestionInfoWithBLOBs> selectByQuestionIds(@Param("ids") List<Long> questionIdList);

    int updateByPrimaryKey(QuestionInfo record);

    List<QuestionInfoWithBLOBs> selectAllQuestion(@Param("content") String content,
                                                  @Param("questionType") Integer type);

    int selectQuestionCountNum(@Param("content") String content, @Param("questionType") Integer questionType);

    int selectQuestionCount(@Param("questionType") Integer questionType);

    List<QuestionInfoWithBLOBs> selectAnalysisById(@Param("questionId") Long questionId);

    int updateQuestionScore(@Param("score") Float score, @Param("questionId") Long questionId);

    int updateQuestionStatus(@Param("questionId") Long questionId);
}