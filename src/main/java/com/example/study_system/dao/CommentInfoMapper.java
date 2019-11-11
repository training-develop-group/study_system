<<<<<<< HEAD
package com.example.study_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
=======
package com.example.study_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
>>>>>>> caa8072d35903f32aea5d24014a89c312b1470ab
}