package com.example.study_system.service.impl;

import com.example.study_system.dto.PaperQuestionResultDTO;
import com.example.study_system.dto.PaperResultDTO;
import com.example.study_system.dto.QuestionResultDTO;
import com.example.study_system.model.*;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IPaperInfoService;
import com.example.study_system.util.UserUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaperInfoServiceImpl extends BaseService implements IPaperInfoService {
	// 新建试卷
	@Override
	public int insert(HttpServletRequest request, String paperName) {
		UserInfo userInfo = UserUtil.getUser(request);
		return paperInfoMapper.insert(paperName, userInfo.getUserName());
	}

	// 修改试卷名
	@Override
	public int modifyTestPaperName(HttpServletRequest request, Long paperId , String paperName) {
		UserInfo userInfo = UserUtil.getUser(request);
		return paperInfoMapper.updateByPrimaryKey(paperId, paperName , userInfo.getUserName());
	}

	// 查找用户答案
	@Override
	public PaperResultDTO UserQuestionAnswer(Long taskId, Long paperId, String userId) {
		PaperResultDTO paper = detailsOfExaminationPapers(paperId);
		Float totalPoints = 0f;
		Float question = 0f;
		if (paper != null) {
			for (QuestionResultDTO item : paper.getQuestionList()) {
				List<JUserQuesAnswerRecord> jUserQuesAnswerRecord = jUserQuesAnswerRecordMapper
						.selectUserQuestionAnswer(taskId, paperId, userId, item.getQuestionId());
				if (jUserQuesAnswerRecord != null) {
					totalPoints += jUserQuesAnswerRecord.get(0).getScord();
						item.setUserAnswer(jUserQuesAnswerRecord.get(0).getAnswer());
					
				} else {
					item.setUserAnswer(null);
				}
				question += item.getScore();
			}
		}
		if (paper != null) {
			paper.setUserScore(totalPoints);
			paper.setScore(question);
		}
		return paper;
	}

	// 删除试卷
	@Override
	public int deleteTestPaper(Long paperId) {
		// 查询试题关系
		List<JPaperQuestion> jPaperQuestionList = jPaperQuestionMapper.selectQuestionByPaperId(paperId);
		jPaperQuestionList.forEach(item -> {
			// 查询这个试题在全部试卷中的绑定的数量
			int questionIdNum = jPaperQuestionMapper.selectQuestionId(item.getQuestionId());
			if (questionIdNum == 1) {
				// 修改状态
				questionInfoMapper.updateQuestionStatusNew(item.getQuestionId());
			}
		});
		// 删除试卷关系
		jPaperQuestionMapper.deleteByPaperId(paperId);
		// 删除试卷
		return paperInfoMapper.deleteByPrimaryKey(paperId);
	}

	// 获取试卷详情
	@Override
	@Transactional
	public PaperResultDTO detailsOfExaminationPapers(Long paperId) {
		// 结果集
		PaperResultDTO result;
		// 取该试卷所有信息
		PaperInfo paper = paperInfoMapper.selectByPrimaryKey(paperId);
		if (paper == null) {
			return null;
		} else {
			result = new PaperResultDTO(paper);
		}
		// 通过试卷ID取所有试题关系
		List<JPaperQuestion> jPaperQuestionList = jPaperQuestionMapper.selectQuestionByPaperId(paperId);
		// 获取试题id集合
		List<Long> questionIdList = new ArrayList<Long>();
		// 获取分值map<questionId,JPaperQuestion>
		Map<Long, JPaperQuestion> jPaperQuestionMap = new HashMap<>();
		jPaperQuestionList.forEach(jPaperQuestion -> {
			questionIdList.add(jPaperQuestion.getQuestionId());
			jPaperQuestionMap.put(jPaperQuestion.getQuestionId(), jPaperQuestion);
		});

		if (CollectionUtils.isEmpty(questionIdList)) {
			// 空处理
			result.setQuestionList(new ArrayList<>());
			return result;
		}
		// 根据questionIds取选项List
		List<JQuestionOption> questionOptionList = jQuestionOptionMapper.selectQuestionByQuestionIdList(questionIdList);
		// 根据questionIds取试题List
		List<QuestionInfoWithBLOBs> questionList = questionInfoMapper.selectByQuestionIds(questionIdList);
		// 试题加选项的集合
		List<QuestionResultDTO> questionInfoList = new ArrayList<QuestionResultDTO>();
		// 设置选项map<questionId,List<JQuestionOption>>
		Map<Long, List<JQuestionOption>> optionMap = new HashMap<>();
		questionOptionList.forEach(option -> {
			// 比较key然后添加选项
			if (optionMap.containsKey(option.getQuestionId())) {
				optionMap.get(option.getQuestionId()).add(option);
			} else {
				List<JQuestionOption> temp = new ArrayList<>();
				temp.add(option);
				optionMap.put(option.getQuestionId(), temp);
			}
		});

		// 处理试题集合
		for (QuestionInfoWithBLOBs question : questionList) {
			// question信息处理
			QuestionResultDTO questionInfo = new QuestionResultDTO(question);
			// 处理分数，排序
			if (jPaperQuestionMap.containsKey(question.getQuestionId())) {
				questionInfo.setScore(jPaperQuestionMap.get(question.getQuestionId()).getScore());
				questionInfo.setOrderIndex(jPaperQuestionMap.get(question.getQuestionId()).getOrderIndex());
			} else {
				questionInfo.setScore(0f);
			}
			// 处理选项
			if (optionMap.containsKey(question.getQuestionId())) {
				questionInfo.setOptionInfo(optionMap.get(question.getQuestionId()));
			} else {
				questionInfo.setOptionInfo(new ArrayList<JQuestionOption>());
			}
			questionInfoList.add(questionInfo);
		}
		// 排序
		questionInfoList = questionInfoList.stream().sorted(Comparator.comparing(QuestionResultDTO::getOrderIndex))
				.collect(Collectors.toList());
		result.setQuestionList(questionInfoList);
		return result;
	}

	// 获取试卷列表
	@Override
	public PageInfo<PaperResultDTO> selectPaperInfos(Integer pageNum, Integer pageSize, String paperName) {
		PageHelper.startPage(pageNum, pageSize);
		List<PaperResultDTO> list = new ArrayList<PaperResultDTO>();
		List<PaperInfo> paperInfo = paperInfoMapper.selectPaperInfo(paperName);
		paperInfo.forEach(paper -> {
			PaperInfo singleAndMany = paperInfoMapper.selectQuestionInfoSingleAndMany(paper.getPaperId());
			PaperResultDTO papers = new PaperResultDTO(paper.getPaperId(), paper.getPaperName(), paper.getStatus(),
					singleAndMany.getMany(), singleAndMany.getSingle());
			list.add(papers);
		});
		PageInfo<PaperResultDTO> result = new PageInfo<>(list);

		result.setTotal(paperInfoMapper.selectAllPaperInfo(paperName));
		result.setPageNum(pageNum);
		return result;
	}

	// 查询单选和多选题数
	@Override
	public PaperInfo selectQuestionInfoSingleAndMany(Long paperId) {
		return paperInfoMapper.selectQuestionInfoSingleAndMany(paperId);
	}

	// 添加试题到试卷
	@Override
	public int insertJPQ(JPaperQuestion record) {
		return jPaperQuestionMapper.insertSelective(record);
	}

	/**
	 * 提交答案
	 */
	@Override
	@Transactional
	public PaperResultDTO answer(String userId, Long paperId, Long taskId,
			List<JUserTaskQuestionsInfoMapper> jUserQuesAnswerRecordInfo) {
		// 总分
		float score = 0;
		// 查找试卷所有信息(根据id查)
		PaperResultDTO paperResultDTO = detailsOfExaminationPapers(paperId);
		// 用来存正确答案
		System.out.println(paperResultDTO);
		Map<Long, JUserTaskQuestionsInfoMapper> isRight = new HashMap<Long, JUserTaskQuestionsInfoMapper>();
		paperResultDTO.getQuestionList().forEach(item -> {
			// 正确答案字符串
			String isRighta = "";
			// 拼接正确答案
			for (JQuestionOption Option : item.getOptionInfo()) {
				if (Option.getIsRight() == 1) {
					isRighta += (Option.getOptionType() + "|");
				}
			}
			// 添加到Map
			JUserTaskQuestionsInfoMapper jUserTask = new JUserTaskQuestionsInfoMapper();
			jUserTask.setAnswer(isRighta);
			jUserTask.setScore((item).getScore());
			isRight.put(item.getQuestionId(), jUserTask);
		});
		// 下面是用拼接的字符串来比较
		for (JUserTaskQuestionsInfoMapper jUserTask : jUserQuesAnswerRecordInfo) {
			// 为用户试题赋初始值
			jUserTask.setScore((float) 0.0);
			// 判断是否相等
			if (isRight.get(jUserTask.getQuestionId()).getAnswer().equals(jUserTask.getAnswer())) {
				// 加分
				score += isRight.get(jUserTask.getQuestionId()).getScore();
				// 修改他到正确
				jUserTask.setIsRight(1);
				// 获取当前题目的分数
				jUserTask.setScore(isRight.get(jUserTask.getQuestionId()).getScore());
			};
			
			for (QuestionResultDTO QuestionResult : paperResultDTO.getQuestionList()) {
				if (jUserTask.getQuestionId() == QuestionResult.getQuestionId()) {
					// 添加用户选择答案到paperResultDTO里面
					QuestionResult.setUserAnswer(jUserTask.getAnswer());
				}
			}
		};
		
		// 下面是添加部分
		JUserPaper jUserPaper = new JUserPaper();
		jUserPaper.setUserId(userId);
		jUserPaper.setPaperId(paperId);
		jUserPaper.setTaskId(taskId);
		jUserPaper.setScore(score);
		jUserPaperMapper.insert(jUserPaper);
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
			// 添加每道题的情况
			jUserQuesAnswerRecordMapper.insert(TSJUserQuesAnswerRecord);
		}
		// 修改他已经完成
		jUserTaskMapper.updateStatus(userId, taskId);
		paperResultDTO.setUserScore(score);
		// 返回处理完的DTO
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

	// 设置试题分数
	@Override
	public int updateScore(Long paperId, Long questionId, Float score) {
		return jPaperQuestionMapper.updateScore(paperId, questionId, score);
	}

	// 获取试卷成绩
	@Override
	public Long selectPaperScore(Long paperId) {
		return paperInfoMapper.selectPaperScore(paperId);
	}

	// 修改试卷
	int result = 0;

	@Override
	@Transactional
	public int addOrRemoveRelationships(List<JPaperQuestion> JPaperQuestionList,
			List<PaperQuestionResultDTO> PaperQuestionPesultList, List<PaperQuestionResultDTO> questionScoreList,
			List<JPaperQuestion> sortIng) {
//			删除试卷试题关系
		if (PaperQuestionPesultList.size() != 0) {
			PaperQuestionPesultList.forEach(item -> {
				result = jPaperQuestionMapper.delete(item.getPaperId(), item.getQuestionId());
				int questionIdNum = jPaperQuestionMapper.selectQuestionId(item.getQuestionId());
				if (questionIdNum == 0) {
					questionInfoMapper.updateQuestionStatusNew(item.getQuestionId());
				}
			});
		}
		// 添加试题到试卷
		if (JPaperQuestionList.size() != 0) {
			JPaperQuestionList.forEach(item -> {
				result = jPaperQuestionMapper.insertSelective(item);
				// 修改试题状态
				result = questionInfoMapper.updateQuestionStatus(item.getQuestionId());
			});
		}
		// 排序
		if (sortIng.size() != 0) {
			sortIng.forEach(item -> {
				// 先删除
				result = jPaperQuestionMapper.delete(item.getPaperId(), item.getQuestionId());
				// 在添加
				result = jPaperQuestionMapper.insertSelective(item);
				// 修改试题状态
				result = questionInfoMapper.updateQuestionStatus(item.getQuestionId());
			});
		}
		// 设置试卷里试题的分值
		if (questionScoreList.size() != 0) {
			questionScoreList.forEach(item -> 
			result = jPaperQuestionMapper.updateScore(item.getPaperId(), item.getQuestionId(), item.getScore()));
		}
		return result;
	}

//  查询部分试题
	@Override
	@Transactional
	public List<QuestionResultDTO> someQuestions(List<Long> questionIdList) {
		List<QuestionResultDTO> result = new ArrayList<QuestionResultDTO>();
		if (CollectionUtils.isEmpty(questionIdList)) {
			// 空处理
			result = new ArrayList<>();
			return result;
		}
		// 根据questionIds取选项List
		List<JQuestionOption> questionOptionList = jQuestionOptionMapper.selectQuestionByQuestionIdList(questionIdList);
		// 根据questionIds取试题List
		List<QuestionInfoWithBLOBs> questionList = questionInfoMapper.selectByQuestionIds(questionIdList);
		// 试题加选项的集合
		List<QuestionResultDTO> questionInfoList = new ArrayList<QuestionResultDTO>();
		// 设置选项map<questionId,List<JQuestionOption>>
		Map<Long, List<JQuestionOption>> optionMap = new HashMap<>();
		questionOptionList.forEach(option -> {
			if (optionMap.containsKey(option.getQuestionId())) {
				optionMap.get(option.getQuestionId()).add(option);
			} else {
				List<JQuestionOption> temp = new ArrayList<>();
				temp.add(option);
				optionMap.put(option.getQuestionId(), temp);
			}
		});

		// 处理试题集合
		for (QuestionInfoWithBLOBs question : questionList) {
			// question信息处理
			QuestionResultDTO questionInfo = new QuestionResultDTO(question);
			// 处理选项
			if (optionMap.containsKey(question.getQuestionId())) {
				questionInfo.setOptionInfo(optionMap.get(question.getQuestionId()));
			} else {
				questionInfo.setOptionInfo(new ArrayList<JQuestionOption>());
			}
			questionInfoList.add(questionInfo);
			result.add(questionInfo);
		}
		return result;
	}
	
//	获取请求路径
	public String getUrl() {
		return serverConfig.getUrl();
	}
}
