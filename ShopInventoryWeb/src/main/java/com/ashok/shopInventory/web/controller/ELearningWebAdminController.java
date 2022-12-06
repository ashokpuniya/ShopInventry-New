package com.ashok.shopInventory.web.controller;


import com.ashok.shopInventory.web.entity.ActionParams;
import com.ashok.shopInventory.web.entity.Course;
import com.ashok.shopInventory.web.service.ICourseServiceImpl;
import com.ashok.shopInventory.web.service.IQuizDetailServiceImpl;
import com.ashok.shopInventory.web.service.ISubjectServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.lang.reflect.Field;
import java.util.*;

@Controller
@RequestMapping(ELearningWebAdminController.PATH)
public class ELearningWebAdminController {
    private static final Logger LOG = LoggerFactory.getLogger(ELearningWebAdminController.class);
    static final String PATH = "/Admin";

    @Autowired
    private ICourseServiceImpl courseService;

    @Autowired
    private ISubjectServiceImpl subjectService;

    @Autowired
    private IQuizDetailServiceImpl quizDetailService;

    @RequestMapping(path = {"/"}, method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("Eindex", "Eindex");
        List<String> courses = courseService.getAllCourses();
        model.addAttribute("courses",courses);
        return "Eindex";
    }

    @RequestMapping(path = "/compitation/{course}")
    public String showEvents(Model model,@PathVariable("course") String course) {
        List<String> subject = new ArrayList<>();//get all subject regarding to course
        Course cr= courseService.getCourseByName(course);
        subject= subjectService.getAllSubjectById(cr.getId());
        model.addAttribute("subject", subject);
        model.addAttribute("course",course);
        return "showSubjects";
    }

    @RequestMapping(path = "/createQuiz/{course}/{subject}")
    public String createQuiz(Model model,@PathVariable("course") String course,@PathVariable("subject") String subject) {
        //List<String> subjects = new ArrayList<>();
        //get quizNumber based on co
        ActionParams actionParam= quizDetailService.getQuizDetailByCourseNameAndSubjectName(subject,course,1);
        ActionParams actionParams=new ActionParams();
        String quizNumber="1";
        if(actionParam!=null){
            quizNumber=String.valueOf(Integer.parseInt(actionParam.getQuizNumber())+1);
        }
        actionParams.setCourseName(course);
        actionParams.setQuizNumber(quizNumber);
        actionParams.setSubjectName(subject);
        model.addAttribute("fieldTypeMap", getFieldTypeMap(ActionParams.class));
        model.addAttribute("ActionParams", actionParams);
        return "createQuiz";
    }

    @RequestMapping(path = "/saveQuiz", method = RequestMethod.POST)
    public String createAction(ActionParams action) {
        LOG.info("creating action {}", action);
        action.setCreated(getCurrentDate());
        String []sub = action.getSubjectName().split(",",2);
        String []cs= action.getCourseName().split(",",2);
        String []qz= action.getQuizNumber().split(",",2);
        action.setSubjectName(sub[0]);
        action.setCourseName(cs[0]);
        action.setQuizNumber(qz[0]);
        System.out.println(action);
        ActionParams a = quizDetailService.saveQuizDetail(action);
        return "redirect:/Admin/";
    }

    private Map<String, String> getFieldTypeMap(Class clazz) {
        Map<String, String> fieldNameTypeMap = new LinkedHashMap<>();
        if (clazz == null) {
            return fieldNameTypeMap;
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(JoinColumn.class)) {
                fieldNameTypeMap.put(field.getName(), String.valueOf(field.getType()));
            }
        }
        return fieldNameTypeMap;
    }
    public static Date getCurrentDate() {
        Calendar now = Calendar.getInstance();
        now.set(now.get(1), now.get(2), now.get(5), 0, 0, 0);
        now.set(14, 0);
        return now.getTime();
    }
}
