package com.example.study_system.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.study_system.model.QuestionInfo;
import com.example.study_system.model.QuestionInfoWithBLOBs;
@Mapper
public interface QuestionInfoMapper {
    int deleteByPrimaryKey(Long questionId);

    int insert(QuestionInfoWithBLOBs record);

    int insertSelective(QuestionInfoWithBLOBs record);

    QuestionInfoWithBLOBs selectByPrimaryKey(Long questionId);

    int updateByPrimaryKeySelective(QuestionInfoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(QuestionInfoWithBLOBs record);

    int updateByPrimaryKey(QuestionInfo record);
}