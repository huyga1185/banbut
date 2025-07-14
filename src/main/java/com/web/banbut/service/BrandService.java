package com.web.banbut.service;

import com.web.banbut.dto.request.BrandCreationRequest;
import com.web.banbut.dto.request.BrandUpdateRequest;
import com.web.banbut.dto.response.BrandCreationResponse;
import com.web.banbut.dto.response.BrandListResponse;
import com.web.banbut.dto.response.BrandResponse;
import com.web.banbut.dto.response.BrandUpdateResponse;
import com.web.banbut.entity.Brand;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.BrandRepository;
import com.web.banbut.repository.PenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final PenRepository penRepository;

    public BrandService(BrandRepository brandRepository, PenRepository penRepository) {
        this.brandRepository = brandRepository;
        this.penRepository = penRepository;
    }

    public BrandCreationResponse createBrand(BrandCreationRequest brandCreationRequest) {
        if (brandRepository.existsByName(brandCreationRequest.getName()))
            throw new AppException(ErrorCode.BRAND_EXISTED);
        Brand brand = brandRepository.save(new Brand(brandCreationRequest.getName()));
        return new BrandCreationResponse(brand.getBrandId(), brand.getName());
    }

    public BrandUpdateResponse updateBrand(BrandUpdateRequest brandUpdateRequest) {
        if (brandRepository.existsByName(brandUpdateRequest.getName()))
            throw new AppException(ErrorCode.BRAND_NAME_EXISTED);
        Brand brand = brandRepository.findById(brandUpdateRequest.getBrandId()).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        brand.setName(brandUpdateRequest.getName());
        brand.setUpdatedAt(LocalDateTime.now());
        brand = brandRepository.save(brand);
        return new BrandUpdateResponse(
                brand.getBrandId(),
                brand.getUpdatedAt(),
                brand.getCreatedAt(),
                brand.getName()
        );
    }

    public BrandListResponse getListBrands() {
        List<BrandResponse> brandResponses = new ArrayList<>();
        List<Brand> brands = brandRepository.findAll();
        for (Brand brand : brands) {
            BrandResponse brandResponse = new BrandResponse(
                    brand.getBrandId(),
                    brand.getUpdatedAt(),
                    brand.getCreatedAt(),
                    brand.getName()
            );
            brandResponses.add(brandResponse);
        }
        return new BrandListResponse(brandResponses);
    }

    public void deleteBrand(String brandId) {
        if (!brandRepository.existsById(brandId))
            throw new AppException(ErrorCode.BRAND_NOT_FOUND);
        else if (penRepository.existsPenByBrand_BrandId(brandId))
            throw new AppException(ErrorCode.BRAND_HAS_PRODUCTS);
        brandRepository.deleteById(brandId);
    }
}
