package com.example.study_system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.study_system.model.CommentInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.ICommentInfoService;

@Service
public class CommentInfoServiceImpl extends BaseService implements ICommentInfoService {
	@Override
	public List<CommentInfo> selectCommentByTaskId(Long taskId) {
		return commentInfoMapper.selectCommentByTaskId(taskId);
	}

	@Override
	public int insertSelective(CommentInfo record) {
		return commentInfoMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(CommentInfo record) {
		return commentInfoMapper.updateByPrimaryKeySelective(record);
	}
}
