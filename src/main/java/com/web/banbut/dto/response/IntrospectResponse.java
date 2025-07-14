package com.web.banbut.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IntrospectResponse {
    private boolean active;
    private String sub;
    private String role;
    private Long exp;

    public IntrospectResponse(boolean active, String sub, String role, Long exp) {
        this.active = active;
        this.sub = sub;
        this.role = role;
        this.exp = exp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }
}
