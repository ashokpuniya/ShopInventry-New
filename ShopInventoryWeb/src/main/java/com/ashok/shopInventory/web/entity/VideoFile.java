package com.ashok.shopInventory.web.entity;


import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "video_file")
public class VideoFile {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "video_stream",columnDefinition="LONGBLOB")
    private byte[] videoStream;

    @Column(name = "video_number")
    private Integer videoNumber;

    @Column(name = "course_name")
    private String course;

    @Column(name = "subject_name")
    private String subject;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_mode")
    private String fileMode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getVideoStream() {
        return videoStream;
    }

    public void setVideoStream(byte[] videoStream) {
        this.videoStream = videoStream;
    }

    public Integer getVideoNumber() {
        return videoNumber;
    }

    public void setVideoNumber(Integer videoNumber) {
        this.videoNumber = videoNumber;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileMode() {
        return fileMode;
    }

    public void setFileMode(String fileMode) {
        this.fileMode = fileMode;
    }

    @Override
    public String toString() {
        return "VideoFile{" +
                "id=" + id +
                ", videoStream=" + Arrays.toString(videoStream) +
                ", videoNumber=" + videoNumber +
                ", course='" + course + '\'' +
                ", subject='" + subject + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileMode='" + fileMode + '\'' +
                '}';
    }
}
