package com.example.study_system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.dto.PaperQuestionResultDTO;
import com.example.study_system.dto.PaperResultDTO;
import com.example.study_system.dto.QuestionResultDTO;
import com.example.study_system.model.JPaperQuestion;
import com.example.study_system.model.JUserPaper;
import com.example.study_system.model.JUserTaskQuestionsInfoMapper;
import com.example.study_system.model.UserActionLog;
import com.example.study_system.model.UserInfo;
import com.example.study_system.util.IpUtil;
import com.example.study_system.util.UserUtil;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/paper")
public class PaperInfoController extends BaseController {
	String userId = "";
	String userName = "";
	/**
	 *	新建试卷
	 *
	 * @param paperInfo
	 * @return
	 */
	@RequestMapping(value = "/paper", method = RequestMethod.POST)
	public ResultDTO add(HttpServletRequest request, @RequestParam(value = "paperName") String paperName) {
		if (StringUtils.isEmpty(paperName)) {
			return noData();
		}
		String remark = userName + "新建试卷";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "paperName:" + paperName;
		try {
			int insertPaper = serviceFacade.getPaperInfoService().insert(request, paperName);
			int status = 1;
			serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
			return success(insertPaper);
        } catch (Exception e){
        	int status = 0;
        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
        	int insertPaper = serviceFacade.getPaperInfoService().insert(request, paperName);
			return success(insertPaper);
        }
	}
	 /**
     * 查询用户答题情况
     * 
     * @param request
     * @param taskId
     * @param paperId
     * @return
     */
    @RequestMapping(value = "/question-answer", method = RequestMethod.GET)
    public ResultDTO UserQuestionAnswer(HttpServletRequest request,@RequestParam("taskId")Long taskId,@RequestParam("paperId")Long paperId) {
        UserInfo userInfo = UserUtil.getUser(request);
        String remark = userName + "查询用户答题情况";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "taskId:" + taskId + "paperId:" + paperId;
		try {
			PaperResultDTO paper = serviceFacade.getPaperInfoService().UserQuestionAnswer(taskId,paperId,userInfo.getUserId());
			int status = 1;
			serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
			return success(paper);
        } catch (Exception e){
        	int status = 0;
        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
        	PaperResultDTO paper = serviceFacade.getPaperInfoService().UserQuestionAnswer(taskId,paperId,userInfo.getUserId());
        	return success(paper);
        }
    }
	/**
	 *	修改试卷名
	 *
	 * @param paperInfo
	 * @return
	 */
	@RequestMapping(value = "/paper-name", method = RequestMethod.POST)
	public ResultDTO modifyTestPaperName(HttpServletRequest request, 
										@RequestParam(value = "paperId") Long paperId,
										@RequestParam(value = "paperName") String paperName) {
		if (paperName == null || paperId == null) {
			return noData();
		}
		UserInfo userInfo = UserUtil.getUser(request);
        String remark = userName + "修改试卷名";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "paperId:" + paperId + "paperName:" + paperName;
		try {
			int updatePaperName = serviceFacade.getPaperInfoService().modifyTestPaperName(request , paperId , paperName);
			int status = 1;
			serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
			return success(updatePaperName);
        } catch (Exception e){
        	int status = 0;
        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
        	int updatePaperName = serviceFacade.getPaperInfoService().modifyTestPaperName(request , paperId , paperName);
        	return success(updatePaperName);
        }
	}

	/**
	 *	获取试卷列表
	 * 
	 * @return
	 */
	
	@RequestMapping(value = "/papers", method = RequestMethod.GET)
	public ResultDTO<PageInfo<PaperResultDTO>> selectPaperInfos(HttpServletRequest request,
			@RequestParam(value = "pageNum") Integer pageNum,
			@RequestParam(value = "pageSize") Integer pageSize,
			@RequestParam(value = "paperName", required = false) String paperName) {
		userId = UserUtil.getUser(request).getUserId();
		userName = UserUtil.getUser(request).getUserName();
		if (pageNum == null || pageSize == null) {
			return validationError();
		}
		String remark = userName + "获取试卷列表";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "pageNum:" + pageNum + "pageSize:" + pageSize + "paperName:" + paperName;
		try {
			PageInfo<PaperResultDTO> paperList = serviceFacade.getPaperInfoService().selectPaperInfos(pageNum, pageSize, paperName);
			if (paperList == null) {
				return noData();
			}
			int status = 1;
			serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
			return success(paperList);
        } catch (Exception e){
        	int status = 0;
        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
        	PageInfo<PaperResultDTO> paperList = serviceFacade.getPaperInfoService().selectPaperInfos(pageNum, pageSize, paperName);
        	if (paperList == null) {
    			return noData();
    		}
        	return success(paperList);
        }
	}

	/**
	 *	删除试卷
	 *
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = "/{paperId}", method = RequestMethod.DELETE)
	public ResultDTO deleteTestPaper(HttpServletRequest request,@PathVariable("paperId") Long paperId) {
		if (paperId == null) {
			return validationError();
		}
		String remark = userName + "删除试卷";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "paperId:" + paperId;
		try {
			int deletePaperById = serviceFacade.getPaperInfoService().deleteTestPaper(paperId);
			int status = 1;
			serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
			return success(deletePaperById);
        } catch (Exception e){
        	int status = 0;
        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
        	int deletePaperById = serviceFacade.getPaperInfoService().deleteTestPaper(paperId);
        	return success(deletePaperById);
        }
	}

	/**
	 *	获取试卷详情
	 *
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = "/{paperId}", method = RequestMethod.GET)
	public ResultDTO detailsOfExaminationPapers(HttpServletRequest request,@PathVariable("paperId") Long paperId) {
		if (paperId == null) {
			return validationError();
		}
		String remark = userName + "获取试卷详情";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "paperId:" + paperId;
		try {
			PaperResultDTO PaperParticulars = serviceFacade.getPaperInfoService().detailsOfExaminationPapers(paperId);
			if (PaperParticulars == null) {
				return validationError();
			}
			int status = 1;
			serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
			return success(PaperParticulars);
        } catch (Exception e){
        	int status = 0;
        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
        	PaperResultDTO PaperParticulars = serviceFacade.getPaperInfoService().detailsOfExaminationPapers(paperId);
    		if (PaperParticulars == null) {
    			return validationError();
    		}
    		return success(PaperParticulars);
        }
	}

	/**
	 *	添加试题到试卷
	 *
	 * @param JPaperQuestion
	 * @return
	 */
	@RequestMapping(value = "/question", method = RequestMethod.POST)
	public ResultDTO insertJPQ(HttpServletRequest request,@RequestBody JPaperQuestion JPaperQuestion) {
		if (JPaperQuestion == null) {
			return validationError();
		}
		String remark = userName + "添加试题到试卷";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "JPaperQuestion:" + JPaperQuestion.toString();
		try {
			int insertQuestionArrivePaper = serviceFacade.getPaperInfoService().insertJPQ(JPaperQuestion);
			int status = 1;
			serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
			return success(insertQuestionArrivePaper);
        } catch (Exception e){
        	int status = 0;
        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
        	int insertQuestionArrivePaper = serviceFacade.getPaperInfoService().insertJPQ(JPaperQuestion);
        	return success(insertQuestionArrivePaper);
        }
	}

	/**
	 *	提交试卷答案
	 *
	 * @param jUserPaper
	 * @param jUserQuesAnswerRecord
	 * @return
	 */
	@RequestMapping(value = "/answers", method = RequestMethod.POST)
	public ResultDTO answer(HttpServletRequest request, 
							@RequestParam("paperId") Long paperId,
							@RequestParam("taskId") Long taskId, 
							@RequestParam("jUserQuesAnswerRecord") String jUserQuesAnswerRecord) {
		if (paperId == null || taskId == null || StringUtils.isEmpty(jUserQuesAnswerRecord)) {
			return validationError();
		}
		UserInfo userInfo = UserUtil.getUser(request);
		if (userInfo == null) {
			return noData();
		}
		List<JUserTaskQuestionsInfoMapper> jUserQuesAnswerRecordInfo = JSON.parseArray(jUserQuesAnswerRecord,
				JUserTaskQuestionsInfoMapper.class);
		JUserPaper jUserPaperInfo = new JUserPaper();
		jUserPaperInfo.setUserId(userInfo.getUserId());
		jUserPaperInfo.setPaperId(paperId);
		jUserPaperInfo.setTaskId(taskId);
		String remark = userName + "提交试卷答案";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "userId:" + userInfo.getUserId() + "paperId:" + paperId + "taskId:" + 
						taskId + "jUserQuesAnswerRecord:" + jUserQuesAnswerRecord;
		try {
			PaperResultDTO result = serviceFacade.getPaperInfoService().answer(userInfo.getUserId(), paperId, taskId, jUserQuesAnswerRecordInfo);
			if (result == null) {
				return noData();
			}
			int status = 1;
			serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
			return success(result);
        } catch (Exception e){
        	int status = 0;
        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
        	PaperResultDTO result = serviceFacade.getPaperInfoService().answer(userInfo.getUserId(), paperId, taskId, jUserQuesAnswerRecordInfo);
    		if (result == null) {
    			return noData();
    		}
    		return success(result);
        }
	}

	/**
	 *	获取试卷总数
	 *
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResultDTO count(HttpServletRequest request) {
		String remark = userName + "获取试卷总数";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "";
		try {
			int countNum = serviceFacade.getPaperInfoService().selectPaperCount();
			int status = 1;
			serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
			return success(countNum);
        } catch (Exception e){
        	int status = 0;
        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
        	int countNum = serviceFacade.getPaperInfoService().selectPaperCount();
        	return success(countNum);
        }
	}

	/**
	 *	设定试题分值
	 *
	 * @param score
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value = "/question/score", method = RequestMethod.POST)
	public ResultDTO score(HttpServletRequest request,@RequestParam("paperId") Long paperId, @RequestParam("questionId") Long questionId,
			@RequestParam("score") Float score) {
		if (paperId == null || score == null || questionId == null) {
			return validationError();
		}
		String remark = userName + "设定试题分值";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "paperId:" + paperId + "questionId:" + questionId + "score:" + score;
		try {
			int setGoals = serviceFacade.getPaperInfoService().updateScore(paperId, questionId, score);
			int status = 1;
			serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
			return success(setGoals);
        } catch (Exception e){
        	int status = 0;
        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
        	int setGoals = serviceFacade.getPaperInfoService().updateScore(paperId, questionId, score);
        	return success(setGoals);
        }
	}

	/**
	 *	删除试卷试题
	 *
	 * @param paperId
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value = "/question", method = RequestMethod.DELETE)
	public ResultDTO deletePQRelationship(HttpServletRequest request,
			@RequestParam("paperId") Long paperId,
			@RequestParam("questionId") Long questionId) {
		if (paperId == null || questionId == null) {
			return validationError();
		}
		String remark = userName + "删除试卷试题";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "paperId:" + paperId + "questionId:" + questionId;
		try {
			int deletePaperQuestion = serviceFacade.getPaperInfoService().deletePQRelationship(paperId, questionId);
			int status = 1;
			serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
			return success(deletePaperQuestion);
        } catch (Exception e){
        	int status = 0;
        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
        	int deletePaperQuestion = serviceFacade.getPaperInfoService().deletePQRelationship(paperId, questionId);
        	return success(deletePaperQuestion);
        }
	}

	/**
	 *	获取试卷成绩
	 *
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = "/score", method = RequestMethod.GET)
	public ResultDTO selectPaperScore(HttpServletRequest request,@RequestParam("paperId") Long paperId) {
		if (paperId == null) {
			return validationError();
		}
		String remark = userName + "获取试卷成绩";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "paperId:" + paperId;
		try {
			Long paperScore = serviceFacade.getPaperInfoService().selectPaperScore(paperId);
			if (paperScore == null) {
				return noData();
			}
			int status = 1;
			serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
			return success(paperScore);
        } catch (Exception e){
        	int status = 0;
        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
        	Long paperScore = serviceFacade.getPaperInfoService().selectPaperScore(paperId);
    		if (paperScore == null) {
    			return noData();
    		}
        	return success(paperScore);
        }
	}

	/**
	 *	修改试卷
	 *
	 * @param JPaperQuestion
	 * @param PaperQuestionPesult
	 * @param questionScore
	 * @return
	 */
	@RequestMapping(value = "/paper/paper", method = RequestMethod.POST)
	public ResultDTO modelPaperInfo(HttpServletRequest request,
			@RequestParam("JPaperQuestion") String JPaperQuestion,
			@RequestParam("PaperQuestionPesult") String PaperQuestionPesult,
			@RequestParam("questionScore") String questionScore, 
			@RequestParam("sorting") String sorting) {
		List<JPaperQuestion> jPaperQuestionList = JSON.parseArray(JPaperQuestion, JPaperQuestion.class);
		List<PaperQuestionResultDTO> paperQuestionPesultList = JSON.parseArray(PaperQuestionPesult,
				PaperQuestionResultDTO.class);
		List<PaperQuestionResultDTO> questionScoreList = JSON.parseArray(questionScore, PaperQuestionResultDTO.class);
		List<JPaperQuestion> sortIng = JSON.parseArray(sorting, JPaperQuestion.class);
		String remark = userName + "修改试卷";
		String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
		String params = "JPaperQuestion:" + JPaperQuestion + "PaperQuestionPesult:" + PaperQuestionPesult
						+ "questionScore:" + questionScore + "sorting:" + sorting;
		try {
			int updatePaper = serviceFacade.getPaperInfoService().addOrRemoveRelationships(jPaperQuestionList,
					paperQuestionPesultList, questionScoreList, sortIng);
			int status = 1;
			serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
			return success(updatePaper);
        } catch (Exception e){
        	int status = 0;
        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
        	int updatePaper = serviceFacade.getPaperInfoService().addOrRemoveRelationships(jPaperQuestionList,
    				paperQuestionPesultList, questionScoreList, sortIng);
        	return success(updatePaper);
        }
	}
	
	/**
	 *	查询部分试题
	 * @param questionIdList
	 * @return
	 */
	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public ResultDTO someQuestions(HttpServletRequest request,@RequestParam("questionIdList") String questionIdList) {
		if (questionIdList == null) {
			return validationError();
		} else {
			List<Long> questionIdsList = JSON.parseArray(questionIdList, Long.class);
			String remark = userName + "查询部分试题";
			String url = serviceFacade.getPaperInfoService().getUrl() + request.getRequestURI();
			String params = "questionIdList:" + questionIdList;
			try {
				List<QuestionResultDTO> result = serviceFacade.getPaperInfoService().someQuestions(questionIdsList);
				int status = 1;
				serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
				return success(result);
	        } catch (Exception e){
	        	int status = 0;
	        	serviceFacade.getIUserActionLogServive().insert(1, url, status, remark, userId, params);
	        	List<QuestionResultDTO> result = serviceFacade.getPaperInfoService().someQuestions(questionIdsList);
	        	return success(result);
	        }
		}
	}
}
