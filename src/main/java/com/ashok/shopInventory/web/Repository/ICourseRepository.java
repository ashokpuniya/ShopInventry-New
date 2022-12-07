package com.ashok.shopInventory.web.Repository;


import com.ashok.shopInventory.web.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Long> {

    @Query(nativeQuery = true,value="select c.course_name from course c")
    List<String> getAllCourses();

    @Query(nativeQuery = true,value="select * from course s where s.course_name= :name")
    Course getCourseByName(@Param("name")String name);

    @Query(nativeQuery = true,value="select s.amount from course s where s.course_name= :course")
    Double getCourseAmount(@Param("course") String course);
}
