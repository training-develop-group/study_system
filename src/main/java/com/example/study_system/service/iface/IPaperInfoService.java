<<<<<<< HEAD
package com.example.study_system.service.iface;

import java.util.List;

import com.example.study_system.model.JPaperQuestion;
import com.example.study_system.model.JUserPaper;
import com.example.study_system.model.JUserQuesAnswerRecord;
import com.example.study_system.model.PaperInfo;

public interface IPaperInfoService {
//	新建试卷
	int insert(PaperInfo record);

//	修改试卷名
	int modifyTestPaperName(PaperInfo record);

//	删除试卷
	int deleteTestPaper(Long paperId);

//	获取试卷详情
	PaperInfo detailsOfExaminationPapers(Long paperId);

	List<PaperInfo> selectAllPaperInfo();

	int selectAllQuestionInfoSingle(Long paperId);

	int selectAllQuestionInfoMany(Long paperId);

	List<PaperInfo> select();

	int insertJPQ(JPaperQuestion record);

	float answer(JUserPaper jUserPaperInfo, List<JUserQuesAnswerRecord> jUserQuesAnswerRecordInfo);

	int selectPaperCount();
}
=======
package com.example.study_system.service.iface;

import java.util.List;

import com.example.study_system.dto.PaperQuestionPesultDTO;
import com.example.study_system.dto.PaperResultDTO;
import com.example.study_system.model.JPaperQuestion;
import com.example.study_system.model.JUserPaper;
import com.example.study_system.model.JUserQuesAnswerRecord;
import com.example.study_system.model.PaperInfo;
import com.github.pagehelper.PageInfo;

public interface IPaperInfoService {
//	新建试卷
	int insert(PaperInfo record);

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

	float answer(JUserPaper jUserPaperInfo, List<JUserQuesAnswerRecord> jUserQuesAnswerRecordInfo);
	
	int selectPaperCount();
	
	int deletePQRelationship(Long paperId , Long questionId);
	
	Long selectPaperScore(Long paperId);

	int addOrRemoveRelationships(List<JPaperQuestion> JPaperQuestionList, List<PaperQuestionPesultDTO> PaperQuestionPesultList, List<PaperQuestionPesultDTO> questionScore , List<JPaperQuestion> sorting);
//	设置试题分数
	int updateScore(Long paperId , Long questionId , Float score);
}
>>>>>>> dev-wtq
