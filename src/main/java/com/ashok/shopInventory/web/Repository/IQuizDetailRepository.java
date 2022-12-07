package com.ashok.shopInventory.web.Repository;

import com.ashok.shopInventory.web.entity.ActionParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IQuizDetailRepository extends JpaRepository<ActionParams, Long> {
    @Query(nativeQuery = true,value="select * from quiz_detail qd where qd.course_name=:courseName and qd.subject_name = :subjectName order by id desc limit :limit")
    ActionParams getQuizDetailByCourseNameAndSubjectName(@Param("subjectName") String subjectName, @Param("courseName")String courseName, @Param("limit")Integer limit);

    @Query(nativeQuery = true,value="select * from quiz_detail qd where qd.course_name=:courseName and qd.subject_name = :subjectName order by id desc")
    List<ActionParams> getAllQuizDetailByCourseAndSubjectName(@Param("subjectName")String subjectName, @Param("courseName")String courseName);

    @Query(nativeQuery = true,value="select qd.created from quiz_detail qd where qd.course_name=:courseName and qd.subject_name = :subjectName order by id desc")
    List<Date> getQuizDetailByCreated(@Param("subjectName")String subjectName, @Param("courseName")String courseName);

    @Query(nativeQuery = true,value="select qd.question_template from quiz_detail qd where qd.course_name=:courseName and qd.subject_name = :subjectName and qd.quiz_number = :quizNumber")
    String getQuestionTemplateFromQuizDetail(@Param("subjectName")String subjectName, @Param("courseName")String courseName, @Param("quizNumber")String quizNumber);

}
