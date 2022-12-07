package com.ashok.shopInventory.web.service.impl;


import com.ashok.shopInventory.web.Repository.ICourserSubjectRepository;
import com.ashok.shopInventory.web.Repository.ISubjectRepository;
import com.ashok.shopInventory.web.service.ISubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectService implements ISubjectServiceImpl {

    @Autowired
    private ISubjectRepository subjectRepository;

    @Autowired
    private ICourserSubjectRepository courserSubjectRepository;

    @Transactional
    @Override
    public List<String> getAllSubjectById(Long id) {
        return subjectRepository.getAllSubjectById(id);
    }

}
