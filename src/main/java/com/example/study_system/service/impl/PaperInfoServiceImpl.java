package com.example.study_system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.study_system.model.JPaperQuestion;
import com.example.study_system.model.JUserPaper;
import com.example.study_system.model.JUserQuesAnswerRecord;
import com.example.study_system.model.PaperInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IPaperInfoService;

@Service
public class PaperInfoServiceImpl extends BaseService implements IPaperInfoService {
//	新建试卷
	@Override
	public int insert(PaperInfo record) {
		return paperInfoMapper.insertSelective(record);
	}

//	修改试卷名
	@Override
	public int modifyTestPaperName(PaperInfo record) {
		return paperInfoMapper.updateByPrimaryKeySelective(record);
	}

//	删除试卷
	@Override
	public int deleteTestPaper(Long paperId) {
		return paperInfoMapper.deleteByPrimaryKey(paperId);
	}

//	获取试卷详情
	@Override
	public PaperInfo detailsOfExaminationPapers(Long paperId) {
		return paperInfoMapper.selectByPrimaryKey(paperId);
	}

	@Override
	public List<PaperInfo> selectAllPaperInfo() {
		return paperInfoMapper.selectAllPaperInfo();
	}

	@Override
	public List<PaperInfo> select() {
		return paperInfoMapper.select();
	}

	@Override
	public int selectAllQuestionInfoSingle(Long paperId) {
		return paperInfoMapper.selectAllQuestionInfoSingle(paperId);
	}

	@Override
	public int selectAllQuestionInfoMany(Long paperId) {
		return paperInfoMapper.selectAllQuestionInfoMany(paperId);
	}

	@Override
	public int insertJPQ(JPaperQuestion record) {
		return jPaperQuestionMapper.insert(record);
	}

	float score = 0;

	/**
	 * 提交答案
	 */
	@Override
	@Transactional
	public float answer(JUserPaper jUserPaperInfo, List<JUserQuesAnswerRecord> jUserQuesAnswerRecordInfo) {
		Date date = new Date();
		jUserPaperInfo.setcTime(date);
		jUserPaperMapper.insertSelective(jUserPaperInfo);
		jUserQuesAnswerRecordInfo.forEach(item -> {
			if (item.getIsRight() == 1) {
				score += item.getScord();
			}
			item.setAnswerValue(jUserPaperInfo.getRef());
			item.setcTime(date);
			jUserQuesAnswerRecordMapper.insertSelective(item);
		});
		jUserTaskMapper.updateStatus(jUserPaperInfo.getUserId(), jUserPaperInfo.getTaskId());
		jUserPaperMapper.updateScore(score, jUserPaperInfo.getUserId(), jUserPaperInfo.getTaskId());
		return score;
	}

	/**
	 * 搜索试卷数量
	 */
	@Override
	public int selectPaperCount() {
		return paperInfoMapper.selectPaperCount();
	}
}
