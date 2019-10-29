package com.example.study_system.service.impl;

import org.springframework.stereotype.Service;

import com.example.study_system.dao.JUserQuesAnswerRecordMapper;
import com.example.study_system.model.JUserQuesAnswerRecord;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IJUserQuesAnswerRecordService;
@Service
public class JUserQuesAnswerRecordServiceImpl extends BaseService implements IJUserQuesAnswerRecordService {
	@Override
	public int insertSelective(JUserQuesAnswerRecord record) {
		return jUserQuesAnswerRecordMapper.insertSelective(record);
	}
}
