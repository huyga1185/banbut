package com.web.banbut.dto.request;

public class CategoryCreationRequest {
    private String name;

    public CategoryCreationRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
