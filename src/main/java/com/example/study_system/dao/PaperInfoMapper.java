package com.example.study_system.dao;

import com.example.study_system.model.PaperInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperInfoMapper {
    //	删除试卷
    int deleteByPrimaryKey(Long paperId);
    
    //	新建试卷
    int insert(String paperName, String cUser);

    int insertSelective(PaperInfo record);

    //	获取试卷详情
    PaperInfo selectByPrimaryKey(Long paperId);

    int updateByPrimaryKeySelective(PaperInfo record);
    //	修改试卷名
    int updateByPrimaryKey(Long paperId , String paperName, String mUser);

    List<PaperInfo> selectPaperInfo(@Param("paperName") String paperName);

    //	查询总条数
    int selectAllPaperInfo(@Param("paperName") String paperName);

    int selectPaperCount();

    Long selectPaperScore(Long paperId);

    //	修改发布
    int updateStatus(Long paperId,@Param("status")int status);
    
	// 查询单选题和多选题数
	PaperInfo selectQuestionInfoSingleAndMany(Long paperId);
}