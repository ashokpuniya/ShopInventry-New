package com.ashok.shopInventory.web.Repository;


import com.ashok.shopInventory.web.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubjectRepository extends JpaRepository<Subject, Long> {

    @Query(nativeQuery = true,value="select s.subject_name from subject s where s.id in (select cs.subject_id from course_subject cs where cs.course_id=:id)")
    List<String> getAllSubjectById(@Param("id") Long id);

}
