package com.czy.template.pojo;

public class Question {
    private int q_id;              //题目编号
    private int create_id;         //创建教师编号
    private String type;           //题目类型
    private String title;          //题目问题
    private String options;        //题目选项
    private String answer_text;    //回答
    private String answer;         //答案
    private int score;             //分数

    public Question(int q_id, int create_id, String type, String title, String options, String answer_text, String answer, int score) {
        this.q_id = q_id;
        this.create_id = create_id;
        this.type = type;
        this.title = title;
        this.options = options;
        this.answer_text = answer_text;
        this.answer = answer;
        this.score = score;
    }

    public Question() {
    }

    public int getQ_id() {
        return q_id;
    }

    public void setQ_id(int q_id) {
        this.q_id = q_id;
    }

    public int getCreate_id() {
        return create_id;
    }

    public void setCreate_id(int create_id) {
        this.create_id = create_id;
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

    public String getAnswer_text() {
        return answer_text;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
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
}
