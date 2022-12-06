package com.ashok.shopInventory.web.service.impl;


import com.ashok.shopInventory.web.Repository.IQuizDetailRepository;
import com.ashok.shopInventory.web.dto.QuestionParser;
import com.ashok.shopInventory.web.dto.QuestionParserDto;
import com.ashok.shopInventory.web.entity.ActionParams;
import com.ashok.shopInventory.web.service.IQuizDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class QuizDetailService implements IQuizDetailServiceImpl {

    @Autowired
    private IQuizDetailRepository quizDetailRepository;


    @Override
    public ActionParams getQuizDetailByCourseNameAndSubjectName(String subjectName, String courseName, Integer limit) {
        return quizDetailRepository.getQuizDetailByCourseNameAndSubjectName(subjectName,courseName,limit);
    }

    @Modifying
    @Override
    public ActionParams saveQuizDetail(ActionParams actionParams) {
        return quizDetailRepository.save(actionParams);
    }

    @Override
    public List<ActionParams> getAllQuizDetailByCourseAndSubjectName(String subjectName, String courseName) {
        return quizDetailRepository.getAllQuizDetailByCourseAndSubjectName(subjectName,courseName);
    }

    @Override
    public List<Date> getQuizDatailByCreated(String subjectName, String courseName) {
        return quizDetailRepository.getQuizDetailByCreated(subjectName,courseName);
    }

    @Override
    public String getQuestionTemplateFromQuizDetail(String subject, String course, String quizNumber) {
        return quizDetailRepository.getQuestionTemplateFromQuizDetail(subject,course,quizNumber);
    }

    @Override
    public int saveQuizResult(QuestionParserDto questionParserDto) {
        int correct = 0;
        System.out.println("calculate quiz result "+questionParserDto);

        for(QuestionParser q: questionParserDto.getQuizData())
            if(q.getAns() == q.getChose())
                correct++;
        return correct;
    }
}
