package com.example.study_system.service.impl;

import org.springframework.stereotype.Service;

import com.example.study_system.model.JPaperQuestion;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IJPaperQuestionService;

@Service
public class JPaperQuestionServiceImpl extends BaseService implements IJPaperQuestionService{

	@Override
    public int insertSelective(JPaperQuestion record) {
		return jPaperQuestionMapper.insertSelective(record);
    }
}
