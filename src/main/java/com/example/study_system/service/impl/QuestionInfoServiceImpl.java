package com.example.study_system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.example.study_system.dto.QuestionResultDTO;
import com.example.study_system.model.JQuestionOption;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.example.study_system.model.UserInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.example.study_system.util.UserUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class QuestionInfoServiceImpl extends BaseService implements IQuestionInfoService {
	/**
	 * 修改试题分数 score:分数   questionId:试题id
	 */
	@Override
	public int updateQuestionScore(Float score, Long questionId) {
		return questionInfoMapper.updateQuestionScore(score, questionId);
	}

	/**
	 * 添加试题 question:试题对象 questionOptions:试题内选项集合
	 */
	@Override
	@Transactional
	public int addQuestion(HttpServletRequest request,QuestionInfoWithBLOBs question, List<JQuestionOption> questionOptions) {
		UserInfo userInfo = UserUtil.getUser(request);
		Date date = new Date();
		question.setcTime(date);
		question.setcUser(userInfo.getUserName());
		question.setmTime(date);
		question.setmUser(userInfo.getUserName());
		int result = questionInfoMapper.insertSelective(question);
		questionOptions.forEach(item -> {
			item.setcUser(userInfo.getUserName());
			item.setcTime(date);
			item.setmUser(userInfo.getUserName());
			item.setmTime(date);
			item.setQuestionId(question.getQuestionId());
			jQuestionOptionMapper.insertSelective(item);
		});

		return result;
	}

	/**
	 * 删除试题
	 */
	@Override
	@Transactional
	public int deleteQuestion(Long questionId) {
		int result = questionInfoMapper.deleteByPrimaryKey(questionId);
		jQuestionOptionMapper.deleteQuestionOptionByQuestionId(questionId);
		return result;
	}

	/**
	 * 修改试题 question:试题对象 questionOptions:试题内选项集合
	 */
	@Override
	@Transactional
	public int updateQuestion(HttpServletRequest request,QuestionInfoWithBLOBs question, List<JQuestionOption> questionOptions) {
		UserInfo userInfo = UserUtil.getUser(request);
		Date date = new Date();
		question.setcTime(date);
		question.setmTime(date);
		question.setcUser(userInfo.getUserName());
		question.setmUser(userInfo.getUserName());
		int result = questionInfoMapper.deleteByPrimaryKey(question.getQuestionId());
		questionInfoMapper.insertSelective(question);
		List<JQuestionOption> options = questionOptions;
		jQuestionOptionMapper.deleteQuestionOptionByQuestionId(question.getQuestionId());
		options.forEach(item -> {
			item.setcTime(date);
			item.setmTime(date);
			item.setcUser(userInfo.getUserName());
			item.setmUser(userInfo.getUserName());
			item.setQuestionId(question.getQuestionId());
			jQuestionOptionMapper.insertSelective(item);
		});
		return result;
	}

	/**
	 * 查看试题列表 pageNum：当前页数 pageSize：当前页面的数据数量 content:题目 questionType:试题类型 1单选 2多选
	 */
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
			questionResultDTO.add(new QuestionResultDTO(questionItem, optionList));
		});
		PageInfo<QuestionResultDTO> result = new PageInfo<QuestionResultDTO>(questionResultDTO);
		result.setTotal(questionInfoMapper.selectQuestionCountNum(content, questionType));
		result.setPageNum(pageNum);
		return result;
	}

	/**
	 * 查看试题详细 questionId:试题id
	 */
	@Override
	@Transactional
	public List<QuestionResultDTO> selectQuestionTitle(Long questionId) {
		QuestionInfoWithBLOBs question = questionInfoMapper.selectByPrimaryKey(questionId);
		List<JQuestionOption> questionOptionList = jQuestionOptionMapper.selectQuestionByQuestionId(questionId);
		List<QuestionResultDTO> questionResultDTO = new ArrayList<QuestionResultDTO>();
		questionResultDTO.add(new QuestionResultDTO(question, questionOptionList));
		return questionResultDTO;
	}

	/**
	 * 查询试题数量 questionType:试题类型 1单选 2多选
	 */
	@Override
	public int selectQuestionCount(Integer questionType) {
		return questionInfoMapper.selectQuestionCount(questionType);
	}

	/**
	 * 查询试题答案解析 questionId:试题id
	 */

	@Override
	public List<QuestionInfoWithBLOBs> selectAnalysis(Long questionId) {
		return questionInfoMapper.selectAnalysisById(questionId);
	}
}
