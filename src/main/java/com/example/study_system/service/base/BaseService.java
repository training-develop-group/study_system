package com.example.study_system.service.base;

<<<<<<< HEAD
<<<<<<< HEAD
import com.example.study_system.dao.JPaperQuestionMapper;
import com.example.study_system.dao.PaperInfoMapper;
=======
>>>>>>> remotes/origin/dev-wtq
=======
>>>>>>> remotes/origin/dev-wtq
import com.example.study_system.dao.QuestionInfoMapper;
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
	@Autowired
	protected UserInfoMapper userInfoMapper;
	@Autowired
	protected QuestionInfoMapper questionInfoMapper;
<<<<<<< HEAD
>>>>>>> remotes/origin/dev-wtq
=======
>>>>>>> remotes/origin/dev-wtq
}
