package com.example.study_system.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TaskInfo {
    private Long taskId;

    private Long resId;

    private Long paperId;

    private Integer taskType;

    private String taskName;
    
    private String userId;

	private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date mTime;

    private String cUser;

    private String mUser;

    private String taskRemark;
    
    private Integer learningNum;
    private Integer testNum;
    private Integer syntheticalNum;
    
    private float comprehensive;
	private float test;
	private float learning;
    
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
    public Integer getLearningNum() {
		return learningNum;
	}

	public void setLearningNum(Integer learningNum) {
		this.learningNum = learningNum;
	}

	public Integer getTestNum() {
		return testNum;
	}

	public void setTestNum(Integer testNum) {
		this.testNum = testNum;
	}

	public Integer getSyntheticalNum() {
		return syntheticalNum;
	}

	public void setSyntheticalNum(Integer syntheticalNum) {
		this.syntheticalNum = syntheticalNum;
	}

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser == null ? null : mUser.trim();
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark == null ? null : taskRemark.trim();
    }
	
	public float getComprehensive() {
		return comprehensive;
	}

	public void setComprehensive(float comprehensive) {
		this.comprehensive = comprehensive;
	}

	public float getTest() {
		return test;
	}

	public void setTest(float test) {
		this.test = test;
	}

	public float getLearning() {
		return learning;
	}

	public void setLearning(float learning) {
		this.learning = learning;
	}
}