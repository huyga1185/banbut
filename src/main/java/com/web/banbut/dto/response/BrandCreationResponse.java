package com.web.banbut.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BrandCreationResponse {
    @JsonProperty("brand_id")
    private String brandId;
    private String name;

    public BrandCreationResponse(String brandId, String name) {
        this.brandId = brandId;
        this.name = name;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
