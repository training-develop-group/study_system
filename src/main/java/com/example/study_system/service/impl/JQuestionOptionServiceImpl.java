package com.example.study_system.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.example.study_system.dao.JQuestionOptionMapper;
import com.example.study_system.model.JQuestionOption;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IJQuestionOptionService;

@Service
public class JQuestionOptionServiceImpl extends BaseService implements IJQuestionOptionService {

	@Override
	public int insertSelective(JQuestionOption record) {
		return jQuestionOptionMapper.insertSelective(record);
	}

	@Override
	public List<JQuestionOption> selectQuestionByQuestionId(Long questionId) {
		return jQuestionOptionMapper.selectQuestionByQuestionId(questionId);
	}

	@Override
	public List<JQuestionOption> selectAllQuestionOption() {
		return jQuestionOptionMapper.selectAllQuestionOption();
	}

	@Override
	public int deleteQuestionOptionByQuestionId(Long questionId) {
		return jQuestionOptionMapper.deleteQuestionOptionByQuestionId(questionId);
	}
}
