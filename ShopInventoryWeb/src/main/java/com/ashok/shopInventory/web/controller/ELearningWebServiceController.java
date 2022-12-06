package com.ashok.shopInventory.web.controller;


import com.ashok.shopInventory.web.dto.QuestionParser;
import com.ashok.shopInventory.web.dto.QuestionParserDto;
import com.ashok.shopInventory.web.dto.QuizDetaildto;
import com.ashok.shopInventory.web.dto.ShowResult;
import com.ashok.shopInventory.web.entity.ActionParams;
import com.ashok.shopInventory.web.entity.Course;
import com.ashok.shopInventory.web.entity.QuizResult;
import com.ashok.shopInventory.web.entity.User;
import com.ashok.shopInventory.web.service.*;
import com.ashok.shopInventory.web.service.impl.WebContextUtils;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping(ELearningWebServiceController.PATH)
public class ELearningWebServiceController {
    private static final Logger LOG = LoggerFactory.getLogger(ELearningWebServiceController.class);
    static final String PATH = "/Eedu";
    public static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String PRAGMA = "Pragma";
    public static final String EXPIRES = "Expires";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String NO_CACHE = "no-cache";
    public static final String MAX_AGE_2592000_PUBLIC = "max-age=2592000;public";
    public static final String PNG = "png";
    public static final String IMAGE_PNG = "image/png";



    @Autowired
    private ICourseServiceImpl courseService;

    @Autowired
    private ISubjectServiceImpl subjectService;

    @Autowired
    private IQuizDetailServiceImpl quizDetailService;

    @Autowired
    private IQuizResultServiceImpl quizResultService;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("Eedindex", "Eedindex");
        List<String> courses = courseService.getAllCourses();
        model.addAttribute("courses",courses);
        //model.addAttribute("userId",userId);
        return "Eedindex";
    }

    @RequestMapping(path = "/compitation/{course}")
    public String showEvents(Model model,@PathVariable("course") String course) {
        List<String> subject = new ArrayList<>();//get all subject regarding to course
        Course cr= courseService.getCourseByName(course);
        subject= subjectService.getAllSubjectById(cr.getId());
        if(subject.size()<1)
        {
            model.addAttribute("message","There is No subject related to "+course);
            return "DefaultPage";
        }
        model.addAttribute("subject", subject);
        model.addAttribute("course",course);
        return "showSubjectForEdu";
    }

    @RequestMapping(path = "/showQuiz/{course}/{subject}")
    public String createQuiz(Model model,@PathVariable("course") String course,@PathVariable("subject") String subject) {
        ActionParams actionParams=new ActionParams();
        List<String> quizNumbers= new ArrayList<>();
        //quizNumbers= quizDetailService.getAllQuizDetailByCourseAndSubjectName(subject,course);//get all the quiz numbers corrosponding ot course and subject
        //List<Date> dates = quizDetailService.getQuizDatailByCreated(subject,course);
        List<ActionParams> actionParams1 = quizDetailService.getAllQuizDetailByCourseAndSubjectName(subject,course);
        List<QuizDetaildto> quizDetaildtos = getAllQuizDetailDto(actionParams1);
        if(quizDetaildtos.size()==0)
        {
            //model.addAttribute("userId",userId);
            model.addAttribute("message","There is No Test related to "+subject);
            return "DefaultPage";
        }
        model.addAttribute("course",course);
        model.addAttribute("subject",subject);
        //model.addAttribute("userId",userId);
        model.addAttribute("quizDetaildtos",quizDetaildtos);
        //show all the quiz based on course and subject
        model.addAttribute("fieldTypeMap", getFieldTypeMap(ActionParams.class));
        model.addAttribute("ActionParams", actionParams);
        return "showQuiz";
    }

    @RequestMapping(path = "/startQuiz/{course}/{subject}/{quizNumber}")
    public String startQuiz(Model model,@PathVariable("course") String course,@PathVariable("subject") String subject,@PathVariable("quizNumber") String quizNumber) {
        List<QuestionParser> questionParsers=convertJsontoQuestion(subject,course,quizNumber);

//        String email = WebContextUtils.getCurrentUserEmail();
//        User user = WebContextUtils.getCurrentUser();

        //System.out.println(model);

//        if(user ==null || email ==null){
//            return "loginPage";
//        }


        String email = WebContextUtils.getCurrentUserEmail();
        User user = userService.getUserByName(email);
        List<QuizResult> quizResults = quizResultService.getResultByCourseAndSubjectAndQuizNumberAndUser(course,subject,quizNumber,user.getId());
        if(quizResults !=null && quizResults.size()>0){
            model.addAttribute("message","You Have Already Attempt this Test.");
            return "DefaultPage";
        }
        QuestionParserDto qForm = new QuestionParserDto();
        qForm.setQuizData(questionParsers);
        model.addAttribute("qForm",qForm);
        model.addAttribute("course",course);
        model.addAttribute("subject",subject);
        model.addAttribute("quizNumber",quizNumber);
        return "startQuizNew";
    }
    @RequestMapping(path = "/saveQuiz/{course}/{subject}/{quizNumber}")
    public String saveQuiz(@ModelAttribute QuestionParserDto qForm, Model model, @PathVariable("course") String course, @PathVariable("subject") String subject, @PathVariable("quizNumber") String quizNumber) {
       String email = WebContextUtils.getCurrentUserEmail();
        User user1 = userService.getUserByName(email);
        List<QuizResult> quizResults = quizResultService.getResultByCourseAndSubjectAndQuizNumberAndUser(course,subject,quizNumber,user1.getId());
        if(quizResults.size()>0){
            model.addAttribute("message","You Have Already Attempt this Test.");
            return "DefaultPage";
        }
       int Correct = quizDetailService.saveQuizResult(qForm);
        //String email = WebContextUtils.getCurrentUserEmail();
        //CocofsUser user = WebContextUtils.getCurrentUser();
        model.addAttribute("course",course);
        model.addAttribute("subject",subject);
        model.addAttribute("quizNumber",quizNumber);
        CalculateQuizResultAndSave(course,subject,quizNumber,Correct,qForm.getQuizData().size());
       return "SuccessPage";
    }

    @RequestMapping(path = "/showScoreBoard/{course}/{subject}/{quizNumber}")
    public String showScoreBoard(Model model,@PathVariable("course") String course,@PathVariable("subject") String subject,@PathVariable("quizNumber") String quizNumber) {
        List<ShowResult> showResults = getRankByCourseAndSubjectAndQuizNumber(course,subject,quizNumber);
        //System.out.println(showResults);
        model.addAttribute("results",showResults);
        model.addAttribute("fieldTypeMap", getFieldTypeMap(ShowResult.class));
        return "showScoreBoard";
    }

    @RequestMapping(value = "/getQuizData/{cs}/{sb}/{quizNumber}", method = RequestMethod.GET,produces = "application/json")
    public @ResponseBody List<QuestionParser> addPersons(@PathVariable("cs") String cs, @PathVariable("sb") String sb, @PathVariable("quizNumber") String quizNumber) {
        List<QuestionParser> questionParsers = convertJsontoQuestion(sb,cs,quizNumber);
        return questionParsers;
    }


    private List<QuestionParser> convertJsontoQuestion(String subject, String course, String quizNumber) {
            List<QuestionParser> questionParsers = new ArrayList<>();
            try {
                String template = quizDetailService.getQuestionTemplateFromQuizDetail(subject, course, quizNumber);
                JsonParser parser = new JsonParser();
                Object obj = parser.parse(template);
                JsonObject locationjson = (JsonObject) obj;
                JsonArray location = locationjson.get("quizData").getAsJsonArray();
                for (final JsonElement json : location) {
                    Gson g = new Gson();
                    QuestionParser entity = g.fromJson(json, QuestionParser.class);
                    questionParsers.add(entity);
                }
            }catch (Exception e){
                System.out.println(e);
            }
            return questionParsers;
    }
    private List<QuizDetaildto> getAllQuizDetailDto(List<ActionParams> actionParams1) {
        List<QuizDetaildto> quizDetaildtos = new ArrayList<>();
        if(actionParams1.size()>0){
            for(ActionParams actionParams: actionParams1){
                QuizDetaildto quizDetaildto=new QuizDetaildto();
                quizDetaildto.setQuizNumber(actionParams.getQuizNumber());
                quizDetaildto.setCreated(actionParams.getCreated());
                quizDetaildtos.add(quizDetaildto);
            }
        }
        return quizDetaildtos;
    }

    private Map<String, String> getFieldTypeMap(Class clazz) {
        Map<String, String> fieldNameTypeMap = new LinkedHashMap<>();
        if (clazz == null) {
            return fieldNameTypeMap;
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (true) {
                fieldNameTypeMap.put(field.getName(), String.valueOf(field.getType()));
            }
        }
        return fieldNameTypeMap;
    }

    private void CalculateQuizResultAndSave(String course, String subject, String quizNumber, int correct, int size) {
        try{
//            String email = WebContextUtils.getCurrentUserEmail();
//            User user = WebContextUtils.getCurrentUser();
//            System.out.println(email);
//            System.out.println(user);
            String email = WebContextUtils.getCurrentUserEmail();
            User user1 = userService.getUserByName(email);
            QuizResult quizResult = new QuizResult();
            quizResult.setQuizNumber(quizNumber);
            quizResult.setCourseName(course);
            quizResult.setScore((double)correct);
            quizResult.setTotalScore((double)size);
            quizResult.setSubjectName(subject);
            quizResult.setUserId(user1.getId());
            quizResultService.save(quizResult);
        }catch (Exception e){
            LOG.info("Exception occour during save Result:{}",e);
        }
    }
    private List<ShowResult> getRankByCourseAndSubjectAndQuizNumber(String course, String subject, String quizNumber) {
        List<QuizResult> quizResults = quizResultService.getResultByCourseAndSubjectAndQuizNumber(course,subject,quizNumber);
        System.out.println(quizResults);
          String email = WebContextUtils.getCurrentUserEmail();
//        User user = WebContextUtils.getCurrentUser();
        User user = userService.getUserByName(email);
        List<ShowResult> showResults = new ArrayList<>();
        ShowResult first = new ShowResult();
        Double prev=null;
        Integer count=1;
        boolean flag=false;
        for(QuizResult quizResult:quizResults){
            ShowResult showResult = new ShowResult();
            User userInDb = userService.getUserById(quizResult.getUserId());
            showResult.setUserName(userInDb.getName());
            showResult.setScore(quizResult.getScore());
            showResult.setTotalScore(quizResult.getTotalScore());
            if(prev==null) {
                showResult.setRank(count);
                prev= quizResult.getScore();
            }
            else if(prev == quizResult.getScore()){
                showResult.setRank(count);
            }
            else{
                prev=quizResult.getScore();
                count++;
                showResult.setRank(count);
            }
            //todo
            if(quizResult.getUserId()==user.getId()){
                first=showResult;
                flag=true;
            }
            else {
                showResults.add(showResult);
            }
        }
        if(flag) {
            showResults.add(0, first);
        }
        System.out.println(showResults);

        return showResults;
    }


}
