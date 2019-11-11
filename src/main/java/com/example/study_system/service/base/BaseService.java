package com.example.study_system.service.base;

import com.example.study_system.dao.CommentInfoMapper;
import com.example.study_system.dao.JPaperQuestionMapper;
import com.example.study_system.dao.JQuestionOptionMapper;
import com.example.study_system.dao.JUserPaperMapper;
import com.example.study_system.dao.JUserQuesAnswerRecordMapper;
import com.example.study_system.dao.JUserTaskInfoMapper;
import com.example.study_system.dao.JUserTaskMapper;
import com.example.study_system.dao.PaperInfoMapper;
import com.example.study_system.dao.QuestionInfoMapper;
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
	protected UserInfoMapper userInfoMapper;
	@Autowired
	protected QuestionInfoMapper questionInfoMapper;
	@Autowired
	protected JQuestionOptionMapper jQuestionOptionMapper;
	@Autowired
	protected TaskInfoMapper taskInfoMapper;
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
	protected JUserQuesAnswerRecordMapper jUserQuesAnswerRecordMapper;
	@Autowired
	protected JUserTaskMapper jUserTaskMapper;
}
