package com.web.banbut.dto.request;

import com.web.banbut.dto.response.VerifyOTPResponse;

public class VerifyOTPRequest {
    private String otp;
    private String email;

    public VerifyOTPRequest() {}

    public VerifyOTPRequest(String otp, String email) {
        this.otp = otp;
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
