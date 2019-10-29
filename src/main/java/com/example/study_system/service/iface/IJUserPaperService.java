package com.example.study_system.service.iface;

import com.example.study_system.model.JUserPaper;

public interface IJUserPaperService {
	int insertSelective(JUserPaper record);
	
	int updateScore(Float score, String userId, Long paperId);
}
