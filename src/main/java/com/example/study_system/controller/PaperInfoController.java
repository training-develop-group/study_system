package com.example.study_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.study_system.common.ResultDTO;
import com.example.study_system.controller.base.BaseController;
import com.example.study_system.model.JPaperQuestion;
import com.example.study_system.model.PaperInfo;

@RestController
@RequestMapping("/paper")
public class PaperInfoController extends BaseController {
// 	新建试卷
	@RequestMapping(value = "/paper", method = RequestMethod.POST)
	public ResultDTO add(@RequestBody PaperInfo paperInfo) {
		int res = serviceFacade.getPaperInfoService().insert(paperInfo);
		return success(res);
	}
// 	修改试卷名
	@RequestMapping(value = "/paper-name", method = RequestMethod.POST)
	public ResultDTO modifyTestPaperName(@RequestBody PaperInfo paperInfo) {
		int res = serviceFacade.getPaperInfoService().modifyTestPaperName(paperInfo);
		return success(res);
	}
	// 	删除试卷
	@RequestMapping(value = "/{paperId}", method = RequestMethod.DELETE)
	public ResultDTO deleteTestPaper (@PathVariable("paperId") Long paperId) {
		int res = serviceFacade.getPaperInfoService().deleteTestPaper(paperId);
		return success(res);
	}
//	获取试卷详情
	@RequestMapping(value = "/{paperId}", method = RequestMethod.GET)
	public ResultDTO detailsOfExaminationPapers (@PathVariable("paperId") Long paperId) {
		PaperInfo paperInfo = serviceFacade.getPaperInfoService().detailsOfExaminationPapers(paperId);
		if (paperInfo == null) {
			return noData();
		}
		return success(paperInfo);
	}
	
	@RequestMapping(value = "/paperId", method = RequestMethod.GET)
	public ResultDTO selectTwo (@RequestParam("paperId") Long paperId,
							@RequestParam("questionType") Integer questionType) {
		PaperInfo paperInfo = serviceFacade.getPaperInfoService().selectTwo(paperId , questionType);
		if (paperInfo == null) {
			return noData();
		}
		return success(paperInfo);
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
}
