package com.example.study_system.model;

public class PaperQuestionInfo {
	private Long paperId;

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
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

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

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

	private Long questionId;
	
	private Integer questionType;
	
	private String paperName;
	
	private Integer status;
	
	private Integer Single;
	
	private Integer Many;
	
//	public PaperQuestionInfo(Long paperId , Long questionId, Integer questionType , String paperName , Integer status , Integer Single , Integer Many){
//		return;
//	}
}
