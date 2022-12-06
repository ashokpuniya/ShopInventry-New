package com.ashok.shopInventory.web.dto;

import java.util.Date;

public class QuizDetaildto {

    private String quizNumber;
    private Date created;

    public String getQuizNumber() {
        return quizNumber;
    }

    public void setQuizNumber(String quizNumber) {
        this.quizNumber = quizNumber;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "QuizDetaildto{" +
                "quizNumber='" + quizNumber + '\'' +
                ", created=" + created +
                '}';
    }
}
