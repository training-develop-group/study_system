package com.example.study_system.dto;

import java.util.Date;
<<<<<<< HEAD
=======
import java.util.List;

import com.example.study_system.model.JQuestionOption;
>>>>>>> dev-wtq

public class QuestionResultDTO {
	private Long questionId;

	private Integer questionType;

	private Float score;
<<<<<<< HEAD

	private Integer difficulty;

	private Integer isRight;

	private String optionType;
=======
	
	private Float newScore;

	public Float getNewScore() {
		return newScore;
	}

	public void setNewScore(Float newScore) {
		this.newScore = newScore;
	}

	private Integer difficulty;
>>>>>>> dev-wtq

	private String content;

	private String analysis;

<<<<<<< HEAD
	private String optionContent;

=======
>>>>>>> dev-wtq
	private Integer status;

	private Date cTime;

	private Date mTime;

	private String cUser;

	private String mUser;

<<<<<<< HEAD
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
=======
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
>>>>>>> dev-wtq
	}

	public QuestionResultDTO() {

	}

<<<<<<< HEAD
	public QuestionResultDTO(Long questionId, Integer questionType, Float score, Integer difficulty, Integer isRight,
			String optionType, String content, String analysis, String optionCentent, Integer status, Date cTime,
			Date mTime, String cUser, String mUser, Long ref) {
=======
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
>>>>>>> dev-wtq
		this.questionId = questionId;
		this.questionType = questionType;
		this.score = score;
		this.difficulty = difficulty;
<<<<<<< HEAD
		this.isRight = isRight;
		this.optionType = optionType;
		this.content = content;
		this.analysis = analysis;
		this.optionContent = optionCentent;
=======
		this.content = content;
		this.analysis = analysis;
>>>>>>> dev-wtq
		this.status = status;
		this.cTime = cTime;
		this.mTime = mTime;
		this.cUser = cUser;
		this.mUser = mUser;
<<<<<<< HEAD
		this.ref = ref;
=======
		this.optionInfo =optionInfo;
		this.newScore = score1;
>>>>>>> dev-wtq
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

<<<<<<< HEAD
	public String getOptionContent() {
		return optionContent;
	}

	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}

=======
>>>>>>> dev-wtq
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

<<<<<<< HEAD
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

=======
>>>>>>> dev-wtq
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
