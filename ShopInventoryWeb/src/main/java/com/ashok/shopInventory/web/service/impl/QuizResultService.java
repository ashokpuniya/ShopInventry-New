package com.ashok.shopInventory.web.service.impl;


import com.ashok.shopInventory.web.Repository.IQuizResultRepository;
import com.ashok.shopInventory.web.entity.QuizResult;
import com.ashok.shopInventory.web.service.IQuizResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("QuizResultService")
public class QuizResultService implements IQuizResultServiceImpl {
    @Autowired
    private IQuizResultRepository quizResultRepository;

    @Override
    public QuizResult save(QuizResult quizResult) {
        return quizResultRepository.save(quizResult);
    }

    @Override
    public List<QuizResult> getResultByCourseAndSubjectAndQuizNumber(String course, String subject, String quizNumber) {
        return quizResultRepository.getResultByCourseAndSubjectAndQuizNumber(course,subject,quizNumber);
    }

    @Override
    public List<QuizResult> getResultByCourseAndSubjectAndQuizNumberAndUser(String course, String subject, String quizNumber, long userId) {
        return quizResultRepository.getResultByCourseAndSubjectAndQuizNumberAndUser(course,subject,quizNumber,userId);
    }

}
