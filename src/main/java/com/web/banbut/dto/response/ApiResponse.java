package com.web.banbut.dto.response;

public class ApiResponse<T> {
    private String status;

    private T data;

    private T error;

    public ApiResponse(String status, T data, T error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public ApiResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getError() {
        return error;
    }

    public void setError(T error) {
        this.error = error;
    }
}
