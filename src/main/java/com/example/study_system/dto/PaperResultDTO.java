package com.example.study_system.dto;

import com.example.study_system.model.PaperInfo;

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
    private Float userScore;
    private Integer Many;
    private List<QuestionResultDTO> questionList;

    public List<QuestionResultDTO> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionResultDTO> questionList) {
        this.questionList = questionList;
    }

    public PaperResultDTO() {

    }

    public PaperResultDTO(PaperInfo paperInfo) {
        super();
        this.paperId = paperInfo.getPaperId();
        this.paperName = paperInfo.getPaperName();
        this.score = paperInfo.getScore();
        this.status = paperInfo.getStatus();
        this.cTime = paperInfo.getcTime();
        this.mTime = paperInfo.getmTime();
        this.cUser = paperInfo.getcUser();
        this.mUser = paperInfo.getmUser();
    }

    public PaperResultDTO(Long paperId, String paperName, Float score, Integer status, Date cTime, Date mTime,
                          String cUser, String mUser, List<QuestionResultDTO> questionList) {
        this.paperId = paperId;
        this.paperName = paperName;
        this.score = score;
        this.status = status;
        this.cTime = cTime;
        this.mTime = mTime;
        this.cUser = cUser;
        this.mUser = mUser;
        this.questionList = questionList;
    }

    public PaperResultDTO(Long paperId, String paperName, Integer status, Integer many, Integer single) {
        this.paperId = paperId;
        this.paperName = paperName;
        this.status = status;
        this.Many = many;
        this.Single = single;
    }

    public PaperResultDTO(Long paperId, String paperName, Float score, Integer status, Date cTime, Date mTime,
                          String cUser, String mUser, List<QuestionResultDTO> questionList, float userScore) {
        this.paperId = paperId;
        this.paperName = paperName;
        this.score = score;
        this.status = status;
        this.cTime = cTime;
        this.mTime = mTime;
        this.cUser = cUser;
        this.mUser = mUser;
        this.questionList = questionList;
        this.userScore = userScore;
    }

    public Float getUserScore() {
        return userScore;
    }

    public void setUserScore(Float userScore) {
        this.userScore = userScore;
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
}
