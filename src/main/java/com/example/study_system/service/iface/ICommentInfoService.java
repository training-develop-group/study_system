<<<<<<< HEAD
package com.example.study_system.service.iface;

import java.util.List;

import com.example.study_system.model.CommentInfo;

public interface ICommentInfoService {
	List<CommentInfo> selectCommentByTaskId(Long taskId);

	int insertSelective(CommentInfo record);

	int updateByPrimaryKeySelective(CommentInfo record);
}
=======
package com.example.study_system.service.iface;

import java.util.List;

import com.example.study_system.model.CommentInfo;

public interface ICommentInfoService {
	List<CommentInfo> selectCommentByTaskId(Long taskId);

	int insertSelective(CommentInfo record);

	int updateByPrimaryKeySelective(CommentInfo record);
}
>>>>>>> caa8072d35903f32aea5d24014a89c312b1470ab
