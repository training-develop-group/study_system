package com.example.study_system.dto;

public class PaperQuestionPesultDTO {
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

	private Long questionId;
	
	public PaperQuestionPesultDTO() {

	}
	
	private Float score;
	
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public PaperQuestionPesultDTO(Long paperId , Long questionId) {
		this.paperId = paperId;
		this.questionId = questionId;
	}
	
	public PaperQuestionPesultDTO(Long paperId , Long questionId , Float score) {
		this.paperId = paperId;
		this.questionId = questionId;
		this.score = score;
	}
}
