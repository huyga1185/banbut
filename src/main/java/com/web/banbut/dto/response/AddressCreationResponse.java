package com.web.banbut.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressCreationResponse {
    @JsonProperty("address_id")
    private String addressID;
    private String address;
    private String district;
    private String name;
    private String province;
    private String ward;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("phone_number")
    private long phoneNumber;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public AddressCreationResponse() {}

    public AddressCreationResponse(
        String addressID, 
        String address, 
        String district, 
        String name, 
        String province,
        String ward, 
        String userId, 
        long phoneNumber, 
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.addressID = addressID;
        this.address = address;
        this.district = district;
        this.name = name;
        this.province = province;
        this.ward = ward;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAddressID() {
        return addressID;
    }
    public void setAddressID(String addressID) {
        this.addressID = addressID;
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
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public long getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
