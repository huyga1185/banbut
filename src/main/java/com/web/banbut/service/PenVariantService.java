package com.web.banbut.service;

import com.web.banbut.dto.request.PenVariantCreationRequest;
import com.web.banbut.dto.request.PenVariantUpdateRequest;
import com.web.banbut.dto.response.PenResponse;
import com.web.banbut.dto.response.PenVariantCreationResponse;
import com.web.banbut.dto.response.PenVariantResponse;
import com.web.banbut.entity.Pen;
import com.web.banbut.entity.PenVariant;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.PenRepository;
import com.web.banbut.repository.PenVariantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PenVariantService {
    private final PenRepository penRepository;
    private final PenVariantRepository penVariantRepository;

    public PenVariantService(PenVariantRepository penVariantRepository, PenRepository penRepository) {
        this.penVariantRepository = penVariantRepository;
        this.penRepository = penRepository;
    }

    public PenVariantCreationResponse createPenVariant(PenVariantCreationRequest penVariantCreationRequest) {
        Pen pen = penRepository.findById(penVariantCreationRequest.getPenId()).orElseThrow(() -> new AppException(ErrorCode.PEN_NOT_FOUND));
        PenVariant newPenVariant = penVariantRepository.save(new PenVariant(
                penVariantCreationRequest.getColor(),
                penVariantCreationRequest.getQuantity(),
                penVariantCreationRequest.getPrice(),
                penVariantCreationRequest.getTip(),
                pen
        ));
        return new PenVariantCreationResponse(
                newPenVariant.getPenVariantId(),
                newPenVariant.getTip(),
                newPenVariant.getPrice(),
                newPenVariant.getColor(),
                newPenVariant.getQuantity(),
                penVariantCreationRequest.getPenId()
        );
    }

    public List<PenVariantResponse> getListPenVariantByPenId(String penId) {
        List<PenVariantResponse> penVariantResponses = new ArrayList<>();
        Pen pen = penRepository.findById(penId).orElseThrow(() -> new AppException(ErrorCode.PEN_NOT_FOUND));
        for (PenVariant penVariant : pen.getPenVariants()) {
            if (penVariant.isVisible()) {
                penVariantResponses.add(new PenVariantResponse(
                        penVariant.getPenVariantId(),
                        pen.getPenId(),
                        penVariant.getColor(),
                        penVariant.getQuantity(),
                        penVariant.getPrice(),
                        penVariant.getTip(),
                        penVariant.isVisible(),
                        penVariant.getCreatedAt(),
                        penVariant.getUpdatedAt()
                ));
            }
        }
        return penVariantResponses;
    }

    //admin
    public List<PenVariantResponse> adminGetListPenVariantByPenId(String penId) {
        List<PenVariantResponse> penVariantResponses = new ArrayList<>();
        Pen pen = penRepository.findById(penId).orElseThrow(() -> new AppException(ErrorCode.PEN_NOT_FOUND));
        for (PenVariant penVariant : pen.getPenVariants()) {
            penVariantResponses.add(new PenVariantResponse(
                    penVariant.getPenVariantId(),
                    pen.getPenId(),
                    penVariant.getColor(),
                    penVariant.getQuantity(),
                    penVariant.getPrice(),
                    penVariant.getTip(),
                    penVariant.isVisible(),
                    penVariant.getCreatedAt(),
                    penVariant.getUpdatedAt()
            ));
        }
        return penVariantResponses;
    }

    public void setVisible(String penVariantId, boolean visible) {
        PenVariant penVariant = penVariantRepository.findById(penVariantId).orElseThrow(() -> new AppException(ErrorCode.PEN_VARIANT_NOT_FOUND));
        penVariant.setVisible(visible);
        penVariantRepository.save(penVariant);
    }

    public PenVariantResponse updatePenVariant(String penVariantId, PenVariantUpdateRequest penVariantUpdateRequest) {
        PenVariant penVariant = penVariantRepository.findById(penVariantId).orElseThrow(() -> new AppException(ErrorCode.PEN_VARIANT_NOT_FOUND));
        if (penVariantUpdateRequest.getColor() != null)
            penVariant.setColor(penVariantUpdateRequest.getColor());
        if (penVariantUpdateRequest.getQuantity() != null)
            penVariant.setQuantity(penVariant.getQuantity());
        if (penVariantUpdateRequest.getPrice() != null)
            penVariant.setPrice(penVariantUpdateRequest.getPrice());
        if (penVariantUpdateRequest.getTip() != null)
            penVariant.setTip(penVariantUpdateRequest.getTip());
        if (penVariantUpdateRequest.isVisible() != null)
            penVariant.setVisible(penVariantUpdateRequest.isVisible());
        penVariant = penVariantRepository.save(penVariant);
        return new PenVariantResponse(
                penVariant.getPenVariantId(),
                penVariant.getPen().getPenId(),
                penVariant.getColor(),
                penVariant.getQuantity(),
                penVariant.getPrice(),
                penVariant.getTip(),
                penVariant.isVisible(),
                penVariant.getCreatedAt(),
                penVariant.getUpdatedAt()
        );
    }
}
