package com.ashok.shopInventory.web.Repository;


import com.ashok.shopInventory.web.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuizResultRepository extends JpaRepository<QuizResult, Long> {

    @Query(nativeQuery = true,value="select * from quiz_result qr where qr.course_name=:course and qr.subject_name = :subject and qr.quiz_number =:quizNumber order by qr.score desc")
    List<QuizResult> getResultByCourseAndSubjectAndQuizNumber(@Param("course")String course, @Param("subject")String subject, @Param("quizNumber") String quizNumber);

    @Query(nativeQuery = true,value="select * from quiz_result qr where qr.course_name=:course and qr.subject_name = :subject and qr.quiz_number =:quizNumber and qr.user_id=:userId ")
    List<QuizResult> getResultByCourseAndSubjectAndQuizNumberAndUser(@Param("course")String course, @Param("subject")String subject, @Param("quizNumber") String quizNumber, @Param("userId")long userId);

}
