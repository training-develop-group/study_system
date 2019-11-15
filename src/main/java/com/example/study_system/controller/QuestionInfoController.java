package com.example.study_system.controller;

import com.alibaba.fastjson.JSON;
import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.dto.QuestionResultDTO;
import com.example.study_system.model.JQuestionOption;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.github.pagehelper.PageInfo;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionInfoController extends BaseController {
    /**
     * 添加问题
     *
     * @return
     */
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    public ResultDTO addQuestion(@RequestParam("question") String question,
                                 @RequestParam("questionOption") String questionOptions) {
        if (StringUtils.isEmpty(question) || StringUtils.isEmpty(questionOptions)) {
            return validationError();
        } else {
            List<JQuestionOption> options = JSON.parseArray(questionOptions, JQuestionOption.class);
            QuestionInfoWithBLOBs questionInfo = JSON.parseObject(question, QuestionInfoWithBLOBs.class);
            int result = serviceFacade.getQuestionInfoService().addQuestion(questionInfo, options);
            return success(result);
        }
    }

    /**
     * 删除问题
     *
     * @param questionId
     * @return
     */

    @RequestMapping(value = "/{questionId}", method = RequestMethod.DELETE)
    public ResultDTO deleteQuestion(@PathVariable("questionId") Long questionId) {
        if (questionId == null) {
            return validationError();
        } else {
            return success(serviceFacade.getQuestionInfoService().deleteQuestion(questionId));
        }
    }

    /**
     * 修改问题
     *
     * @param questionId
     * @return
     */
    @RequestMapping(value = "/{questionId}", method = RequestMethod.POST)
    public ResultDTO updateQuestionInfo(@PathVariable("questionId") Long questionId,
                                        @RequestParam("question") String question, @RequestParam("questionOption") String questionOptions) {
        if (questionId == null || StringUtils.isEmpty(question) || StringUtils.isEmpty(questionOptions)) {
            return validationError();
        } else {
            List<JQuestionOption> options = JSON.parseArray(questionOptions, JQuestionOption.class);
            QuestionInfoWithBLOBs questionInfo = JSON.parseObject(question, QuestionInfoWithBLOBs.class);
            int result = serviceFacade.getQuestionInfoService().updateQuestion(questionInfo, options);
            return success(result);
        }
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
                                    @RequestParam(value = "content", required = false) String content,
                                    @RequestParam(value = "questionType", required = false) Integer questionType) {
        if (pageSize == null || pageNum == null) {
            return validationError();
        } else {
            PageInfo<QuestionResultDTO> result = serviceFacade.getQuestionInfoService().selectQuestion(pageNum,
                    pageSize, content, questionType);
            if (result == null) {
            	return noData();
            }
            return success(result);
        }
    }

    /**
     * 查询问题详细信息
     *
     * @param questionId
     * @return
     */
    @RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
    public ResultDTO selectQuestionDetailed(@PathVariable("questionId") Long questionId) {
        if (questionId == null) {
            return validationError();
        } else {
        	if (serviceFacade.getQuestionInfoService().selectQuestionTitle(questionId) == null) {
        		return noData();
        	}
            return success(serviceFacade.getQuestionInfoService().selectQuestionTitle(questionId));
        }
    }

    /**
     * 查询问题数量 1.单选 2.多选
     *
     * @param questionType
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResultDTO selectQuestionCount(@RequestParam(value = "questionType", required = false) Integer questionType) {
        if (questionType == null) {
            return validationError();
        } else {
            return success(serviceFacade.getQuestionInfoService().selectQuestionCount(questionType));
        }
    }

    /**
     * 查询解析、答案
     *
     * @param questionId
     * @return
     */
    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ResultDTO selectAnalysis(@RequestParam("questionId") Long questionId) {
        if (questionId == null) {
            return validationError();
        } else {
        	if (serviceFacade.getQuestionInfoService().selectAnalysis(questionId) == null) {
        		return noData();
        	}
            return success(serviceFacade.getQuestionInfoService().selectAnalysis(questionId));
        }
    }

}
