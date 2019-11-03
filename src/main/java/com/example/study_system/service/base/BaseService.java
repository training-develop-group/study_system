package com.example.study_system.service.base;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import com.example.study_system.dao.JPaperQuestionMapper;
import com.example.study_system.dao.PaperInfoMapper;
=======
>>>>>>> remotes/origin/dev-wtq
=======
>>>>>>> remotes/origin/dev-wtq
import com.example.study_system.dao.QuestionInfoMapper;
=======
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
>>>>>>> dev-wtq
import com.example.study_system.dao.UserInfoMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author lindan. date 2019/10/10.
 */
public class BaseService {
	protected Logger logger = LoggerFactory.getLogger(getClass());

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    @Autowired
    protected UserInfoMapper userInfoMapper;
    
    @Autowired
    protected PaperInfoMapper paperInfoMapper;
    
    @Autowired
    protected JPaperQuestionMapper jPaperQuestionMapper;
    
    @Autowired
    protected QuestionInfoMapper questionInfoMapper;
=======
=======
>>>>>>> remotes/origin/dev-wtq
=======
>>>>>>> dev-wtq
	@Autowired
	protected UserInfoMapper userInfoMapper;
	@Autowired
	protected QuestionInfoMapper questionInfoMapper;
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> remotes/origin/dev-wtq
=======
>>>>>>> remotes/origin/dev-wtq
=======
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
>>>>>>> dev-wtq
}
