package com.web.banbut.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileResponse {
    @JsonProperty("file_name")
    private String fileName;
    private String url;
    @JsonProperty("file_type")
    private String fileType;
    private long size;

    public FileResponse(String fileName, String url, String fileType, long size) {
        this.fileName = fileName;
        this.url = url;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
