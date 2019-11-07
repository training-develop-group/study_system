package com.example.study_system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.study_system.dto.PaperQuestionPesultDTO;
import com.example.study_system.dto.PaperResultDTO;
import com.example.study_system.dto.QuestionResultDTO;
import com.example.study_system.model.JPaperQuestion;
import com.example.study_system.model.JQuestionOption;
import com.example.study_system.model.JUserPaper;
import com.example.study_system.model.JUserQuesAnswerRecord;
import com.example.study_system.model.JUserTaskQuestionsInfoMapper;
import com.example.study_system.model.PaperInfo;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IPaperInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class PaperInfoServiceImpl extends BaseService implements IPaperInfoService {

//	新建试卷
	@Override
	public int insert(String paperName) {
		String cUser = "mc";
		return paperInfoMapper.insert(paperName, cUser);
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
//	@Transactional
	public PaperResultDTO detailsOfExaminationPapers(Long paperId) {
		// 取该试卷所有信息
		PaperInfo paper = paperInfoMapper.selectByPrimaryKey(paperId);
		List<QuestionResultDTO> questionInfo = new ArrayList<QuestionResultDTO>();
		// 通过试卷ID取所有ID
		jPaperQuestionMapper.selectQuestionByPaperId(paperId).forEach(item -> {
			// 取试题ID
			QuestionInfoWithBLOBs question = questionInfoMapper.selectByPrimaryKey(item.getQuestionId());
			questionInfo.add(new QuestionResultDTO(question.getQuestionId(), question.getQuestionType(),
					question.getScore(), question.getDifficulty(), question.getContent(), question.getAnalysis(),
					question.getStatus(), question.getcTime(), question.getmTime(), question.getcUser(),
					question.getmUser(), jQuestionOptionMapper.selectQuestionByQuestionId(item.getQuestionId()),
					item.getScore()));
		});
		PaperResultDTO paperResultDTO = new PaperResultDTO(paperId, paper.getPaperName(), paper.getScore(),
				paper.getStatus(), paper.getcTime(), paper.getmTime(), paper.getcUser(), paper.getmUser(),
				questionInfo);

		return paperResultDTO;
	}

//	获取试卷列表
	@Override
	public PageInfo<PaperResultDTO> selectPaperInfos(Integer pageNum, Integer pageSize, String paperName) {
		PageHelper.startPage(pageNum, pageSize);
		List<PaperResultDTO> list = new ArrayList<PaperResultDTO>();
		List<PaperInfo> paperInfo = paperInfoMapper.selectPaperInfo(paperName);
		paperInfo.forEach(paper -> {
			Integer Single = paperInfoMapper.selectAllQuestionInfoSingle(paper.getPaperId());
			Integer Many = paperInfoMapper.selectAllQuestionInfoMany(paper.getPaperId());
			PaperResultDTO papers = new PaperResultDTO(paper.getPaperId(), paper.getPaperName(), paper.getStatus(),
					Many, Single);
			list.add(papers);
		});
		PageInfo<PaperResultDTO> result = new PageInfo<>(list);

		result.setTotal(paperInfoMapper.selectAllPaperInfo(paperName));
		result.setPageNum(pageNum);
		return result;
	}

//	单选题
	@Override
	public int selectQuestionInfoSingles(Long paperId) {
		return paperInfoMapper.selectAllQuestionInfoSingle(paperId);
	}

//	多选题
	@Override
	public int selectQuestionInfoManys(Long paperId) {
		return paperInfoMapper.selectAllQuestionInfoMany(paperId);
	}

//	添加试题到试卷
	@Override
	public int insertJPQ(JPaperQuestion record) {
		return jPaperQuestionMapper.insertSelective(record);
	}

	float score = 0;

	/**
	 * 提交答案
	 */
	@Override
	@Transactional
	public PaperResultDTO answer(String userId, Long paperId, Long taskId,
			List<JUserTaskQuestionsInfoMapper> jUserQuesAnswerRecordInfo) {
		float score = 0;
		PaperResultDTO paperResultDTO = detailsOfExaminationPapers(paperId);
		Map<Long, JUserTaskQuestionsInfoMapper> isRight = new HashMap<Long, JUserTaskQuestionsInfoMapper>();
		paperResultDTO.getQuestions().forEach(item -> {
			String isRighta = "";
			for (JQuestionOption Option : item.getOptionInfo()) {
				if (Option.getIsRight() == 1) {
					isRighta += (Option.getOptionType() + "|");
				}
			}
			JUserTaskQuestionsInfoMapper jUserTask = new JUserTaskQuestionsInfoMapper();
			jUserTask.setAnswer(isRighta);
			jUserTask.setScore((item).getNewScore());
			isRight.put(item.getQuestionId(), jUserTask);
		});
		for (JUserTaskQuestionsInfoMapper jUserTask : jUserQuesAnswerRecordInfo) {
			jUserTask.setScore((float) 0.0);
			if (isRight.get(jUserTask.getQuestionId()).getAnswer().equals(jUserTask.getAnswer())) {
				score += isRight.get(jUserTask.getQuestionId()).getScore();
				jUserTask.setIsRight(1);
				jUserTask.setScore(isRight.get(jUserTask.getQuestionId()).getScore());
			};
			for (QuestionResultDTO xx : paperResultDTO.getQuestions()) {
				if (jUserTask.getQuestionId() == xx.getQuestionId()) {
					xx.setUserAnswer(jUserTask.getAnswer());
				}
			}
		}
		;

		JUserPaper jUserPaper = new JUserPaper();
		jUserPaper.setUserId(userId);
		jUserPaper.setPaperId(paperId);
		jUserPaper.setTaskId(taskId);
		jUserPaper.setScore(score);
		System.out.println(jUserPaper.getScore() + "asda");
		for (JUserTaskQuestionsInfoMapper jUserQuesAnswerRecord : jUserQuesAnswerRecordInfo) {
			JUserQuesAnswerRecord TSJUserQuesAnswerRecord = new JUserQuesAnswerRecord();
			TSJUserQuesAnswerRecord.setAnswerValue(jUserPaper.getRef());
			TSJUserQuesAnswerRecord.setAnswer(jUserQuesAnswerRecord.getAnswer());
			TSJUserQuesAnswerRecord.setUserId(userId);
			TSJUserQuesAnswerRecord.setQuestionId(jUserQuesAnswerRecord.getQuestionId());
			if (jUserQuesAnswerRecord.getIsRight() == null) {
				jUserQuesAnswerRecord.setIsRight(0);
			}
			TSJUserQuesAnswerRecord.setIsRight(jUserQuesAnswerRecord.getIsRight());
			TSJUserQuesAnswerRecord.setScord(jUserQuesAnswerRecord.getScore());
			jUserQuesAnswerRecordMapper.insert(TSJUserQuesAnswerRecord);
		}
		jUserTaskMapper.updateStatus(userId, taskId);
		paperResultDTO.setUserScore(score);
		return paperResultDTO;
	}

	/**
	 * 搜索试卷数量
	 */
	@Override
	public int selectPaperCount() {
		return paperInfoMapper.selectPaperCount();
	}

	@Override
	public int deletePQRelationship(Long paperId, Long questionId) {
		return jPaperQuestionMapper.delete(paperId, questionId);
	}

//	设置试题分数
	@Override
	public int updateScore(Long paperId, Long questionId, Float score) {
		return jPaperQuestionMapper.updateScore(paperId, questionId, score);
	}

//	获取试卷成绩
	@Override
	public Long selectPaperScore(Long paperId) {
		return paperInfoMapper.selectPaperScore(paperId);
	}

	// 修改试卷
	int result = 0;

	@Override
	@Transactional
	public int addOrRemoveRelationships(List<JPaperQuestion> JPaperQuestionList,
			List<PaperQuestionPesultDTO> PaperQuestionPesultList, List<PaperQuestionPesultDTO> questionScoreList,
			List<JPaperQuestion> sortIng) {
//		添加试题到试卷
		if (JPaperQuestionList.size() != 0) {
			JPaperQuestionList.forEach(item -> {
				result = jPaperQuestionMapper.insertSelective(item);
			});
		}
//		排序
		if (sortIng.size() != 0) {
			sortIng.forEach(item -> {
				System.out.println(item.getPaperId() + ' ' + item.getQuestionId());
//				先删除
				result = jPaperQuestionMapper.delete(item.getPaperId(), item.getQuestionId());
//				在添加
				result = jPaperQuestionMapper.insertSelective(item);
			});
		}
//		设置试卷里试题的分值
		if (questionScoreList.size() != 0) {
			questionScoreList.forEach(item -> {
				result = jPaperQuestionMapper.updateScore(item.getPaperId(), item.getQuestionId(), item.getScore());
			});
		}
//		删除试卷试题关系
		if (PaperQuestionPesultList.size() != 0) {
			PaperQuestionPesultList.forEach(item -> {
				result = jPaperQuestionMapper.delete(item.getPaperId(), item.getQuestionId());
			});
		}
		return result;
	}

}
