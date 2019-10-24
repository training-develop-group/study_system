package com.example.study_system.service.iface;

import com.example.study_system.model.QuestionInfoWithBLOBs;

public interface IQuestionInfoService {
	int insertSelective(QuestionInfoWithBLOBs record);
}
