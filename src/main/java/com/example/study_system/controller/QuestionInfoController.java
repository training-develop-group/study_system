package com.example.study_system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.dto.QuestionResultDTO;
import com.example.study_system.model.JQuestionOption;
import com.example.study_system.model.QuestionInfo;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.github.pagehelper.PageInfo;
import com.alibaba.fastjson.*;

@RestController
@RequestMapping("/question")
public class QuestionInfoController extends BaseController {
	/**
	 * 添加问题
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/question", method = RequestMethod.POST)
	public ResultDTO addQuestion(@RequestParam("question") String question,
			@RequestParam("questionOption") String questionOptions) {
<<<<<<< HEAD
		List<JQuestionOption> options = JSON.parseArray(questionOptions, JQuestionOption.class);
		QuestionInfoWithBLOBs questionInfo = JSON.parseObject(question, QuestionInfoWithBLOBs.class);
		int result = serviceFacade.getQuestionInfoService().addQuestion(questionInfo, options);
		return success(result);
=======
		if (question == null || questionOptions == null) {
			return validationError();
		} else {
			List<JQuestionOption> options = JSON.parseArray(questionOptions, JQuestionOption.class);
			QuestionInfoWithBLOBs questionInfo = JSON.parseObject(question, QuestionInfoWithBLOBs.class);
			int result = serviceFacade.getQuestionInfoService().addQuestion(questionInfo, options);
			return success(result);
		}
>>>>>>> dev-wtq
	}

	/**
	 * 删除问题
	 * 
	 * @param questionId
	 * @return
	 */

	@RequestMapping(value = "/{questionId}", method = RequestMethod.DELETE)
	public ResultDTO deleteQuestion(@PathVariable("questionId") Long questionId) {
<<<<<<< HEAD
		return success(serviceFacade.getQuestionInfoService().deleteQuestion(questionId));
=======
		if (questionId == null) {
			return validationError();
		} else {
			return success(serviceFacade.getQuestionInfoService().deleteQuestion(questionId));
		}
>>>>>>> dev-wtq
	}

	/**
	 * 修改问题
	 * 
	 * @param questionId
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/{questionId}", method = RequestMethod.POST)
	public ResultDTO updateQeustionInfo(@PathVariable("questionId") Long questionId,
<<<<<<< HEAD
			@RequestParam("question") String question, @RequestParam("questionOption") String questionOptions,
			@RequestParam("count") Integer count) {
		List<JQuestionOption> options = JSON.parseArray(questionOptions, JQuestionOption.class);
		QuestionInfoWithBLOBs questionInfo = JSON.parseObject(question, QuestionInfoWithBLOBs.class);
		int result = serviceFacade.getQuestionInfoService().updateQuestion(questionInfo, options, count);
		return success(result);
=======
			@RequestParam("question") String question, @RequestParam("questionOption") String questionOptions) {
		if (questionId == null || question == null || questionOptions == null) {
			return validationError();
		} else {
			List<JQuestionOption> options = JSON.parseArray(questionOptions, JQuestionOption.class);
			QuestionInfoWithBLOBs questionInfo = JSON.parseObject(question, QuestionInfoWithBLOBs.class);
			int result = serviceFacade.getQuestionInfoService().updateQuestion(questionInfo, options);
			return success(result);
		}
>>>>>>> dev-wtq
	}

	/**
	 * 查询问题列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public ResultDTO selectQuestion(@RequestParam(value = "pageNum") Integer pageNum,
			@RequestParam(value = "pageSize") Integer pageSize,
<<<<<<< HEAD
			@RequestParam(value = "content", required = false) String content) {

		PageInfo<QuestionResultDTO> result = serviceFacade.getQuestionInfoService().selectQuestion(pageNum, pageSize,
				content);

		return success(result);
=======
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "questionType", required = false) Integer questionType) {
//			@RequestParam("questionType") Integer questionType) {
//		if (pageSize == null || content == null || pageNum == null || questionType == null) {
		if (pageSize == null || content == null || pageNum == null) {
			return validationError();
		} else {
			PageInfo<QuestionResultDTO> result = serviceFacade.getQuestionInfoService().selectQuestion(pageNum,
					pageSize, content, questionType);
			return success(result);
		}
>>>>>>> dev-wtq
	}

	/**
	 * 查询问题详细信息
	 * 
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
	public ResultDTO selectQuestionDetailed(@PathVariable("questionId") Long questionId) {
<<<<<<< HEAD
		return success(serviceFacade.getQuestionInfoService().selectQuestionTitle(questionId));
=======
		if (questionId == null) {
			return validationError();
		} else {
			return success(serviceFacade.getQuestionInfoService().selectQuestionTitle(questionId));
		}
>>>>>>> dev-wtq
	}

	/**
	 * 查询问题数量 1.单选 2.多选
	 * 
	 * @param questionType
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResultDTO selectQuestionCount(@RequestParam(value = "questionType", required = false) Integer questionType) {
<<<<<<< HEAD
		return success(serviceFacade.getQuestionInfoService().selectQuestionCount(questionType));
=======
		if (questionType == null) {
			return validationError();
		} else {
			return success(serviceFacade.getQuestionInfoService().selectQuestionCount(questionType));
		}
>>>>>>> dev-wtq
	}

	/**
	 * 查询解析、答案
	 * 
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value = "/answer", method = RequestMethod.GET)
	public ResultDTO selectAnalysis(@RequestParam("questionId") Long questionId) {
<<<<<<< HEAD
		return success(serviceFacade.getQuestionInfoService().selectAnalysis(questionId));
=======
		if (questionId == null) {
			return validationError();
		} else {
			return success(serviceFacade.getQuestionInfoService().selectAnalysis(questionId));
		}
>>>>>>> dev-wtq
	}

}
