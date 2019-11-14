package com.example.study_system.dao;

import com.example.study_system.model.JUserPaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JUserPaperMapper {
    int deleteByPrimaryKey(Long ref);

    int insert(JUserPaper record);

    int insertSelective(JUserPaper record);

    JUserPaper selectByPrimaryKey(Long ref);

    int updateByPrimaryKeySelective(JUserPaper record);

    int updateByPrimaryKey(JUserPaper record);

    int updateScore(Float score, String userId, Long paperId);

    JUserPaper selectByUserIdAndTaskId(@Param("userId") String userId, @Param("taskId") Long taskId);
}