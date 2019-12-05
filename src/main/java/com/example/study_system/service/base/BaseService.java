package com.example.study_system.service.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.study_system.dao.CommentInfoMapper;
import com.example.study_system.dao.JPaperQuestionMapper;
import com.example.study_system.dao.JQuestionOptionMapper;
import com.example.study_system.dao.JUserPaperMapper;
import com.example.study_system.dao.JUserQuesAnswerRecordMapper;
import com.example.study_system.dao.JUserTaskMapper;
import com.example.study_system.dao.JUserVideoLogMapper;
import com.example.study_system.dao.PaperInfoMapper;
import com.example.study_system.dao.QuestionInfoMapper;
import com.example.study_system.dao.ResourceInfoMapper;
import com.example.study_system.dao.TaskInfoMapper;
import com.example.study_system.dao.UserActionLogMapper;
import com.example.study_system.dao.UserInfoMapper;
import com.example.study_system.model.ServerConfig;

/**
 * author lindan. date 2019/10/10.
 */
public class BaseService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected JQuestionOptionMapper jQuestionOptionMapper;

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

    @Autowired
    protected QuestionInfoMapper questionInfoMapper;

    @Autowired
    protected TaskInfoMapper taskInfoMapper;

    @Autowired
    protected JUserTaskMapper jUserTaskMapper;
    
    @Autowired
    protected UserActionLogMapper userActionLogMapper;

    @Autowired
    protected JUserQuesAnswerRecordMapper jUserQuesAnswerRecordMapper;
    
    @Autowired
    protected ServerConfig serverConfig;
}
    
  

  


	

