package com.ashok.shopInventory.web.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "quiz_detail")
public class ActionParams implements Serializable {
    private static final long serialVersionUID = -1842969714133775846L;
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

    @Basic(optional = false)
    @Column(name="questionTemplate")
    @Lob
    private String questionTemplate;

    @Basic(optional = false)
    @Column(name="answerTemplate")
    @Lob
    private String answerTemplate;

    @Column(name="created")
    private Date created;

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

    public String getQuestionTemplate() {
        return questionTemplate;
    }

    public void setQuestionTemplate(String questionTemplate) {
        this.questionTemplate = questionTemplate;
    }

    public String getAnswerTemplate() {
        return answerTemplate;
    }

    public void setAnswerTemplate(String answerTemplate) {
        this.answerTemplate = answerTemplate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ActionParams{" +
                "id=" + id +
                ", quizNumber='" + quizNumber + '\'' +
                ", courseName='" + courseName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", questionTemplate='" + questionTemplate + '\'' +
                ", answerTemplate='" + answerTemplate + '\'' +
                ", created=" + created +
                '}';
    }
}
