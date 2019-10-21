package com.example.study_system.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class QuestionInfoServiceImpl implements IQuestionInfoService {
	@Resource
	private QuestionInfoMapper questionInfoMapper;
	@Resource
	private JQuestionOptionMapper jQuestionInfoMapper;

	@Override
	public int insertSelective(QuestionInfoWithBLOBs record) {
		Date date = new Date();
		record.setcTime(date);

		return questionInfoMapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int deleteByPrimaryKey(Long questionId) {
		int result = questionInfoMapper.deleteByPrimaryKey(questionId);
		jQuestionInfoMapper.deleteByPrimaryKey(questionId);
		return result;
	}

	@Override
	public int updateByPrimaryKeySelective(QuestionInfoWithBLOBs record) {
		Date date = new Date();
		record.setmTime(date);
		
		return questionInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional
	public PageInfo<QuestionResultDTO> selectAllQuestion(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<QuestionInfoWithBLOBs> questionList = questionInfoMapper.selectAllQuestion();
		List<JQuestionOption> questionOptionList = jQuestionInfoMapper.selectAllQuestionOption();
		List<QuestionResultDTO> questionResultDTO = new ArrayList<QuestionResultDTO>();
		questionList.forEach(questionItem -> {
			questionOptionList.forEach(optionItem -> {
				if (questionItem.getQuestionId() == optionItem.getQuestionId()) {
					questionResultDTO
							.add(new QuestionResultDTO(questionItem.getQuestionId(), questionItem.getQuestionType(),
									questionItem.getScore(), questionItem.getDifficulty(), optionItem.getIsRight(),
									optionItem.getOptionType(), questionItem.getContent(), questionItem.getAnalysis(),
									optionItem.getContent(), questionItem.getStatus(), questionItem.getcTime(),
									questionItem.getmTime(), questionItem.getcUser(), questionItem.getmUser()));
				}
			});
		});
		PageInfo<QuestionResultDTO> result = new PageInfo<QuestionResultDTO>(questionResultDTO);
		return result;
	}

	@Override
	public QuestionInfoWithBLOBs selectByPrimaryKey(Long questionId) {
		return questionInfoMapper.selectByPrimaryKey(questionId);
	}

	@Override
	public int selectQuestionCount(Integer questionType) {
		return questionInfoMapper.selectQuestionCount(questionType);
	}

	@Override
	public String selectAnalysisById(Long questionId) {
		return questionInfoMapper.selectAnalysisById(questionId);
	}
}
