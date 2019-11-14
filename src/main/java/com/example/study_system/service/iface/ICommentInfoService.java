package com.example.study_system.service.iface;

import com.example.study_system.model.CommentInfo;
import com.github.pagehelper.PageInfo;

public interface ICommentInfoService {
    PageInfo<CommentInfo> selectCommentByTaskId(Long taskId, int pageNum, int pageSize);

    int insertSelective(CommentInfo record);

    int updateByPrimaryKeySelective(CommentInfo record);
}
