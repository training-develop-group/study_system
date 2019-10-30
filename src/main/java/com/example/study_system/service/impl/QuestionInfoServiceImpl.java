<<<<<<< HEAD
<<<<<<< HEAD
package com.example.study_system.service.impl;

import org.springframework.stereotype.Service;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IQuestionInfoService;

@Service
public class QuestionInfoServiceImpl  extends BaseService implements IQuestionInfoService{
	@Override
	public int insertSelective(QuestionInfoWithBLOBs record) {
		return questionInfoMapper.insertSelective(record);
    }
}
=======
package com.example.study_system.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.study_system.dao.JQuestionOptionMapper;
import com.example.study_system.dao.QuestionInfoMapper;
import com.example.study_system.dto.QuestionResultDTO;
import com.example.study_system.model.JQuestionOption;
import com.example.study_system.model.QuestionInfo;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class QuestionInfoServiceImpl implements IQuestionInfoService {
	@Resource
	private QuestionInfoMapper questionInfoMapper;
	@Resource
	private JQuestionOptionMapper jQuestionInfoMapper;

	@Override
	public int addQuestion(QuestionInfoWithBLOBs question, List<JQuestionOption> questionOptions) {
		Date date = new Date();
		question.setcTime(date);
		question.setcUser("未定义");
		int result = questionInfoMapper.insertSelective(question);
		questionOptions.forEach(item -> {
			item.setcUser("未定义");
			item.setcTime(date);
			item.setQuestionId(question.getQuestionId());
			jQuestionInfoMapper.insertSelective(item);
		});

		return result;
	}

	@Override
	@Transactional
	public int deleteQuestion(Long questionId) {
		int result = questionInfoMapper.deleteByPrimaryKey(questionId);
		jQuestionInfoMapper.deleteByPrimaryKey(questionId);
		return result;
	}

	@Override
	public int updateQuestion(QuestionInfoWithBLOBs question, List<JQuestionOption> questionOptions, Integer count) {
		Date date = new Date();
		question.setmTime(date);
		question.setcUser("未定义");
		int result = questionInfoMapper.updateByPrimaryKeySelective(question);
		List<JQuestionOption> options = jQuestionInfoMapper.selectQuestionByQuestionId(question.getQuestionId());
		questionOptions.forEach(item -> {
			item.setcUser("未定义");
			item.setcTime(date);
			jQuestionInfoMapper.updateByPrimaryKeySelective(item);
		});
		if (count != 0) {
			if (count > 0) {
				int index = 0;
				for (int i = 0; i < count; i++) {
					index++;
					questionOptions.get(questionOptions.size() - index).setQuestionId(question.getQuestionId());
					jQuestionInfoMapper.insertSelective(questionOptions.get(questionOptions.size() - index));
				}
			} else {
				int index = 0;
				for (int i = 0; i < -(count); i++) {
					index++;
					jQuestionInfoMapper.deleteByPrimaryKey(options.get(options.size() - index).getRef());
					
				}
			}
		}
		return result;
	}

	@Override
	@Transactional
	public PageInfo<QuestionResultDTO> selectQuestion(Integer pageNum, Integer pageSize, String content) {
		PageHelper.startPage(pageNum, pageSize);
		List<QuestionInfoWithBLOBs> questionList = questionInfoMapper.selectAllQuestion(content);
		List<JQuestionOption> questionOptionList = jQuestionInfoMapper.selectAllQuestionOption();
		List<QuestionResultDTO> questionResultDTO = new ArrayList<QuestionResultDTO>();
		questionList.forEach(questionItem -> {
			questionOptionList.forEach(optionItem -> {
				if (questionItem.getQuestionId() == optionItem.getQuestionId()) {
					questionResultDTO.add(new QuestionResultDTO(questionItem.getQuestionId(),
							questionItem.getQuestionType(), questionItem.getScore(), questionItem.getDifficulty(),
							optionItem.getIsRight(), optionItem.getOptionType(), questionItem.getContent(),
							questionItem.getAnalysis(), optionItem.getContent(), questionItem.getStatus(),
							questionItem.getcTime(), questionItem.getmTime(), questionItem.getcUser(),
							questionItem.getmUser(), optionItem.getRef()));
				}
			});
		});
		PageInfo<QuestionResultDTO> result = new PageInfo<QuestionResultDTO>(questionResultDTO);
		return result;
	}

	@Override
	public List<QuestionResultDTO> selectQuestionTitle(Long questionId) {
		QuestionInfoWithBLOBs question = questionInfoMapper.selectByPrimaryKey(questionId);
		List<JQuestionOption> questionOptionList = jQuestionInfoMapper.selectQuestionByQuestionId(questionId);
		List<QuestionResultDTO> questionResultDTO = new ArrayList<QuestionResultDTO>();

		questionOptionList.forEach(optionItem -> {

			questionResultDTO.add(new QuestionResultDTO(question.getQuestionId(), question.getQuestionType(),
					question.getScore(), question.getDifficulty(), optionItem.getIsRight(), optionItem.getOptionType(),
					question.getContent(), question.getAnalysis(), optionItem.getContent(), question.getStatus(),
					question.getcTime(), question.getmTime(), question.getcUser(), question.getmUser(),
					optionItem.getRef()));

		});

		return questionResultDTO;
	}

	@Override
	public int selectQuestionCount(Integer questionType) {
		return questionInfoMapper.selectQuestionCount(questionType);
	}

	@Override
	public QuestionInfoWithBLOBs selectAnalysis(Long questionId) {
		return questionInfoMapper.selectAnalysisById(questionId);
	}
}
>>>>>>> remotes/origin/dev-wtq
=======
package com.example.study_system.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.study_system.dao.JQuestionOptionMapper;
import com.example.study_system.dao.QuestionInfoMapper;
import com.example.study_system.dto.QuestionResultDTO;
import com.example.study_system.model.JQuestionOption;
import com.example.study_system.model.QuestionInfo;
import com.example.study_system.model.QuestionInfoWithBLOBs;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class QuestionInfoServiceImpl implements IQuestionInfoService {
	@Resource
	private QuestionInfoMapper questionInfoMapper;
	@Resource
	private JQuestionOptionMapper jQuestionInfoMapper;

	@Override
	public int addQuestion(QuestionInfoWithBLOBs question, List<JQuestionOption> questionOptions) {
		Date date = new Date();
		question.setcTime(date);
		question.setcUser("未定义");
		int result = questionInfoMapper.insertSelective(question);
		questionOptions.forEach(item -> {
			item.setcUser("未定义");
			item.setcTime(date);
			item.setQuestionId(question.getQuestionId());
			jQuestionInfoMapper.insertSelective(item);
		});

		return result;
	}

	@Override
	@Transactional
	public int deleteQuestion(Long questionId) {
		int result = questionInfoMapper.deleteByPrimaryKey(questionId);
		jQuestionInfoMapper.deleteByPrimaryKey(questionId);
		return result;
	}

	@Override
	public int updateQuestion(QuestionInfoWithBLOBs question, List<JQuestionOption> questionOptions, Integer count) {
		Date date = new Date();
		question.setmTime(date);
		question.setcUser("未定义");
		int result = questionInfoMapper.updateByPrimaryKeySelective(question);
		List<JQuestionOption> options = jQuestionInfoMapper.selectQuestionByQuestionId(question.getQuestionId());
		questionOptions.forEach(item -> {
			item.setcUser("未定义");
			item.setcTime(date);
			jQuestionInfoMapper.updateByPrimaryKeySelective(item);
		});
		if (count != 0) {
			if (count > 0) {
				int index = 0;
				for (int i = 0; i < count; i++) {
					index++;
					questionOptions.get(questionOptions.size() - index).setQuestionId(question.getQuestionId());
					jQuestionInfoMapper.insertSelective(questionOptions.get(questionOptions.size() - index));
				}
			} else {
				int index = 0;
				for (int i = 0; i < -(count); i++) {
					index++;
					jQuestionInfoMapper.deleteByPrimaryKey(options.get(options.size() - index).getRef());
					
				}
			}
		}
		return result;
	}

	@Override
	@Transactional
	public PageInfo<QuestionResultDTO> selectQuestion(Integer pageNum, Integer pageSize, String content) {
		PageHelper.startPage(pageNum, pageSize);
		List<QuestionInfoWithBLOBs> questionList = questionInfoMapper.selectAllQuestion(content);
		List<JQuestionOption> questionOptionList = jQuestionInfoMapper.selectAllQuestionOption();
		List<QuestionResultDTO> questionResultDTO = new ArrayList<QuestionResultDTO>();
		questionList.forEach(questionItem -> {
			questionOptionList.forEach(optionItem -> {
				if (questionItem.getQuestionId() == optionItem.getQuestionId()) {
					questionResultDTO.add(new QuestionResultDTO(questionItem.getQuestionId(),
							questionItem.getQuestionType(), questionItem.getScore(), questionItem.getDifficulty(),
							optionItem.getIsRight(), optionItem.getOptionType(), questionItem.getContent(),
							questionItem.getAnalysis(), optionItem.getContent(), questionItem.getStatus(),
							questionItem.getcTime(), questionItem.getmTime(), questionItem.getcUser(),
							questionItem.getmUser(), optionItem.getRef()));
				}
			});
		});
		PageInfo<QuestionResultDTO> result = new PageInfo<QuestionResultDTO>(questionResultDTO);
		return result;
	}

	@Override
	public List<QuestionResultDTO> selectQuestionTitle(Long questionId) {
		QuestionInfoWithBLOBs question = questionInfoMapper.selectByPrimaryKey(questionId);
		List<JQuestionOption> questionOptionList = jQuestionInfoMapper.selectQuestionByQuestionId(questionId);
		List<QuestionResultDTO> questionResultDTO = new ArrayList<QuestionResultDTO>();

		questionOptionList.forEach(optionItem -> {

			questionResultDTO.add(new QuestionResultDTO(question.getQuestionId(), question.getQuestionType(),
					question.getScore(), question.getDifficulty(), optionItem.getIsRight(), optionItem.getOptionType(),
					question.getContent(), question.getAnalysis(), optionItem.getContent(), question.getStatus(),
					question.getcTime(), question.getmTime(), question.getcUser(), question.getmUser(),
					optionItem.getRef()));

		});

		return questionResultDTO;
	}

	@Override
	public int selectQuestionCount(Integer questionType) {
		return questionInfoMapper.selectQuestionCount(questionType);
	}

	@Override
	public QuestionInfoWithBLOBs selectAnalysis(Long questionId) {
		return questionInfoMapper.selectAnalysisById(questionId);
	}
}
>>>>>>> remotes/origin/dev-wtq
