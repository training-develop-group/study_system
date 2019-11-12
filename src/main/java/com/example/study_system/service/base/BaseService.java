package com.example.study_system.service.base;

import com.example.study_system.dao.CommentInfoMapper;
import com.example.study_system.dao.JPaperQuestionMapper;
import com.example.study_system.dao.JQuestionOptionMapper;
import com.example.study_system.dao.JUserPaperMapper;
import com.example.study_system.dao.JUserQuesAnswerRecordMapper;
import com.example.study_system.dao.JUserTaskInfoMapper;
import com.example.study_system.dao.JUserTaskMapper;
import com.example.study_system.dao.JUserVideoLogMapper;
import com.example.study_system.dao.PaperInfoMapper;
import com.example.study_system.dao.QuestionInfoMapper;
import com.example.study_system.dao.ResourceInfoMapper;
import com.example.study_system.dao.TaskInfoMapper;
import com.example.study_system.dao.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author lindan. date 2019/10/10.
 */
public class BaseService {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected JQuestionOptionMapper jQuestionOptionMapper;
	@Autowired
	protected JUserTaskInfoMapper jUserTaskInfoMapper;
	@Autowired
	protected CommentInfoMapper commentInfoMapper;
	@Autowired
	protected PaperInfoMapper paperInfoMapper;
	@Autowired
	protected JPaperQuestionMapper jPaperQuestionMapper;
	@Autowired
	protected JUserPaperMapper jUserPaperMapper;

	  @Autowired
	    protected ResourceInfoMapper resourceInfoMapper;
	  @Autowired
	    protected JUserVideoLogMapper userVideoLogMapper;
	  
	  @Autowired
		protected UserInfoMapper userInfoMapper;
		
		//获取任务类型百分比
		@Autowired
		protected QuestionInfoMapper questionInfoMapper;
		
		//获取学生任务完成百分比
		@Autowired
		protected TaskInfoMapper taskInfoMapper;
		
		//获取学生作答正确率
		@Autowired
		protected JUserTaskMapper jUserTaskMapper;
		
		//使用DTO拼接在一起
		@Autowired
		protected JUserQuesAnswerRecordMapper jUserQuesAnswerRecordMapper;

}
    
  

  


	

