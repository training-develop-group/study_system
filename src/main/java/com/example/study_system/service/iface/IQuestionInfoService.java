package com.example.study_system.service.iface;

import com.example.study_system.dto.QuestionResultDTO;
import com.example.study_system.model.JQuestionOption;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IQuestionInfoService {
    int addQuestion(QuestionInfoWithBLOBs question, List<JQuestionOption> questionOptions);

    int deleteQuestion(Long questionId);

    int updateQuestion(QuestionInfoWithBLOBs question, List<JQuestionOption> questionOptions);

    PageInfo<QuestionResultDTO> selectQuestion(Integer pageNum, Integer pageSize, String content, Integer questionType);

    List<QuestionResultDTO> selectQuestionTitle(Long questionId);

    int selectQuestionCount(Integer questionType);

    List<QuestionInfoWithBLOBs> selectAnalysis(Long questionId);

    int updateQuestionScore(Float score, Long questionId);
}
