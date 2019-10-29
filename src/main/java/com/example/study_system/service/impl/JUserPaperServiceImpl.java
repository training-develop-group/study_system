package com.example.study_system.service.impl;

import org.springframework.stereotype.Service;

import com.example.study_system.dao.JUserPaperMapper;
import com.example.study_system.model.JUserPaper;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IJUserPaperService;

@Service
public class JUserPaperServiceImpl extends BaseService implements IJUserPaperService {
	@Override
	public int insertSelective(JUserPaper record) {
		return jUserPaperMapper.insertSelective(record);
	}

	@Override
	public int updateScore(Float score, String userId, Long paperId) {
		return jUserPaperMapper.updateScore(score, userId, paperId);
	}

}
