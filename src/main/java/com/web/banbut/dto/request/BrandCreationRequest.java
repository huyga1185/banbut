package com.web.banbut.dto.request;

public class BrandCreationRequest {
    private String name;

    public BrandCreationRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
