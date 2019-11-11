package com.example.study_system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.study_system.dao.JUserPaperMapper;
import com.example.study_system.model.CommentInfo;
import com.example.study_system.model.JUserPaper;
import com.example.study_system.model.JUserTask;
import com.example.study_system.model.TaskInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.ICommentInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CommentInfoServiceImpl extends BaseService implements ICommentInfoService {
	/**
	 * 获取评论列表
	 */
	@Override
	public  PageInfo<CommentInfo> selectCommentByTaskId(Long taskId,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<CommentInfo> comment = commentInfoMapper.selectCommentByTaskId(taskId);
		PageInfo<CommentInfo> result =  new PageInfo<>(comment);
		return result;
	}
	/**
	 * 添加评论
	 */
	@Override
	public int insertSelective(CommentInfo record) {
		record.setcTime(new Date());
		int flag = commentInfoMapper.insertSelective(record);
		JUserTask jUserPaper = jUserTaskMapper.selectByTaskIdAndUserId(record.getCommentUserId(), record.getTaskId());
		if(flag==1) {
			if(jUserPaper.getStatus()!=1) {
				commentInfoMapper.updateJUserTaskStatus(record.getCommentUserId(), record.getTaskId());
			}
		}
		
		return flag;
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
