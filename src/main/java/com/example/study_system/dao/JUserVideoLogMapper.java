package com.example.study_system.dao;

import com.example.study_system.model.JUserVideoLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JUserVideoLogMapper {
    int deleteByPrimaryKey(Long ref);

    int insert(JUserVideoLog record);

    int insertSelective(JUserVideoLog record);

    Long selectByPrimaryKey(Long ref);

    int updateByPrimaryKeySelective(JUserVideoLog record);

    int updateByPrimaryKey(JUserVideoLog record);

    long insertVideoPlaybackTime(Long resId, Long seconds);

    Long selectByResId(Long resId);
}