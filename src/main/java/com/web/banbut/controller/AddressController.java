package com.web.banbut.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.banbut.dto.request.AddressRequest;
import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ApiResponse<Map<String, Object>> createAddress(Authentication authentication, @RequestBody AddressRequest addressRequest) {
        return new ApiResponse<Map<String,Object>>(
            "success",
            Map.of(
                "address:", addressService.createAddress(authentication, addressRequest)
            ));
    }

    @GetMapping("/list")
    public ApiResponse<Map<String, Object>> getListAddress(Authentication authentication) {
        return new ApiResponse<Map<String,Object>>(
            "success", 
            Map.of(
                "addresses", addressService.getListAddress(authentication)
            )
        );
    }

    @GetMapping("{addressID}")
    public ApiResponse<Map<String, Object>> getAddress(@PathVariable String addressID) {
        return new ApiResponse<Map<String,Object>>(
            "success",
            Map.of(
                "address", addressService.getAddress(addressID)
            )
        );
    }

    @DeleteMapping("/{addressID}")
    public ApiResponse<String> deleteAddress(@PathVariable String addressID) {
        addressService.deleteAddress(addressID);
        return new ApiResponse<String>(
            "success","Address deleted"
        );
    }

    @PatchMapping("/{addressID}")
    public ApiResponse<Map<String, Object>> updateAddress(@PathVariable String addressID, @RequestBody AddressRequest addressRequest) {
        return new ApiResponse<Map<String,Object>>(
            "success",
            Map.of(
                "address:", addressService.updateAddress(addressID, addressRequest)
            )
        );
    } 
}
