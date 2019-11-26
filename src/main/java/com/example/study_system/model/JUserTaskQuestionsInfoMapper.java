package com.example.study_system.model;

public class JUserTaskQuestionsInfoMapper {
	Long questionId;
	String answer;
	Float score;
   Integer	isRight ; 
//  float scord ;
	 public Float getScore() {
	        return score;
	    }

	    public void setScore(Float score) {
	        this.score = score;
	    }
	    public Integer getIsRight() {
	        return isRight;
	    }

	    public void setIsRight(Integer isRight) {
	        this.isRight = isRight;
	    }
	    public Long getQuestionId() {
	        return questionId;
	    }

	    public void setQuestionId(Long questionId) {
	        this.questionId = questionId;
	    }
	    public String getAnswer() {
	        return answer;
	    }

	    public void setAnswer(String answer) {
	        this.answer = answer;
	    }
		
}
