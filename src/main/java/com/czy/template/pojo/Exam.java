package com.czy.template.pojo;

import java.util.List;

public class Exam {
    private int exam_id;
    private int create_id;
    private String name;
    private String status;
    private List<Question> questions;
    private int totalScore;
    private int totalTitle;


    public Exam() {
    }

    public Exam(int exam_id, int create_id, String name, String status, List<Question> questions, int totalScore, int totalTitle) {
        this.exam_id = exam_id;
        this.create_id = create_id;
        this.name = name;
        this.status = status;
        this.questions = questions;
        this.totalScore = totalScore;
        this.totalTitle = totalTitle;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public int getCreate_id() {
        return create_id;
    }

    public void setCreate_id(int create_id) {
        this.create_id = create_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalTitle() {
        return totalTitle;
    }

    public void setTotalTitle(int totalTitle) {
        this.totalTitle = totalTitle;
    }
}
