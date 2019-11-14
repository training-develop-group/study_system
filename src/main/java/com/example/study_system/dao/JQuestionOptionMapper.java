package com.example.study_system.dao;

import com.example.study_system.model.JQuestionOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JQuestionOptionMapper {
    int deleteByPrimaryKey(Long ref);

    int insert(JQuestionOption record);

    int insertSelective(JQuestionOption record);

    JQuestionOption selectByPrimaryKey(Long ref);

    int updateByPrimaryKeySelective(JQuestionOption record);

    int updateByPrimaryKeyWithBLOBs(JQuestionOption record);

    int updateByPrimaryKey(JQuestionOption record);

    //	试卷详情(用)
    List<JQuestionOption> selectQuestionByQuestionIdList(@Param("ids") List<Long> questionIdList);

    List<JQuestionOption> selectQuestionByQuestionId(Long questionId);

    List<JQuestionOption> selectAllQuestionOption();

    int deleteQuestionOptionByQuestionId(Long questionId);
}