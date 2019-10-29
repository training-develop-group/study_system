package com.example.study_system.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.study_system.dao.JQuestionOptionMapper;
import com.example.study_system.dao.QuestionInfoMapper;
import com.example.study_system.dto.QuestionResultDTO;
import com.example.study_system.model.JQuestionOption;
import com.example.study_system.model.QuestionInfo;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zaxxer.hikari.util.SuspendResumeLock;

@Service
public class QuestionInfoServiceImpl extends BaseService implements IQuestionInfoService {
	

	@Override
	@Transactional
	public int addQuestion(QuestionInfoWithBLOBs question, List<JQuestionOption> questionOptions) {
		Date date = new Date();
		question.setcTime(date);
		question.setcUser("未定义");
		int result = questionInfoMapper.insertSelective(question);
		questionOptions.forEach(item -> {
			item.setcUser("未定义");
			item.setcTime(date);
			item.setQuestionId(question.getQuestionId());
			jQuestionOptionMapper.insertSelective(item);
		});

		return result;
	}

	@Override
	@Transactional
	public int deleteQuestion(Long questionId) {
		int result = questionInfoMapper.deleteByPrimaryKey(questionId);
		jQuestionOptionMapper.deleteQuestionOptionByQuestionId(questionId);
		return result;
	}

	@Override
	@Transactional
	public int updateQuestion(QuestionInfoWithBLOBs question, List<JQuestionOption> questionOptions) {
		Date date = new Date();
		question.setcTime(date);
		question.setmTime(date);
		question.setcUser("未定义");
		question.setmUser("未定义");
		int result = questionInfoMapper.deleteByPrimaryKey(question.getQuestionId());
		questionInfoMapper.insertSelective(question);
		List<JQuestionOption> options = questionOptions;
		jQuestionOptionMapper.deleteQuestionOptionByQuestionId(question.getQuestionId());
		options.forEach(item -> {
			item.setcTime(date);
			item.setmTime(date);
			item.setcUser("未定义");
			item.setmUser("未定义");
			item.setQuestionId(question.getQuestionId());
			jQuestionOptionMapper.insertSelective(item);
		});
		return result;
	}

	@Override
	@Transactional
	public PageInfo<QuestionResultDTO> selectQuestion(Integer pageNum, Integer pageSize, String content,
			Integer questionType) {
		PageHelper.startPage(pageNum, pageSize);
		List<QuestionInfoWithBLOBs> questionList = questionInfoMapper.selectAllQuestion(content, questionType);
		List<QuestionResultDTO> questionResultDTO = new ArrayList<QuestionResultDTO>();
		questionList.forEach(questionItem -> {
			List<JQuestionOption> optionList = jQuestionOptionMapper
					.selectQuestionByQuestionId(questionItem.getQuestionId());
			questionResultDTO.add(new QuestionResultDTO(questionItem.getQuestionId(), questionItem.getQuestionType(),
					questionItem.getScore(), questionItem.getDifficulty(), questionItem.getContent(),
					questionItem.getAnalysis(), questionItem.getStatus(), questionItem.getcTime(),
					questionItem.getmTime(), questionItem.getcUser(), questionItem.getmUser(), optionList));
		});
		PageInfo<QuestionResultDTO> result = new PageInfo<QuestionResultDTO>(questionResultDTO);
		result.setTotal(questionInfoMapper.selectQuestionCount(questionType));
		return result;
	}

	@Override
	@Transactional
	public List<QuestionResultDTO> selectQuestionTitle(Long questionId) {
		QuestionInfoWithBLOBs question = questionInfoMapper.selectByPrimaryKey(questionId);
		List<JQuestionOption> questionOptionList = jQuestionOptionMapper.selectQuestionByQuestionId(questionId);
		List<QuestionResultDTO> questionResultDTO = new ArrayList<QuestionResultDTO>();
		questionResultDTO.add(new QuestionResultDTO(question.getQuestionId(), question.getQuestionType(),
				question.getScore(), question.getDifficulty(), question.getContent(), question.getAnalysis(),
				question.getStatus(), question.getcTime(), question.getmTime(), question.getcUser(),
				question.getmUser(), questionOptionList));
		questionResultDTO.forEach(a -> {
		});
		return questionResultDTO;
	}

	@Override
	public int selectQuestionCount(Integer questionType) {
		return questionInfoMapper.selectQuestionCount(questionType);
	}

	@Override
	public List<QuestionInfoWithBLOBs> selectAnalysis(Long questionId) {
		return questionInfoMapper.selectAnalysisById(questionId);
	}
}
