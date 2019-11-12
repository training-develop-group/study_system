package com.example.study_system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.dto.PaperQuestionPesultDTO;
import com.example.study_system.dto.PaperResultDTO;
import com.example.study_system.model.JPaperQuestion;
import com.example.study_system.model.JUserPaper;
import com.example.study_system.model.JUserQuesAnswerRecord;
import com.example.study_system.model.JUserTaskQuestionsInfoMapper;
import com.example.study_system.model.PaperInfo;
import com.example.study_system.model.UserInfo;
import com.example.study_system.util.UserUtil;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/paper")
public class PaperInfoController extends BaseController {
	/**
	 * 新建试卷
	 * 
	 * @param paperInfo
	 * @return
	 */
	@RequestMapping(value = "/paper", method = RequestMethod.POST)
	public ResultDTO add(HttpServletRequest request , @RequestParam(value = "paperName")String paperName) {
		if (paperName == null) {
			return noData();
		}
		
		
		int insertPaper = serviceFacade.getPaperInfoService().insert(request,paperName);
		return success(insertPaper);
	}

	/**
	 * 修改试卷名
	 * 
	 * @param paperInfo
	 * @return
	 */
	@RequestMapping(value = "/paper-name", method = RequestMethod.POST)
	public ResultDTO modifyTestPaperName(@RequestBody PaperInfo paperInfo) {
		if (paperInfo == null) {
			return noData();
		}
		int updatePaperName = serviceFacade.getPaperInfoService().modifyTestPaperName(paperInfo);
		return success(updatePaperName);
	}

	/**
	 * 删除试卷
	 * 
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = "/{paperId}", method = RequestMethod.DELETE)
	public ResultDTO deleteTestPaper(@PathVariable("paperId") Long paperId) {
		if (paperId == null) {
			return noData();
		}
		int deletePaperById = serviceFacade.getPaperInfoService().deleteTestPaper(paperId);
		return success(deletePaperById);
	}

	/**
	 * 获取试卷详情
	 * 
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = "/{paperId}", method = RequestMethod.GET)
	public ResultDTO detailsOfExaminationPapers(@PathVariable("paperId") Long paperId) {
		if (paperId == null) {
			return noData();
		}
		PaperResultDTO PaperParticulars = serviceFacade.getPaperInfoService().detailsOfExaminationPapers(paperId);
		return success(PaperParticulars);
	}

	/**
	 * 获取试卷列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/papers", method = RequestMethod.GET)
	public ResultDTO<PageInfo<PaperResultDTO>> selectPaperInfos(@RequestParam(value = "pageNum")Integer pageNum , 
			@RequestParam(value = "pageSize")Integer pageSize , 
			@RequestParam(value = "paperName" , required = false)String paperName) {
		PageInfo<PaperResultDTO> paperList = serviceFacade.getPaperInfoService().selectPaperInfos(pageNum, pageSize , paperName);
		return success(paperList);
	}

	/**
	 * 添加试题到试卷
	 * 
	 * @param JPaperQuestion
	 * @return
	 */
	@RequestMapping(value = "/question", method = RequestMethod.POST)
	public ResultDTO insertJPQ(@RequestBody JPaperQuestion JPaperQuestion) {
		if (JPaperQuestion == null) {
			return noData();
		}
		int insertQuestionArrivePaper = serviceFacade.getPaperInfoService().insertJPQ(JPaperQuestion);
		return success(insertQuestionArrivePaper);
	}

	/**
	 * 提交试卷答案
	 * @param jUserPaper
	 * @param jUserQuesAnswerRecord
	 * @return
	 */
	@RequestMapping(value = "/answers", method = RequestMethod.POST)
	public ResultDTO answer(HttpServletRequest request,
						@RequestParam("paperId") Long paperId,
						@RequestParam("taskId") Long taskId,
			@RequestParam("jUserQuesAnswerRecord") String jUserQuesAnswerRecord) {
		UserInfo userInfo = UserUtil.getUser(request);
			List<JUserTaskQuestionsInfoMapper> jUserQuesAnswerRecordInfo = JSON.parseArray(jUserQuesAnswerRecord,
					JUserTaskQuestionsInfoMapper.class);
			JUserPaper jUserPaperInfo = new JUserPaper();
			jUserPaperInfo.setUserId(userInfo.getUserId());
			jUserPaperInfo.setPaperId(paperId);
			jUserPaperInfo.setTaskId(taskId);
			PaperResultDTO result = serviceFacade.getPaperInfoService().answer(userInfo.getUserId(),paperId,taskId, jUserQuesAnswerRecordInfo);
			return success(result);
//		}
	}
	
	/**
	 * 获取试卷总数
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResultDTO count() {
		return success(serviceFacade.getPaperInfoService().selectPaperCount());
	}
	
	/**
	 * 设定试题分值
	 * @param score
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value = "/question/score", method = RequestMethod.POST)
	public ResultDTO score(@RequestParam("paperId") Long paperId , @RequestParam("questionId") Long questionId , @RequestParam("score") Float score) {
		if (paperId == null || score == null || questionId == null) {
			return validationError();
		}
		int setGoals = serviceFacade.getPaperInfoService().updateScore(paperId, questionId , score);
		return success(setGoals);
	}
	
	/**
	 * 删除试卷试题
	 * @param paperId
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value = "/question", method = RequestMethod.DELETE)
	public ResultDTO deletePQRelationship(@RequestParam("paperId") Long paperId, 
										@RequestParam("questionId") Long questionId) {
		System.out.println(paperId);
		int deletePaperQuestion = serviceFacade.getPaperInfoService().deletePQRelationship(paperId , questionId);
		return success(deletePaperQuestion);
	}
	
	/**
	 * 获取试卷成绩
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = "/score", method = RequestMethod.GET)
	public ResultDTO selectPaperScore(@RequestParam("paperId") Long paperId) {
		Long paperScore = serviceFacade.getPaperInfoService().selectPaperScore(paperId);
		return success(paperScore);
	}
	
	/**
	 * 修改试卷
	 * @param JPaperQuestion
	 * @param PaperQuestionPesult
	 * @param questionScore
	 * @return
	 */
	@RequestMapping(value = "/paper/paper", method = RequestMethod.POST)
	public ResultDTO modelPaperInfo(@RequestParam("JPaperQuestion") String JPaperQuestion,
									@RequestParam("PaperQuestionPesult") String PaperQuestionPesult,
									@RequestParam("questionScore") String questionScore,
									@RequestParam("sorting") String sorting) {
		
		List<JPaperQuestion> jPaperQuestionList = JSON.parseArray(JPaperQuestion, JPaperQuestion.class);
		List<PaperQuestionPesultDTO> paperQuestionPesultList = JSON.parseArray(PaperQuestionPesult, PaperQuestionPesultDTO.class);
		List<PaperQuestionPesultDTO> questionScoreList = JSON.parseArray(questionScore, PaperQuestionPesultDTO.class);
		List<JPaperQuestion> sortIng = JSON.parseArray(sorting, JPaperQuestion.class);
		int updatePaper = serviceFacade.getPaperInfoService().addOrRemoveRelationships(jPaperQuestionList , paperQuestionPesultList , questionScoreList , sortIng);
		return success(updatePaper);
	}
}
