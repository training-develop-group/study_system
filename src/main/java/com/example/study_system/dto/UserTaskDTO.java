package com.example.study_system.dto;


public class UserTaskDTO {
    private int taskNumber;
    private int paperInfoNumber;
    private int questionNumber;
    private float questionPercentage;
    private float taskPercentage;
    private String studentName;
    
    public UserTaskDTO() {

    }

    public UserTaskDTO(int taskNumber, int paperInfoNumber, int questionNumber, float answerRecord, float percentage, String studentName) {
        this.setTaskNumber(taskNumber);
        this.setPaperInfoNumber(paperInfoNumber);
        this.setQuestionNumber(questionNumber);
        this.setQuestionPercentage(answerRecord);
        this.setTaskPercentage(percentage);
        this.setStudentName(studentName);
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    public int getPaperInfoNumber() {
        return paperInfoNumber;
    }

    public void setPaperInfoNumber(int paperInfoNumber) {
        this.paperInfoNumber = paperInfoNumber;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public float getQuestionPercentage() {
        return questionPercentage;
    }

    public void setQuestionPercentage(float answerRecord) {
        this.questionPercentage = answerRecord;
    }

    public float getTaskPercentage() {
        return taskPercentage;
    }

    public void setTaskPercentage(float percentage) {
        this.taskPercentage = percentage;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
