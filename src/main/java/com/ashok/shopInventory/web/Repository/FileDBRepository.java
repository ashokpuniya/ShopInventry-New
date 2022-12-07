package com.ashok.shopInventory.web.Repository;

import com.ashok.shopInventory.web.entity.VideoFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDBRepository extends JpaRepository<VideoFile, Long> {

    @Query(nativeQuery = true,value="select * from video_file c where c.course_name=:course and c.subject_name=:subject and c.file_mode=:name order by id desc limit 1")
    VideoFile getVideoFileByCourseAndSubjectAndMode(@Param("course") String course, @Param("subject") String subject, @Param("name") String name);

    @Query(nativeQuery = true,value="select c.id from video_file c where c.course_name=:course and c.subject_name=:subject and c.file_mode=:name")
    List<Integer> getFileVideoByCourseAndSubject(@Param("course") String course, @Param("subject") String subject, @Param("name") String name);

    @Query(nativeQuery = true,value="select c.file_name from video_file c where c.course_name=:course and c.subject_name=:subject and c.file_mode=:name")
    List<String> getFileNameByCourseAndSubject(@Param("course") String course, @Param("subject") String subject, @Param("name") String name);

    @Query(nativeQuery = true,value="select * from video_file c where c.id=:id")
    VideoFile getVideoById(@Param("id") Integer id);

    @Query(nativeQuery = true,value="select * from video_file c where c.course_name=:course and c.subject_name=:subject and c.file_mode=:name and c.video_number=:videoNumber")
    VideoFile getFileByCourseAndSubjectAndMode(@Param("course") String course, @Param("subject") String subject,@Param("videoNumber") Integer videoNumber, @Param("name") String name);
}
