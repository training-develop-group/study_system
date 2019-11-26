package com.example.study_system.dao;

import com.example.study_system.model.JUserQuesAnswerRecord;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JUserQuesAnswerRecordMapper {
    int deleteByPrimaryKey(Long ref);

    int insert(JUserQuesAnswerRecord record);

    int insertSelective(JUserQuesAnswerRecord record);

    JUserQuesAnswerRecord selectByPrimaryKey(Long ref);

    int updateByPrimaryKeySelective(JUserQuesAnswerRecord record);

    int updateByPrimaryKeyWithBLOBs(JUserQuesAnswerRecord record);

    int updateByPrimaryKey(JUserQuesAnswerRecord record);

    Float selectJUserQuesAnswerRecord(String userId);
    
    List<JUserQuesAnswerRecord> selectUserQuestionAnswer(@Param("taskId")Long taskId,@Param("paperId")Long paperId,@Param("userId")String usetId,@Param("questionId")Long questionId);
    
    JUserQuesAnswerRecord choiceJUserQuesAnswerRecord();
}