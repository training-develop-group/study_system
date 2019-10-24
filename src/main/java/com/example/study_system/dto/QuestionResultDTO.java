package com.example.study_system.dto;

import java.util.Date;

public class QuestionResultDTO {
	private Long questionId;

	private Integer questionType;

	private Float score;

	private Integer difficulty;

	private Integer isRight;

	private String optionType;

	private String content;

	private String analysis;

	private String optionContent;

	private Integer status;

	private Date cTime;

	private Date mTime;

	private String cUser;

	private String mUser;

	private Long ref;

	public String getmUser() {
		return mUser;
	}

	public void setmUser(String mUser) {
		this.mUser = mUser;
	}

	public Long getRef() {
		return ref;
	}

	public void setRef(Long ref) {
		this.ref = ref;
	}

	public QuestionResultDTO() {

	}

	public QuestionResultDTO(Long questionId, Integer questionType, Float score, Integer difficulty, Integer isRight,
			String optionType, String content, String analysis, String optionCentent, Integer status, Date cTime,
			Date mTime, String cUser, String mUser, Long ref) {
		this.questionId = questionId;
		this.questionType = questionType;
		this.score = score;
		this.difficulty = difficulty;
		this.isRight = isRight;
		this.optionType = optionType;
		this.content = content;
		this.analysis = analysis;
		this.optionContent = optionCentent;
		this.status = status;
		this.cTime = cTime;
		this.mTime = mTime;
		this.cUser = cUser;
		this.mUser = mUser;
		this.ref = ref;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public String getOptionContent() {
		return optionContent;
	}

	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
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

	public Integer getIsRight() {
		return isRight;
	}

	public void setIsRight(Integer isRight) {
		this.isRight = isRight;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
