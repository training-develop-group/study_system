package com.example.study_system.dto;

public class PaperQuestionResultDTO {
	private Long paperId;
	private Long questionId;
	private Float score;

	public PaperQuestionResultDTO() {

	}

	public PaperQuestionResultDTO(Long paperId, Long questionId) {
		this.paperId = paperId;
		this.questionId = questionId;
	}

	public PaperQuestionResultDTO(Long paperId, Long questionId, Float score) {
		this.paperId = paperId;
		this.questionId = questionId;
		this.score = score;
	}

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

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public PaperQuestionResultDTO(Long questionId) {
		this.questionId = questionId;
	}

	public PaperQuestionResultDTO(Float score) {
		this.score = score;
	}
}
