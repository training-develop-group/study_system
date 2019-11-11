package com.example.study_system.dto;

import java.util.Date;
import java.util.List;

import com.example.study_system.model.JQuestionOption;

public class QuestionResultDTO {
	private Long questionId;

	private Integer questionType;

	private Float score;
	
	private Float newScore;

	public Float getNewScore() {
		return newScore;
	}

	public void setNewScore(Float newScore) {
		this.newScore = newScore;
	}

	private Integer difficulty;

	private String content;

	private String analysis;

	private Integer status;

	private Date cTime;

	private Date mTime;

	private String cUser;

	private String mUser;

	private List<JQuestionOption> optionInfo;

	public List<JQuestionOption> getOptionInfo() {
		return optionInfo;
	}

	public void setOptionInfo(List<JQuestionOption> optionInfo) {
		this.optionInfo = optionInfo;
	}

	public String getmUser() {
		return mUser;
	}

	public void setmUser(String mUser) {
		this.mUser = mUser;
	}

	public QuestionResultDTO() {

	}

	public QuestionResultDTO(Long questionId, Integer questionType, Float score, Integer difficulty, String content,
			String analysis, Integer status, Date cTime, Date mTime, String cUser, String mUser,
			List<JQuestionOption> optionInfo) {
		this.questionId = questionId;
		this.questionType = questionType;
		this.score = score;
		this.difficulty = difficulty;
		this.content = content;
		this.analysis = analysis;
		this.status = status;
		this.cTime = cTime;
		this.mTime = mTime;
		this.cUser = cUser;
		this.mUser = mUser;
		this.optionInfo = optionInfo;
	}
	
	public QuestionResultDTO(Long questionId, Integer questionType, Float score, Integer difficulty, String content,
			String analysis, Integer status, Date cTime, Date mTime, String cUser, String mUser,
			List<JQuestionOption> optionInfo , Float score1) {
		this.questionId = questionId;
		this.questionType = questionType;
		this.score = score;
		this.difficulty = difficulty;
		this.content = content;
		this.analysis = analysis;
		this.status = status;
		this.cTime = cTime;
		this.mTime = mTime;
		this.cUser = cUser;
		this.mUser = mUser;
		this.optionInfo =optionInfo;
		this.newScore = score1;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Date getmTime() {
		return mTime;
	}

	public void setmTime(Date mTime) {
		this.mTime = mTime;
	}

	public String getcUser() {
		return cUser;
	}

	public void setcUser(String cUser) {
		this.cUser = cUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
