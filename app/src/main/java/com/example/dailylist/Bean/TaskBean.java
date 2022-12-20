package com.example.dailylist.Bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: 夜雨
 * @ClassName: task
 * @Description: TODO
 * @Date: 2022/12/7 1:17
 * @Version: 1.0
 */
public class TaskBean implements Serializable {
    private LocalDateTime startTime, endTime;
    private List<LocalDateTime> executionTime;
    private Integer taskId, categoryId, quadrant;
    private String taskName, represent;
    private Boolean execution;

    public Boolean getExecution() {
        return execution;
    }

    public void setExecution(Boolean execution) {
        this.execution = execution;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getQuadrant() {
        return quadrant;
    }

    public void setQuadrant(Integer quadrant) {
        this.quadrant = quadrant;
    }

    public List<LocalDateTime> getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(List<LocalDateTime> executionTime) {
        this.executionTime = executionTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getRepresent() {
        return represent;
    }

    public void setRepresent(String represent) {
        this.represent = represent;
    }

//    public TaskBean clone() {
//        TaskBean taskBean = null;
//        try {
//            taskBean = (TaskBean) super.clone();
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
//        return taskBean;
//    }
}
