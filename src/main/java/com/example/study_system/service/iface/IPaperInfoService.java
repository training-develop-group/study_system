package com.example.study_system.service.iface;

import java.util.List;

import com.example.study_system.dto.PaperQuestionPesultDTO;
import com.example.study_system.dto.PaperResultDTO;
import com.example.study_system.model.JPaperQuestion;
import com.example.study_system.model.JUserPaper;
import com.example.study_system.model.JUserQuesAnswerRecord;
import com.example.study_system.model.JUserTaskQuestionsInfoMapper;
import com.example.study_system.model.PaperInfo;
import com.github.pagehelper.PageInfo;

public interface IPaperInfoService {
//	新建试卷
	int insert(String paperName);

//	修改试卷名
	int modifyTestPaperName(PaperInfo record);

//	删除试卷
	int deleteTestPaper(Long paperId);

//	获取试卷详情
	PaperResultDTO detailsOfExaminationPapers(Long paperId);

	PageInfo<PaperResultDTO> selectPaperInfos(Integer pageNum, Integer pageSize , String paperName);

	int selectQuestionInfoSingles(Long paperId);

	int selectQuestionInfoManys(Long paperId);

	int insertJPQ(JPaperQuestion record);

	PaperResultDTO answer(String userId, Long paperId, Long taskId, List<JUserTaskQuestionsInfoMapper> jUserQuesAnswerRecordInfo);
	
	int selectPaperCount();
	
	int deletePQRelationship(Long paperId , Long questionId);
	
	Long selectPaperScore(Long paperId);

	int addOrRemoveRelationships(List<JPaperQuestion> JPaperQuestionList, List<PaperQuestionPesultDTO> PaperQuestionPesultList, List<PaperQuestionPesultDTO> questionScore , List<JPaperQuestion> sorting);
//	设置试题分数
	int updateScore(Long paperId , Long questionId , Float score);

	
}