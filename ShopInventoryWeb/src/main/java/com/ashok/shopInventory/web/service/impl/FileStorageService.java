package com.ashok.shopInventory.web.service.impl;

import com.ashok.shopInventory.web.Repository.FileDBRepository;
import com.ashok.shopInventory.web.entity.VideoFile;
import com.ashok.shopInventory.web.service.IFileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service("FileStorageService")
public class FileStorageService implements IFileStorageServiceImpl {

    @Autowired
    private FileDBRepository fileDBRepository;

    @Override
    public VideoFile getVideoFileByCourseAndSubjectAndMode(String course, String subject, String params) {
        return fileDBRepository.getVideoFileByCourseAndSubjectAndMode(course,subject,params);
    }

    @Override
    public VideoFile save(VideoFile fileVideo) {
        return fileDBRepository.save(fileVideo);
    }

    @Override
    public List<Integer> getFileVideoByCourseAndSubject(String course, String subject, String name) {
        return fileDBRepository.getFileVideoByCourseAndSubject(course,subject,name);
    }

    @Override
    public List<String> getFileNameByCourseAndSubject(String course, String subject, String name) {
        return fileDBRepository.getFileNameByCourseAndSubject(course,subject,name);
    }

    @Override
    public VideoFile getVideoById(Integer valueOf) {
        return fileDBRepository.getVideoById(valueOf);
    }

    @Override
    public VideoFile getFileByVideoNumberAndSubjectAndCourse(String course, String subject, Integer videoNumber, String name) {
        return fileDBRepository.getFileByCourseAndSubjectAndMode(course,subject,videoNumber,name);
    }
}


