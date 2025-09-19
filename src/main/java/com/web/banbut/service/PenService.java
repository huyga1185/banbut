package com.web.banbut.service;

import com.web.banbut.dto.request.PenCreationRequest;
import com.web.banbut.dto.request.PenUpdateRequest;
import com.web.banbut.dto.request.PenVariantCreationRequest;
import com.web.banbut.dto.response.*;
import com.web.banbut.entity.*;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.BrandRepository;
import com.web.banbut.repository.CategoryRepository;
import com.web.banbut.repository.PenRepository;
import com.web.banbut.repository.PenVariantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PenService {
    private final PenVariantService penVariantService;
    private final PenRepository penRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final PenVariantRepository penVariantRepository;
    private final FileStorageService fileStorageService;

    public PenService(PenVariantService penVariantService, PenRepository penRepository, BrandRepository brandRepository, CategoryRepository categoryRepository, PenVariantRepository penVariantRepository, FileStorageService fileStorageService) {
        this.penVariantService = penVariantService;
        this.penRepository = penRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.penVariantRepository = penVariantRepository;
        this.fileStorageService = fileStorageService;
    }

    public PenCreationResponse createPen(PenCreationRequest penCreationRequest)  {
        Brand brand = brandRepository.findById(penCreationRequest.getBrandId()).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        Category category = categoryRepository.findById(penCreationRequest.getCategoryId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        Pen pen = penRepository.save(new Pen(
                penCreationRequest.getName(),
                brand,
                category
        ));
        PenVariantCreationResponse penVariant = penVariantService.createPenVariant(new PenVariantCreationRequest(
                pen.getPenId(),
                penCreationRequest.getTip(),
                penCreationRequest.getPrice(),
                penCreationRequest.getColor(),
                penCreationRequest.getQuantity()
        ));
        return new PenCreationResponse(
                pen.getPenId(),
                penVariant.getTip(),
                penVariant.getQuantity(),
                category.getCategoryId(),
                penVariant.getColor(),
                brand.getBrandId(),
                pen.isVisible(),
                penVariant.getPrice(),
                pen.getName()
        );
    }

    public PenUpdateResponse updatePen(String penId, PenUpdateRequest penUpdateRequest) {
        Pen pen = penRepository.findById(penId).orElseThrow(() -> new AppException(ErrorCode.PEN_NOT_FOUND));

        if (penUpdateRequest.getName() != null)
            pen.setName(penUpdateRequest.getName());
        if (penUpdateRequest.isVisible() != null)
            pen.setVisible(penUpdateRequest.isVisible());
        if (penUpdateRequest.getBrandId() != null) {
            Brand brand = brandRepository.findById(penUpdateRequest.getBrandId()).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
            pen.setBrand(brand);
        }
        if (penUpdateRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(penUpdateRequest.getCategoryId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            pen.setCategory(category);
        }

        pen.setUpdatedAt(LocalDateTime.now());
        pen = penRepository.save(pen);
        return new PenUpdateResponse(
                pen.getPenId(),
                pen.getName(),
                pen.isVisible(),
                pen.getBrand().getBrandId(),
                pen.getCategory().getCategoryId(),
                pen.getCreatedAt(),
                pen.getUpdatedAt()
        );
    }

    public void setVisible(String penId, boolean isVisible) {
        Pen pen = penRepository.findById(penId).orElseThrow(
                () -> new AppException(ErrorCode.PEN_NOT_FOUND)
        );
        pen.setVisible(isVisible);
        penVariantRepository.disableAllByPenId(penId);
        penRepository.save(pen);
    }

    public PenResponse getPenById(String penId) {
        Pen pen = penRepository.findById(penId).orElseThrow(() -> new AppException(ErrorCode.PEN_NOT_FOUND));
        String imageUrl = null;
        Set<Image> images = pen.getImages();
        if (images != null && !images.isEmpty()) {
            FileResponse fileResponse = fileStorageService.buildFileResponseFromStoredFile(images.iterator().next().getName());
            imageUrl = fileResponse.getUrl();
        }
        return new PenResponse(
                pen.getPenId(),
                pen.getName(),
                pen.isVisible(),
                pen.getBrand().getBrandId(),
                pen.getCategory().getCategoryId(),
                pen.getCreatedAt(),
                pen.getUpdatedAt(),
                imageUrl
        );
    }

    public Page<PenPreviewResponse> getListPenPreview(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Pen> pens = penRepository.findAll(pageable);
        List<PenPreviewResponse> penPreviewResponses = new ArrayList<>();
        if (!pens.isEmpty()) {
            for (Pen pen : pens) {
                if (!pen.isVisible())
                    continue;
                String imageUrl = null;
                Set<Image> images = pen.getImages();
                Set<PenVariant> penVariants = pen.getPenVariants();
                PenVariant penVariant = null;
                if (images != null && !images.isEmpty()) {
                    FileResponse fileResponse = fileStorageService.buildFileResponseFromStoredFile(images.iterator().next().getName());
                    imageUrl = fileResponse.getUrl();
                }
                if (penVariants != null && !penVariants.isEmpty()) {
                    for (PenVariant tempPenVariant : penVariants) {
                        if (tempPenVariant.isVisible()) {
                            penVariant = tempPenVariant;
                            break;
                        }
                    }
                }
                if (penVariant == null)
                    continue;
                penPreviewResponses.add(new PenPreviewResponse(
                        pen.getPenId(),
                        pen.getName(),
                        penVariant.getPrice(),
                        imageUrl
                ));
            }
        }
        return new PageImpl<PenPreviewResponse>(penPreviewResponses, pageable, pens.getTotalElements());
    }
    
    //admin
    public List<PenResponse> adminGetListPen() {
        List<Pen> pens = penRepository.findAll();
        List<PenResponse> penResponses = new ArrayList<>();
        for (Pen pen : pens) {
            penResponses.add( new PenResponse(
                    pen.getPenId(),
                    pen.getName(),
                    pen.isVisible(),
                    pen.getBrand().getBrandId(),
                    pen.getCategory().getCategoryId(),
                    pen.getCreatedAt(),
                    pen.getUpdatedAt(),
                    null
            ));
        }
        return penResponses;
    }
}
