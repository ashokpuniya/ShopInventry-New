package com.ashok.shopInventory.web.dto;

import java.io.Serializable;

public class FileManuplationDto implements Serializable {
    private  Integer id;
    private  String fileName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FileManuplationDto{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
