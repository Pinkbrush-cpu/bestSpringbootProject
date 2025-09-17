package com.czy.template.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {
    @JsonProperty("qId")
    private int qId;              //题目编号
    private int createId;         //创建教师编号
    private String type;           //题目类型
    private String title;          //题目问题
    private String options;        //题目选项
    private String answerText;     //回答
    private String answer;         //答案
    private int score;             //分数

    public Question(int qId, int createId, String type, String title, String options, String answerText, String answer, int score) {
        this.qId = qId;
        this.createId = createId;
        this.type = type;
        this.title = title;
        this.options = options;
        this.answerText = answerText;
        this.answer = answer;
        this.score = score;
    }

    public Question() {
    }

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Question{" +
                "qId=" + qId +
                ", createId=" + createId +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", options='" + options + '\'' +
                ", answerText='" + answerText + '\'' +
                ", answer='" + answer + '\'' +
                ", score=" + score +
                '}';
    }
}
