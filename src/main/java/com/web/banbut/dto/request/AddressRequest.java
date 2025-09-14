package com.web.banbut.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressRequest {
    private String address;
    private String district;
    private String name;
    private String province;
    private String ward;
    @JsonProperty("phone_number")
    private long phoneNumber;

    public AddressRequest(
        String address, 
        String district, 
        String name, 
        String province, 
        String ward,
        long phoneNumber
    ) {
        this.address = address;
        this.district = district;
        this.name = name;
        this.province = province;
        this.ward = ward;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    
}
