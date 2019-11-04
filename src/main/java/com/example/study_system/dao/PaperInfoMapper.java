package com.example.study_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.study_system.model.PaperInfo;

public interface PaperInfoMapper {
//	删除试卷
	int deleteByPrimaryKey(Long paperId);

	int insert(String paperName , String cUser);

//	新建试卷
	int insertSelective(PaperInfo record);

//	获取试卷详情
	PaperInfo selectByPrimaryKey(Long paperId);

//	修改试卷名
	int updateByPrimaryKeySelective(PaperInfo record);

	int updateByPrimaryKey(PaperInfo record);

	List<PaperInfo> selectPaperInfo(@Param("paperName")String paperName);
//	查询总条数
	int selectAllPaperInfo(@Param("paperName")String paperName);

	List<PaperInfo> select();

	int selectAllQuestionInfoSingle(Long paperId);

	int selectAllQuestionInfoMany(Long paperId);

	int selectPaperCount();
	
	Long selectPaperScore(Long paperId);

}