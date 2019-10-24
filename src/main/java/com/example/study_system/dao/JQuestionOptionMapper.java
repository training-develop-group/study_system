package com.example.study_system.dao;

import com.example.study_system.model.JQuestionOption;

public interface JQuestionOptionMapper {
    int deleteByPrimaryKey(Long ref);

    int insert(JQuestionOption record);

    int insertSelective(JQuestionOption record);

    JQuestionOption selectByPrimaryKey(Long ref);

    int updateByPrimaryKeySelective(JQuestionOption record);

    int updateByPrimaryKeyWithBLOBs(JQuestionOption record);

    int updateByPrimaryKey(JQuestionOption record);
}