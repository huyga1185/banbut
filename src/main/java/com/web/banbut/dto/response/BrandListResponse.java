package com.web.banbut.dto.response;

import java.util.List;

public class BrandListResponse {
    List<BrandResponse> listBrand;

    public BrandListResponse() {}

    public BrandListResponse(List<BrandResponse> listBrand) {
        this.listBrand = listBrand;
    }

    public List<BrandResponse> getListBrand() {
        return listBrand;
    }

    public void setListBrand(List<BrandResponse> listBrand) {
        this.listBrand = listBrand;
    }
}
