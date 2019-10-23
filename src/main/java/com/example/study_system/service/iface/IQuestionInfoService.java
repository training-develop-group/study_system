package com.example.study_system.service.iface;

import java.util.ArrayList;
import java.util.List;

import com.example.study_system.dto.QuestionResultDTO;
import com.example.study_system.model.JQuestionOption;
import com.example.study_system.model.QuestionInfo;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.github.pagehelper.PageInfo;

public interface IQuestionInfoService {
	int addQuestion(QuestionInfoWithBLOBs question,List<JQuestionOption> questionOptions);

	int deleteQuestion(Long questionId);

	int updateQuestion(QuestionInfoWithBLOBs record);

	PageInfo<QuestionResultDTO> selectQuestion(Integer pageNum, Integer pageSize,String content);

	QuestionInfoWithBLOBs selectQuestionTitle(Long questionId);

	int selectQuestionCount(Integer questionType);

	String selectAnalysis(Long questionId);
}
