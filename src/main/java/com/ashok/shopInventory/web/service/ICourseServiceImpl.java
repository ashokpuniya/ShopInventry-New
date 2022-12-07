package com.ashok.shopInventory.web.service;


import com.ashok.shopInventory.web.entity.Course;

import java.util.List;

public interface ICourseServiceImpl {
    List<String> getAllCourses();
    Course getCourseByName(String name);

    Double getCourseAmount(String course);
}
