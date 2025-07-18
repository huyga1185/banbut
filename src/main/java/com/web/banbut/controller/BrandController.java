package com.web.banbut.controller;

import com.web.banbut.dto.request.BrandCreationRequest;
import com.web.banbut.dto.request.BrandUpdateRequest;
import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.service.BrandService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-brand")
    public ApiResponse<Map<String, Object>> createBrand(@RequestBody BrandCreationRequest brandCreationRequest) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "brand", brandService.createBrand(brandCreationRequest)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ApiResponse<Map<String, Object>> updateBrand(@RequestBody BrandUpdateRequest brandUpdateRequest) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "brand", brandService.updateBrand(brandUpdateRequest)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("get-list")
    public ApiResponse<Map<String, Object>> getListBrands() {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "brand", brandService.getListBrands()
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{brandId}")
    public ApiResponse<Map<String, Object>> deleteCategory(@PathVariable String brandId) {
        brandService.deleteBrand(brandId);
        return new ApiResponse<>(
                "success",
                Map.of(
                        "message", "Brand deleted"
                )
        );
    }
}
