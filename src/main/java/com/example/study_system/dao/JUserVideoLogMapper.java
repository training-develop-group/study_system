package com.example.study_system.dao;

import java.util.List;

import com.example.study_system.model.JUserVideoLog;

public interface JUserVideoLogMapper {
    int deleteByPrimaryKey(Long ref);

    int insert(JUserVideoLog record);

    int insertSelective(JUserVideoLog record);

    JUserVideoLog selectByPrimaryKey(Long ref);

    int updateByPrimaryKeySelective(JUserVideoLog record);

    int updateByPrimaryKey(JUserVideoLog record);

	long insertVideoPlaybackTime(Long resId, Long seconds);

	List<JUserVideoLog> selectByResId(Long resId);
}