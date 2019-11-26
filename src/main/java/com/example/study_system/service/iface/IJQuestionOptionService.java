package com.example.study_system.service.iface;

import com.example.study_system.model.JQuestionOption;

import java.util.List;

public interface IJQuestionOptionService {
    int insertSelective(JQuestionOption record);

    List<JQuestionOption> selectQuestionByQuestionId(Long questionId);

    List<JQuestionOption> selectAllQuestionOption();

    int deleteQuestionOptionByQuestionId(Long questionId);
}
