package com.example.study_system.service.base;

import com.example.study_system.dao.*;
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
    protected JUserQuesAnswerRecordMapper jUserQuesAnswerRecordMapper;

}
    
  

  


	

