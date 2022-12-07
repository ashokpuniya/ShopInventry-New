package com.ashok.shopInventory.web.entity;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "quiz_result")
public class QuizResult implements Serializable {
    private static final long serialVersionUID = -1072623940524150122L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name="quizNumber")
    private String quizNumber;

    @Column(name="courseName")
    private String courseName;

    @Column(name="subjectName")
    private String subjectName;

    @Column(name="userId")
    private long  userId;

    @Column(name="score")
    private Double score;

    @Column(name="total_score")
    private Double totalScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuizNumber() {
        return quizNumber;
    }

    public void setQuizNumber(String quizNumber) {
        this.quizNumber = quizNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
        return "QuizResult{" +
                "id=" + id +
                ", quizNumber='" + quizNumber + '\'' +
                ", courseName='" + courseName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", userId=" + userId +
                ", score=" + score +
                ", totalScore=" + totalScore +
                '}';
    }
}
