<<<<<<< HEAD
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
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IQuestionInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zaxxer.hikari.util.SuspendResumeLock;

@Service
public class QuestionInfoServiceImpl extends BaseService implements IQuestionInfoService {
	/**
	 * 修改试题分数 score:分数   questionId:试题id
	 */
	@Override
	public int updateQuestionScore(Float score, Long questionId) {
		return questionInfoMapper.updateQuestionScore(score, questionId);
	}

	/**
	 * 添加试题 question:试题对象 questionOptions:试题内选项集合
	 */
	@Override
	@Transactional
	public int addQuestion(QuestionInfoWithBLOBs question, List<JQuestionOption> questionOptions) {
		Date date = new Date();
		question.setcTime(date);
		question.setcUser("未定义");
		question.setmTime(date);
		question.setmUser("未定义");
		int result = questionInfoMapper.insertSelective(question);
		questionOptions.forEach(item -> {
			item.setcUser("未定义");
			item.setcTime(date);
			item.setmUser("未定义");
			item.setmTime(date);
			item.setQuestionId(question.getQuestionId());
			jQuestionOptionMapper.insertSelective(item);
		});

		return result;
	}

	/**
	 * 删除试题
	 */
	@Override
	@Transactional
	public int deleteQuestion(Long questionId) {
		int result = questionInfoMapper.deleteByPrimaryKey(questionId);
		jQuestionOptionMapper.deleteQuestionOptionByQuestionId(questionId);
		return result;
	}

	/**
	 * 修改试题 question:试题对象 questionOptions:试题内选项集合
	 */
	@Override
	@Transactional
	public int updateQuestion(QuestionInfoWithBLOBs question, List<JQuestionOption> questionOptions) {
		Date date = new Date();
		question.setcTime(date);
		question.setmTime(date);
		question.setcUser("未定义");
		question.setmUser("未定义");
		int result = questionInfoMapper.deleteByPrimaryKey(question.getQuestionId());
		questionInfoMapper.insertSelective(question);
		List<JQuestionOption> options = questionOptions;
		jQuestionOptionMapper.deleteQuestionOptionByQuestionId(question.getQuestionId());
		options.forEach(item -> {
			item.setcTime(date);
			item.setmTime(date);
			item.setcUser("未定义");
			item.setmUser("未定义");
			item.setQuestionId(question.getQuestionId());
			jQuestionOptionMapper.insertSelective(item);
		});
		return result;
	}

	/**
	 * 查看试题列表 pageNum：当前页数 pageSize：当前页面的数据数量 content:题目 questionType:试题类型 1单选 2多选
	 */
	@Override
	@Transactional
	public PageInfo<QuestionResultDTO> selectQuestion(Integer pageNum, Integer pageSize, String content,
			Integer questionType) {
		PageHelper.startPage(pageNum, pageSize);
		List<QuestionInfoWithBLOBs> questionList = questionInfoMapper.selectAllQuestion(content, questionType);
		List<QuestionResultDTO> questionResultDTO = new ArrayList<QuestionResultDTO>();
		questionList.forEach(questionItem -> {
			List<JQuestionOption> optionList = jQuestionOptionMapper
					.selectQuestionByQuestionId(questionItem.getQuestionId());
			questionResultDTO.add(new QuestionResultDTO(questionItem.getQuestionId(), questionItem.getQuestionType(),
					questionItem.getScore(), questionItem.getDifficulty(), questionItem.getContent(),
					questionItem.getAnalysis(), questionItem.getStatus(), questionItem.getcTime(),
					questionItem.getmTime(), questionItem.getcUser(), questionItem.getmUser(), optionList));
		});
		PageInfo<QuestionResultDTO> result = new PageInfo<QuestionResultDTO>(questionResultDTO);
		result.setTotal(questionInfoMapper.selectQuestionCount(questionType));
		return result;
	}

	/**
	 * 查看试题详细 questionId:试题id
	 */
	@Override
	@Transactional
	public List<QuestionResultDTO> selectQuestionTitle(Long questionId) {
		QuestionInfoWithBLOBs question = questionInfoMapper.selectByPrimaryKey(questionId);
		List<JQuestionOption> questionOptionList = jQuestionOptionMapper.selectQuestionByQuestionId(questionId);
		List<QuestionResultDTO> questionResultDTO = new ArrayList<QuestionResultDTO>();
		questionResultDTO.add(new QuestionResultDTO(question.getQuestionId(), question.getQuestionType(),
				question.getScore(), question.getDifficulty(), question.getContent(), question.getAnalysis(),
				question.getStatus(), question.getcTime(), question.getmTime(), question.getcUser(),
				question.getmUser(), questionOptionList));
		questionResultDTO.forEach(a -> {
		});
		return questionResultDTO;
	}

	/**
	 * 查询试题数量 questionType:试题类型 1单选 2多选
	 */
	@Override
	public int selectQuestionCount(Integer questionType) {
		return questionInfoMapper.selectQuestionCount(questionType);
	}

	/**
	 * 查询试题答案解析 questionId:试题id
	 */

	@Override
	public List<QuestionInfoWithBLOBs> selectAnalysis(Long questionId) {
		return questionInfoMapper.selectAnalysisById(questionId);
	}
}
>>>>>>> dev-wtq
