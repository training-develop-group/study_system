package com.example.study_system.dto;

import java.util.Date;
import java.util.List;

import com.example.study_system.model.JQuestionOption;
import com.example.study_system.model.QuestionInfoWithBLOBs;

public class QuestionResultDTO extends QuestionInfoWithBLOBs{
	
	private List<JQuestionOption> optionInfo;
	private String userAnswer;
	private List<QuestionInfoWithBLOBs> questionList;
	private Integer orderIndex;
	
	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public List<QuestionInfoWithBLOBs> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<QuestionInfoWithBLOBs> questionList) {
		this.questionList = questionList;
	}

	public String getUserAnswer() {
		return userAnswer;
	}
	
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	public List<JQuestionOption> getOptionInfo() {
		return optionInfo;
	}

	public void setOptionInfo(List<JQuestionOption> optionInfo) {
		this.optionInfo = optionInfo;
	}

	public QuestionResultDTO() {

	}

	public QuestionResultDTO(QuestionInfoWithBLOBs question) {
		super.setQuestionId(question.getQuestionId());
		super.setQuestionType(question.getQuestionType());
		super.setScore(question.getScore());
		super.setDifficulty(question.getDifficulty());
		super.setStatus(question.getStatus());
		super.setcTime(question.getcTime());
		super.setmTime(question.getmTime());
		super.setcUser(question.getcUser());
		super.setmUser(question.getmUser());
		super.setContent(question.getContent());
		super.setAnalysis(question.getAnalysis());
		super.setOptionType(question.getOptionType());
	}
	public QuestionResultDTO(QuestionInfoWithBLOBs question , List<JQuestionOption> optionInfo) {
		super.setQuestionId(question.getQuestionId());
		super.setQuestionType(question.getQuestionType());
		super.setScore(question.getScore());
		super.setDifficulty(question.getDifficulty());
		super.setStatus(question.getStatus());
		super.setcTime(question.getcTime());
		super.setmTime(question.getmTime());
		super.setcUser(question.getcUser());
		super.setmUser(question.getmUser());
		super.setContent(question.getContent());
		super.setAnalysis(question.getAnalysis());
		super.setOptionType(question.getOptionType());
		this.optionInfo = optionInfo;
	}

	public QuestionResultDTO(QuestionInfoWithBLOBs question , List<JQuestionOption> optionInfo , String userAnswer) {
		super.setQuestionId(question.getQuestionId());
		super.setQuestionType(question.getQuestionType());
		super.setScore(question.getScore());
		super.setDifficulty(question.getDifficulty());
		super.setStatus(question.getStatus());
		super.setcTime(question.getcTime());
		super.setmTime(question.getmTime());
		super.setcUser(question.getcUser());
		super.setmUser(question.getmUser());
		super.setContent(question.getContent());
		super.setAnalysis(question.getAnalysis());
		super.setOptionType(question.getOptionType());
		this.optionInfo = optionInfo;
		this.userAnswer = userAnswer;
	}
}
