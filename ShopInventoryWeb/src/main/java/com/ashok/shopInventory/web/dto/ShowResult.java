package com.ashok.shopInventory.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties
public class ShowResult implements Serializable{

    private Integer rank;
    private String  userName;
    private Double  score;
    private Double  totalScore;


    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "ShowResult{" +
                "rank=" + rank +
                ", userName='" + userName + '\'' +
                ", score=" + score +
                ", totalScore=" + totalScore +
                '}';
    }
}
