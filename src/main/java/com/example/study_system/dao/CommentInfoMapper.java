package com.example.study_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.study_system.model.CommentInfo;

@Mapper
public interface CommentInfoMapper {
	int deleteByPrimaryKey(Long ref);

	int insert(CommentInfo record);

	int insertSelective(CommentInfo record);

	CommentInfo selectByPrimaryKey(Long ref);

	int updateByPrimaryKeySelective(CommentInfo record);

	int updateByPrimaryKeyWithBLOBs(CommentInfo record);

	int updateByPrimaryKey(CommentInfo record);
	
	List<CommentInfo> selectCommentByTaskId(Long taskId);
	
	int updateJUserTaskStatus(@Param("userId") String userId , @Param("taskId") Long taskId);
}