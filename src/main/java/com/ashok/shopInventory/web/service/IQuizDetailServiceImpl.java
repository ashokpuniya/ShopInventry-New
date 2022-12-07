package com.ashok.shopInventory.web.service;


import com.ashok.shopInventory.web.dto.QuestionParserDto;
import com.ashok.shopInventory.web.entity.ActionParams;

import java.util.Date;
import java.util.List;

public interface IQuizDetailServiceImpl {
    ActionParams getQuizDetailByCourseNameAndSubjectName(String subjectName, String courseName, Integer limit);
    ActionParams saveQuizDetail(ActionParams actionParams);
    List<ActionParams> getAllQuizDetailByCourseAndSubjectName(String subjectName, String courseName);
    List<Date>  getQuizDatailByCreated(String subjectName,String courseName);

    String getQuestionTemplateFromQuizDetail(String subject, String course, String quizNumber);

    int saveQuizResult(QuestionParserDto questionParserDto);
}
