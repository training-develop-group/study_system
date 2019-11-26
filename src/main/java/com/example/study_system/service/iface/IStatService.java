package com.example.study_system.service.iface;

import com.example.study_system.dto.UserTaskDTO;
import com.example.study_system.model.JUserQuesAnswerRecord;
import com.example.study_system.model.JUserTask;
import com.example.study_system.model.TaskInfo;
import com.github.pagehelper.PageInfo;

public interface IStatService {

    TaskInfo selectStat();

    JUserTask selectJUsePaperPercentage();

    TaskInfo selectTaskInfo(String userId);

    PageInfo<UserTaskDTO> statisticalList(String userName, Integer pageNum, Integer pageSize);

    JUserQuesAnswerRecord choiceJUserQuesAnswerRecord();
}
