package com.example.study_system.service.impl;

import org.springframework.stereotype.Service;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IQuestionInfoService;

@Service
public class QuestionInfoServiceImpl  extends BaseService implements IQuestionInfoService{
	@Override
	public int insertSelective(QuestionInfoWithBLOBs record) {
		return questionInfoMapper.insertSelective(record);
    }
}
