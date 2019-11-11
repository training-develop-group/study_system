<<<<<<< HEAD
package com.example.study_system.dto;

import java.util.Date;
import java.util.List;

public class PaperResultDTO {
	private Long paperId;
	private String paperName;
	private Float score;
	private Integer status;
	private Date cTime;

	private Date mTime;

	private String cUser;

	private String mUser;

	private Integer Single;

	public Integer getSingle() {
		return Single;
	}

	public void setSingle(Integer single) {
		Single = single;
	}

	public Integer getMany() {
		return Many;
	}

	public void setMany(Integer many) {
		Many = many;
	}

	private Integer Many;

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

	public String getmUser() {
		return mUser;
	}

	public void setmUser(String mUser) {
		this.mUser = mUser;
	}

	private List<QuestionResultDTO> questions;

	public PaperResultDTO() {

	}

	public PaperResultDTO(Long paperId, String paperName, Float score, Integer status, Date cTime, Date mTime,
			String cUser, String mUser, List<QuestionResultDTO> questions) {
		this.paperId = paperId;
		this.paperName = paperName;
		this.score = score;
		this.status = status;
		this.cTime = cTime;
		this.mTime = mTime;
		this.cUser = cUser;
		this.mUser = mUser;
		this.questions = questions;
	}
	
	public PaperResultDTO(Long paperId , String paperName , Integer status , Integer many , Integer single) {
		this.paperId = paperId;
		this.paperName = paperName;
		this.status = status;
		this.Many = many;
		this.Single = single;
	}

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<QuestionResultDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionResultDTO> questions) {
		this.questions = questions;
	}
}
=======
package com.example.study_system.dto;

import java.util.Date;
import java.util.List;

public class PaperResultDTO {
	private Long paperId;
	private String paperName;
	private Float score;
	private Integer status;
	private Date cTime;

	private Date mTime;

	private String cUser;

	private String mUser;

	private Integer Single;

	public Integer getSingle() {
		return Single;
	}

	public void setSingle(Integer single) {
		Single = single;
	}

	public Integer getMany() {
		return Many;
	}

	public void setMany(Integer many) {
		Many = many;
	}

	private Integer Many;

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

	public String getmUser() {
		return mUser;
	}

	public void setmUser(String mUser) {
		this.mUser = mUser;
	}

	private List<QuestionResultDTO> questions;

	public PaperResultDTO() {

	}

	public PaperResultDTO(Long paperId, String paperName, Float score, Integer status, Date cTime, Date mTime,
			String cUser, String mUser, List<QuestionResultDTO> questions) {
		this.paperId = paperId;
		this.paperName = paperName;
		this.score = score;
		this.status = status;
		this.cTime = cTime;
		this.mTime = mTime;
		this.cUser = cUser;
		this.mUser = mUser;
		this.questions = questions;
	}
	
	public PaperResultDTO(Long paperId , String paperName , Integer status , Integer many , Integer single) {
		this.paperId = paperId;
		this.paperName = paperName;
		this.status = status;
		this.Many = many;
		this.Single = single;
	}

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<QuestionResultDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionResultDTO> questions) {
		this.questions = questions;
	}
}
>>>>>>> caa8072d35903f32aea5d24014a89c312b1470ab
