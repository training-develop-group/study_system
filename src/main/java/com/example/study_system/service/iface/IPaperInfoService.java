package com.example.study_system.service.iface;

import java.util.List;

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
	
	List<PaperInfo> select();
	
	PaperInfo selectTwo(Long paperId , Integer questionType);
}
