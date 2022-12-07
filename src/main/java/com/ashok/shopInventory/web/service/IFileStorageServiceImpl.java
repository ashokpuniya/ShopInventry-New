package com.ashok.shopInventory.web.service;

import com.ashok.shopInventory.web.entity.VideoFile;

import java.util.List;

public interface IFileStorageServiceImpl {
    VideoFile getVideoFileByCourseAndSubjectAndMode(String course, String subject, String params);

    VideoFile save(VideoFile fileVideo);

    List<Integer> getFileVideoByCourseAndSubject(String course, String subject, String name);

    List<String> getFileNameByCourseAndSubject(String course, String subject, String name);

    VideoFile getVideoById(Integer valueOf);

    VideoFile getFileByVideoNumberAndSubjectAndCourse(String course, String subject, Integer videoNumber, String name);
}
