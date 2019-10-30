package com.example.study_system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.study_system.dto.PaperResultDTO;
import com.example.study_system.dto.QuestionResultDTO;
import com.example.study_system.model.JPaperQuestion;
import com.example.study_system.model.JUserPaper;
import com.example.study_system.model.JUserQuesAnswerRecord;
import com.example.study_system.model.PaperInfo;
import com.example.study_system.model.QuestionInfo;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IPaperInfoService;

@Service
public class PaperInfoServiceImpl extends BaseService implements IPaperInfoService {

	// 新建试卷
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
	@Transactional
	public PaperResultDTO detailsOfExaminationPapers(Long paperId) {
		
		PaperInfo paper = paperInfoMapper.selectByPrimaryKey(paperId);// 取該试卷所有信息
		List<QuestionResultDTO> questionInfo = new ArrayList<QuestionResultDTO>();
		jPaperQuestionMapper.selectQuestionByPaperId(paperId).forEach(item -> {// 通過試卷ID取所有試題ID
			QuestionInfoWithBLOBs question = questionInfoMapper.selectByPrimaryKey(item.getQuestionId());// 取試題ID
			questionInfo.add(new QuestionResultDTO(question.getQuestionId(), question.getQuestionType(),
					question.getScore(), question.getDifficulty(), question.getContent(), question.getAnalysis(),
					question.getStatus(), question.getcTime(), question.getmTime(), question.getcUser(),
					question.getmUser(), jQuestionOptionMapper.selectQuestionByQuestionId(item.getQuestionId())));
		});
		PaperResultDTO paperResultDTO = new PaperResultDTO(paperId, paper.getPaperName(), paper.getScore(),
				paper.getStatus(), paper.getcTime(), paper.getmTime(), paper.getcUser(), paper.getmUser(),
				questionInfo);

		return paperResultDTO;
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
