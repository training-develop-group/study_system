package com.example.study_system.service.iface;

import com.example.study_system.model.JPaperQuestion;

public interface IJPaperQuestionService {
    int insertSelective(JPaperQuestion record);

    int insert(JPaperQuestion record);
}
