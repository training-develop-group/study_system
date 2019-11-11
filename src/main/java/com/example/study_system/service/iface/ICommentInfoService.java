package com.example.study_system.service.iface;

import java.util.List;

import com.example.study_system.model.CommentInfo;
import com.example.study_system.model.TaskInfo;
import com.github.pagehelper.PageInfo;

public interface ICommentInfoService {
	 PageInfo<CommentInfo> selectCommentByTaskId(Long taskId,int pageNum,int pageSize);

	int insertSelective(CommentInfo record);

	int updateByPrimaryKeySelective(CommentInfo record);
}
