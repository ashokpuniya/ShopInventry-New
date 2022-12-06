package com.ashok.shopInventory.web.controller;


import com.ashok.shopInventory.web.dto.FileManuplationDto;
import com.ashok.shopInventory.web.entity.VideoFile;
import com.ashok.shopInventory.web.enums.ShopItemStatusEnum;
import com.ashok.shopInventory.web.service.IFileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(FileController.PATH)
public class FileController {
    static final String PATH = "/file";


    @Autowired
    private IFileStorageServiceImpl fileStorageService;

    @RequestMapping(path = "/upload/{course}/{subject}")
    public String uploadFileOrVideo(Model model, @PathVariable("course") String course, @PathVariable("subject") String subject) {
       model.addAttribute("course",course);
       model.addAttribute("subject",subject);
        List<String> config = new ArrayList<>();
        config.add(ShopItemStatusEnum.File_Upload.getName());
        config.add(ShopItemStatusEnum.Video_Upload.getName());
        model.addAttribute("params",config);
        return "bulkUpload";
    }
    @RequestMapping(value = "/uploadFile/{course}/{subject}", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String uploadFile(@RequestParam("params") String params, @RequestParam("file") MultipartFile file,Model model,@PathVariable("course") String course, @PathVariable("subject") String subject) {
        try {
            VideoFile fileVideo = new VideoFile();
            fileVideo.setFileMode(params);
            fileVideo.setFileName(file.getOriginalFilename());
            fileVideo.setCourse(course);
            fileVideo.setSubject(subject);
            VideoFile file1 = fileStorageService.getVideoFileByCourseAndSubjectAndMode(course,subject,params);
            if(file==null)
                fileVideo.setVideoNumber(1);
            else {
                fileVideo.setVideoNumber(file1.getVideoNumber()+1);
            }
            fileVideo.setVideoStream(file.getBytes());
            fileStorageService.save(fileVideo);
            List<String> config = new ArrayList<>();
            config.add(ShopItemStatusEnum.File_Upload.getName());
            config.add(ShopItemStatusEnum.Video_Upload.getName());
            model.addAttribute("params",config);
            model.addAttribute("message", params+" successfully !");
            model.addAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            model.addAttribute("message", "Error while uploading file");
        }
        return "bulkUpload";
    }

    @RequestMapping(path = "/showVideo/{course}/{subject}")
    public String showVideoFile(Model model, @PathVariable("course") String course, @PathVariable("subject") String subject) {
        List<Integer> ids= fileStorageService.getFileVideoByCourseAndSubject(course,subject,ShopItemStatusEnum.Video_Upload.getName());
        List<String> fileNames = fileStorageService.getFileNameByCourseAndSubject(course,subject,ShopItemStatusEnum.Video_Upload.getName());
        List<FileManuplationDto> fileDtos = populateFileManuplationDto(ids,fileNames);
        model.addAttribute("fileDtos",fileDtos);
        model.addAttribute("course",course);
        model.addAttribute("subject",subject);
        return "showVideoFile";
    }

    @RequestMapping(path = "/watchVideo/{id}")
    public String watchVideo(Model model, @PathVariable("id") String id) {
        //VideoFile videoFile = fileStorageService.getVideoById(Integer.valueOf(id));
        model.addAttribute("id",id);
        return "watchVideo";
    }




    @RequestMapping(path = "/showVideo/{id}")
    public void showVideo(Model model, @PathVariable("id") String id, HttpServletResponse response) {
        VideoFile videoFile = fileStorageService.getVideoById(Integer.valueOf(id));
        try {
            response.setContentType("video/mp4");
            response.setHeader("Content-Disposition", "attachment; filename="+"myFile.mp4");
            byte[] content = new byte[1024];
            BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(videoFile.getVideoStream()));
            OutputStream os = response.getOutputStream();
            while (is.read(content) != -1) {
                //System.out.println("... write bytes");
                os.write(content);
            }
            is.close();
            os.close();
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @RequestMapping(path = "/showPdf/{id}")
    public void showPdf(Model model, @PathVariable("id") String id,HttpServletResponse httpResponse) throws IOException {

        VideoFile videoFile = fileStorageService.getVideoById(Integer.valueOf(id));
        VideoFile videoFile1 = fileStorageService.getFileByVideoNumberAndSubjectAndCourse(videoFile.getCourse(),videoFile.getSubject(),videoFile.getVideoNumber(),ShopItemStatusEnum.File_Upload.getName());
        httpResponse.setHeader("Content-Type", "application/pdf");
        httpResponse.setHeader("Content-disposition", "inline;filename=" + "fileName1" + ".pdf");
        OutputStream output = httpResponse.getOutputStream();
        httpResponse.setContentLength(videoFile1.getVideoStream().length);
        output.write(videoFile1.getVideoStream());
        output.flush();
        output.close();
    }

//    @RequestMapping(path = "/watchPdf1/{id}")
//    public void watchPdf(Model model, @PathVariable("id") String id, HttpServletResponse httpResponse) throws IOException {
//        VideoFile videoFile = fileStorageService.getVideoById(Integer.valueOf(id));
//        VideoFile videoFile1 = fileStorageService.getFileByVideoNumberAndSubjectAndCourse(videoFile.getCourse(),videoFile.getSubject(),videoFile.getVideoNumber(),ShopItemStatusEnum.File_Upload.getName());
//        httpResponse.setHeader("Content-Type", "application/pdf");
//        httpResponse.setHeader("Content-disposition", "inline;filename=" + "fileName1" + ".pdf");
//        OutputStream output = httpResponse.getOutputStream();
//        httpResponse.setContentLength(videoFile1.getVideoStream().length);
//        output.write(videoFile1.getVideoStream());
//        output.flush();
//        output.close();
//    }

    private List<FileManuplationDto> populateFileManuplationDto(List<Integer> ids, List<String> fileNames) {
        List<FileManuplationDto> fileManuplationDtos = new ArrayList<>();
        for(int i=0;i<ids.size();i++){
            FileManuplationDto fileManuplationDto = new FileManuplationDto();
            fileManuplationDto.setFileName(fileNames.get(i));
            fileManuplationDto.setId(ids.get(i));
            fileManuplationDtos.add(fileManuplationDto);
        }
        return fileManuplationDtos;
    }


}
