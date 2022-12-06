package com.ashok.shopInventory.web.Repository;

import com.ashok.shopInventory.web.entity.CourseSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourserSubjectRepository extends JpaRepository<CourseSubject, Long> {

}
