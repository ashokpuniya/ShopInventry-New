package com.ashok.shopInventory.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties
public class QuestionParserDto implements Serializable {
    private static final long serialVersionUID = -8507755517046583218L;

    private List<QuestionParser> quizData;

    public List<QuestionParser> getQuizData() {
        return quizData;
    }

    public void setQuizData(List<QuestionParser> quizData) {
        this.quizData = quizData;
    }

    @Override
    public String toString() {
        return "QuestionParserDto{" +
                "quizData=" + quizData +
                '}';
    }
}
