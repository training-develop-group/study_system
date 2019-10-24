package com.example.study_system.dao;

import java.util.List;

import com.example.study_system.model.PaperInfo;

public interface PaperInfoMapper {
//	删除试卷
    int deleteByPrimaryKey(Long paperId);

    int insert(PaperInfo record);
//	新建试卷
    int insertSelective(PaperInfo record);
//	获取试卷详情
    PaperInfo selectByPrimaryKey(Long paperId);
//	修改试卷名
    int updateByPrimaryKeySelective(PaperInfo record);

    int updateByPrimaryKey(PaperInfo record);
    
    List<PaperInfo> selectAllPaperInfo();
    
    List<PaperInfo> select();
    
    PaperInfo selectTwo(Long paperId , Integer questionType);
}