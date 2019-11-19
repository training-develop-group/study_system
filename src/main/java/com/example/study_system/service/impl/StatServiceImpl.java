package com.example.study_system.service.impl;

import com.example.study_system.dto.UserTaskDTO;
import com.example.study_system.model.JUserQuesAnswerRecord;
import com.example.study_system.model.JUserTask;
import com.example.study_system.model.TaskInfo;
import com.example.study_system.model.UserInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IStatService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatServiceImpl extends BaseService implements IStatService {
    //获取任务类型百分比
    @Override
    public TaskInfo selectStat() {
        return taskInfoMapper.typeTaskInfo();
    }

    //获取学生任务完成百分比
    @Override
    public JUserTask selectJUsePaperPercentage() {
        return jUserTaskMapper.selectUserTaskProbability();
    }

    //获取学生作答正确率
    @Override
    public JUserQuesAnswerRecord choiceJUserQuesAnswerRecord() {
        return jUserQuesAnswerRecordMapper.choiceJUserQuesAnswerRecord();
    }

    //获取统计列表
    @Override
    public TaskInfo selectTaskInfo(String cUser) {
        return taskInfoMapper.selectTaskInfo(cUser);
    }

    //使用DTO拼接在一起
    @Override
    @Transactional
    public PageInfo<UserTaskDTO> statisticalList(String userName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserTaskDTO> statisticalList = new ArrayList<UserTaskDTO>();
        List<UserInfo> allUserInfo = userInfoMapper.selectUserByName(userName);
        if (allUserInfo == null) {
        	 return null;
        }
        allUserInfo.forEach(item -> {
            TaskInfo task = taskInfoMapper.selectTaskInfo(item.getUserName());

            Float answerRecord = jUserQuesAnswerRecordMapper.selectJUserQuesAnswerRecord(item.getUserId());
            System.out.println(answerRecord);
            if (answerRecord == null) {
                answerRecord = 0.0f;
            }
            Float percentage = jUserTaskMapper.selectJUsePaper(item.getUserId());
            statisticalList.add(new UserTaskDTO(task.getXx(), task.getCs(), task.getZh(), answerRecord, percentage, item.getUserName()));
        });
        PageInfo<UserTaskDTO> result = new PageInfo<>(statisticalList);
        result.setTotal(userInfoMapper.selectUserByName(userName).size());
        result.setPageNum(pageNum);
        return result;
    }
}
