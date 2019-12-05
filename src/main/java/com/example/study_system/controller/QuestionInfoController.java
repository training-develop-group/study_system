package com.example.study_system.controller;

import com.alibaba.fastjson.JSON;
import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.dto.QuestionResultDTO;
import com.example.study_system.model.JQuestionOption;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.example.study_system.util.UserUtil;
import com.github.pagehelper.PageInfo;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/question")
public class QuestionInfoController extends BaseController {
	String userId = "";
	String userName = "";
    /**
	 * 	添加试题
     *
     * @return
     */
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    public ResultDTO addQuestion(HttpServletRequest request,
    								@RequestParam("question") String question,
    								@RequestParam("questionOption") String questionOptions) {
        if (StringUtils.isEmpty(question) || StringUtils.isEmpty(questionOptions)) {
            return validationError();
        } else {
            List<JQuestionOption> options = JSON.parseArray(questionOptions, JQuestionOption.class);
            QuestionInfoWithBLOBs questionInfo = JSON.parseObject(question, QuestionInfoWithBLOBs.class);
            String remark = userName + "添加试题";
    		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
    		String params = "question:" + question + "questionOption:" + questionOptions;
    		try {
    			int result = serviceFacade.getQuestionInfoService().addQuestion(request,questionInfo, options);
    			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
    			return success(result);
            } catch (Exception e){
            	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
            	int result = serviceFacade.getQuestionInfoService().addQuestion(request,questionInfo, options);
    			return success(result);
            }
        }
    }

    /**
	 * 	删除试题
     *
     * @param questionId
     * @return
     */

    @RequestMapping(value = "/{questionId}", method = RequestMethod.DELETE)
    public ResultDTO deleteQuestion(HttpServletRequest request,
    								@PathVariable("questionId") Long questionId) {
        if (questionId == null) {
            return validationError();
        } else {
        	String remark = userName + "删除试题";
    		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
    		String params = "questionId:" + questionId;
    		try {
    			int result = serviceFacade.getQuestionInfoService().deleteQuestion(questionId);
    			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
    			return success(result);
            } catch (Exception e){
            	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
            	int result = serviceFacade.getQuestionInfoService().deleteQuestion(questionId);
    			return success(result);
            }
        }
    }

    /**
     *	 修改试题
     *
     * @param questionId
     * @return
     */
    @RequestMapping(value = "/{questionId}", method = RequestMethod.POST)
    public ResultDTO updateQuestionInfo(HttpServletRequest request,
    									@PathVariable("questionId") Long questionId,
                                        @RequestParam("question") String question, 
                                        @RequestParam("questionOption") String questionOptions) {
        if (questionId == null || StringUtils.isEmpty(question) || StringUtils.isEmpty(questionOptions)) {
            return validationError();
        } else {
            List<JQuestionOption> options = JSON.parseArray(questionOptions, JQuestionOption.class);
            QuestionInfoWithBLOBs questionInfo = JSON.parseObject(question, QuestionInfoWithBLOBs.class);
            String remark = userName + "修改试题";
    		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
    		String params = "questionId:" + questionId + "question:" + question + "questionOption:" + questionOptions;
    		try {
    			int result = serviceFacade.getQuestionInfoService().updateQuestion(request,questionInfo, options);
    			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
    			return success(result);
            } catch (Exception e){
            	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
            	int result = serviceFacade.getQuestionInfoService().updateQuestion(request,questionInfo, options);
    			return success(result);
            }
        }
    }

    /**
     *	查询试题列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public ResultDTO selectQuestion(HttpServletRequest request,
    								@RequestParam(value = "pageNum") Integer pageNum,
                                    @RequestParam(value = "pageSize") Integer pageSize,
                                    @RequestParam(value = "content", required = false) String content,
                                    @RequestParam(value = "questionType", required = false) Integer questionType) {
    	userId = UserUtil.getUser(request).getUserId();
		userName = UserUtil.getUser(request).getUserName();
        if (pageSize == null || pageNum == null) {
            return validationError();
        } else {
        	String remark = userName + "查询试题列表";
    		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
    		String params = "pageNum:" + pageNum + "pageSize:" + pageSize + "content:" + content + "questionType:" + questionType;
    		try {
    			PageInfo<QuestionResultDTO> result = serviceFacade.getQuestionInfoService().selectQuestion(pageNum, pageSize, content, questionType);
                if (result == null) {
                	return noData();
                }
    			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
    			return success(result);
            } catch (Exception e){
            	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
            	PageInfo<QuestionResultDTO> result = serviceFacade.getQuestionInfoService().selectQuestion(pageNum, pageSize, content, questionType);
                if (result == null) {
                	return noData();
                }
    			return success(result);
            }
        }
    }

    /**
     *	查询试题详细信息
     *
     * @param questionId
     * @return
     */
    @RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
    public ResultDTO selectQuestionDetailed(HttpServletRequest request,
    										@PathVariable("questionId") Long questionId) {
        if (questionId == null) {
            return validationError();
        } else {
        	String remark = userName + "查询试题详细信息";
    		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
    		String params = "questionId:" + questionId;
    		try {
    			List<QuestionResultDTO> result = serviceFacade.getQuestionInfoService().selectQuestionTitle(questionId);
                if (result == null) {
                	return noData();
                }
    			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
    			return success(result);
            } catch (Exception e){
            	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
            	List<QuestionResultDTO> result = serviceFacade.getQuestionInfoService().selectQuestionTitle(questionId);
                if (result == null) {
                	return noData();
                }
    			return success(result);
            }
        }
    }

    /**
     *	查询试题数量 1.单选 2.多选
     *
     * @param questionType
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResultDTO selectQuestionCount(HttpServletRequest request,
    									@RequestParam(value = "questionType", required = false) Integer questionType) {
        if (questionType == null) {
            return validationError();
        } else {
        	String remark = userName + "查询试题数量";
    		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
    		String params = "questionType:" + questionType;
    		try {
    			int result = serviceFacade.getQuestionInfoService().selectQuestionCount(questionType);
    			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
    			return success(result);
            } catch (Exception e){
            	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
            	int result = serviceFacade.getQuestionInfoService().selectQuestionCount(questionType);
    			return success(result);
            }
        }
    }

    /**
     *	查询解析、答案
     *
     * @param questionId
     * @return
     */
    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ResultDTO selectAnalysis(HttpServletRequest request,
    								@RequestParam("questionId") Long questionId) {
        if (questionId == null) {
            return validationError();
        } else {
        	String remark = userName + "查询解析、答案";
    		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
    		String params = "questionId:" + questionId;
    		try {
    			List<QuestionInfoWithBLOBs> result = serviceFacade.getQuestionInfoService().selectAnalysis(questionId);
    			if (result == null) {
            		return noData();
            	}
    			serviceFacade.getIUserActionLogServive().insert(1, url, 1, remark, userId, params);
    			return success(result);
            } catch (Exception e){
            	serviceFacade.getIUserActionLogServive().insert(1, url, 0, remark, userId, params);
            	List<QuestionInfoWithBLOBs> result = serviceFacade.getQuestionInfoService().selectAnalysis(questionId);
            	if (result == null) {
            		return noData();
            	}
    			return success(result);
            }
        }
    }

}
