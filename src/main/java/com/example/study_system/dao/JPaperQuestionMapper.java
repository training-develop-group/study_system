package com.example.study_system.dao;

import com.example.study_system.model.JPaperQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JPaperQuestionMapper {
    int deleteByPrimaryKey(Long ref);

    int delete(Long paperId, Long questionId);

    int insert(JPaperQuestion record);

    int insertSelective(JPaperQuestion record);

    JPaperQuestion selectByPrimaryKey(Long ref);

    int updateByPrimaryKeySelective(JPaperQuestion record);

    int updateByPrimaryKey(JPaperQuestion record);

    List<JPaperQuestion> selectQuestionByPaperId(Long paperId);

    int updateScore(Long paperId, Long questionId, Float score);
    
    int selectQuestionId(Long questionId);
//	删除跟试卷有关系的
    int deleteByPaperId(Long paperId);
}