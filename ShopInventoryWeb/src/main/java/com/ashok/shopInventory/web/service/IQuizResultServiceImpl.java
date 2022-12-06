package com.ashok.shopInventory.web.service;


import com.ashok.shopInventory.web.entity.QuizResult;

import java.util.List;

public interface IQuizResultServiceImpl {
    QuizResult save(QuizResult quizResult);

    List<QuizResult> getResultByCourseAndSubjectAndQuizNumber(String course, String subject, String quizNumber);

    List<QuizResult> getResultByCourseAndSubjectAndQuizNumberAndUser(String course, String subject, String quizNumber, long parseLong);
}
