package com.gaf.project.model;

public class PercentValue {
    private String questionContent;
    private String percent;
    private String percent1;
    private String percent2;
    private String percent3;
    private String percent4;

    public PercentValue(String questionContent, String percent, String percent1, String percent2, String percent3, String percent4) {
        this.questionContent = questionContent;
        this.percent = percent;
        this.percent1 = percent1;
        this.percent2 = percent2;
        this.percent3 = percent3;
        this.percent4 = percent4;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getPercent1() {
        return percent1;
    }

    public void setPercent1(String percent1) {
        this.percent1 = percent1;
    }

    public String getPercent2() {
        return percent2;
    }

    public void setPercent2(String percent2) {
        this.percent2 = percent2;
    }

    public String getPercent3() {
        return percent3;
    }

    public void setPercent3(String percent3) {
        this.percent3 = percent3;
    }

    public String getPercent4() {
        return percent4;
    }

    public void setPercent4(String percent4) {
        this.percent4 = percent4;
    }
}
