package com.web.banbut.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateEmailRequest {
    @JsonProperty("new_email")
    private String newEmail;
    @JsonProperty("old_email")
    private String oldEmail;

    public UpdateEmailRequest() {}

    public UpdateEmailRequest(
        String newEmail,
        String oldEmail
    ) {
        this.newEmail = newEmail;
        this.oldEmail = oldEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }
}
