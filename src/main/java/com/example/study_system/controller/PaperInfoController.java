package com.example.study_system.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.dto.PaperResultDTO;
import com.example.study_system.model.JPaperQuestion;
import com.example.study_system.model.JUserPaper;
import com.example.study_system.model.JUserQuesAnswerRecord;
import com.example.study_system.model.PaperInfo;
import com.example.study_system.model.PaperQuestionInfo;
import com.example.study_system.model.QuestionInfoWithBLOBs;

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
	public ResultDTO add(@RequestBody PaperInfo paperInfo) {
		int res = serviceFacade.getPaperInfoService().insert(paperInfo);
		return success(res);
	}

	/**
	 * 修改试卷名
	 * 
	 * @param paperInfo
	 * @return
	 */
	@RequestMapping(value = "/paper-name", method = RequestMethod.POST)
	public ResultDTO modifyTestPaperName(@RequestBody PaperInfo paperInfo) {
		int res = serviceFacade.getPaperInfoService().modifyTestPaperName(paperInfo);
		return success(res);
	}

	/**
	 * 删除试卷
	 * 
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = "/{paperId}", method = RequestMethod.DELETE)
	public ResultDTO deleteTestPaper(@PathVariable("paperId") Long paperId) {
		int res = serviceFacade.getPaperInfoService().deleteTestPaper(paperId);
		return success(res);
	}

	/**
	 * 获取试卷详情 不用
	 * 
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = "/{paperId}", method = RequestMethod.GET)
	public ResultDTO detailsOfExaminationPapers(@PathVariable("paperId") Long paperId) {
		if (paperId == null) {
			return validationError();
		}
		return success(serviceFacade.getPaperInfoService().detailsOfExaminationPapers(paperId));
	}

	/**
	 * 获取试卷列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/papers", method = RequestMethod.GET)
	public ResultDTO selectAllPaperInfo() {
		List<PaperQuestionInfo> list = new ArrayList<PaperQuestionInfo>();

		List<PaperInfo> paperInfo = serviceFacade.getPaperInfoService().selectAllPaperInfo();

		for (int i = 0; i < paperInfo.size(); i++) {
			Integer Single = serviceFacade.getPaperInfoService()
					.selectAllQuestionInfoSingle(paperInfo.get(i).getPaperId());
			// 多选题
			Integer Many = serviceFacade.getPaperInfoService().selectAllQuestionInfoMany(paperInfo.get(i).getPaperId());
			PaperQuestionInfo a = new PaperQuestionInfo();
			a.setMany(Many);
			a.setSingle(Single);
			a.setPaperId(paperInfo.get(i).getPaperId());
			a.setPaperName(paperInfo.get(i).getPaperName());
			a.setQuestionId(paperInfo.get(i).getQuestionId());
			a.setQuestionType(paperInfo.get(i).getQuestionType());
			a.setStatus(paperInfo.get(i).getStatus());
			list.add(a);
		}
		return success(list);
	}

	/**
	 * 添加试题到试卷
	 * 
	 * @param JPaperQuestion
	 * @return
	 */
	@RequestMapping(value = "/question", method = RequestMethod.POST)
	public ResultDTO insertJPQ(@RequestBody JPaperQuestion JPaperQuestion) {
		int res = serviceFacade.getPaperInfoService().insertJPQ(JPaperQuestion);
		return success(res);
	}

//	@RequestMapping(value = "/question", method = RequestMethod.POST)
//	public ResultDTO insertModelInfo(PaperInfo record) {
//		int result = serviceFacade.getPaperInfoService().insert(record);
//
//		List<PaperInfo> paperInfo = serviceFacade.getPaperInfoService().selectAllPaperInfo();
//
//		paperInfo.forEach(a -> {
//			serviceFacade.getJPaperQuestionService()
//					.insertSelective(new JPaperQuestion(a.getPaperId(), record.getModelId(), "00000000000000000000"));
//		});
//		return success(0);
//	}
	@RequestMapping(value = "/answers", method = RequestMethod.POST)
	public ResultDTO answer(@RequestParam("jUserPaper") String jUserPaper,
			@RequestParam("jUserQuesAnswerRecord") String jUserQuesAnswerRecord) {
		if (jUserPaper == null || jUserQuesAnswerRecord == null) {
			return validationError();
		} else {
			JUserPaper jUserPaperInfo = JSON.parseObject(jUserPaper, JUserPaper.class);
			List<JUserQuesAnswerRecord> jUserQuesAnswerRecordInfo = JSON.parseArray(jUserQuesAnswerRecord,
					JUserQuesAnswerRecord.class);
			float result = serviceFacade.getPaperInfoService().answer(jUserPaperInfo, jUserQuesAnswerRecordInfo);
			return success(result);
		}
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResultDTO count() {
		return success(serviceFacade.getPaperInfoService().selectPaperCount());
	}

	@RequestMapping(value = "/question/score", method = RequestMethod.POST)
	public ResultDTO score(@RequestParam("score") Float score, @RequestParam("questionId") Long questionId) {
		if (score == null || questionId == null) {
			return validationError();
		}
		return success(serviceFacade.getQuestionInfoService().updateQuestionScore(score, questionId));
	}

}