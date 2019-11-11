package com.example.study_system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.study_system.model.CommentInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.ICommentInfoService;

@Service
public class CommentInfoServiceImpl extends BaseService implements ICommentInfoService {
	/**
	 * 获取评论列表
	 */
	@Override
	public List<CommentInfo> selectCommentByTaskId(Long taskId) {
		return commentInfoMapper.selectCommentByTaskId(taskId);
	}
	/**
	 * 添加评论
	 */
	@Override
	public int insertSelective(CommentInfo record) {
		record.setcTime(new Date());
		return commentInfoMapper.insertSelective(record);
	}
	/**
	 * 修改评论
	 */
	@Override
	public int updateByPrimaryKeySelective(CommentInfo record) {
		record.setcTime(new Date());
		return commentInfoMapper.updateByPrimaryKeySelective(record);
	}
}
