package com.web.banbut.dto.response;

public class VerifyOTPResponse {
    private String token;

    public VerifyOTPResponse() {}

    public VerifyOTPResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
