package com.web.banbut.controller;

import com.web.banbut.dto.request.PenVariantCreationRequest;
import com.web.banbut.dto.request.PenVariantUpdateRequest;
import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.service.PenService;
import com.web.banbut.service.PenVariantService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pen/variant")
public class PenVariantController {
    private final PenVariantService penVariantService;

    public PenVariantController(PenVariantService penVariantService) {
        this.penVariantService = penVariantService;
    }

    @PostMapping("/add-variant")
    public ApiResponse<Map<String, Object>> createVariant(@RequestBody PenVariantCreationRequest penVariantCreationRequest) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "variant",
                        penVariantService.createPenVariant(penVariantCreationRequest)
                )
        );
    }

    @GetMapping("/{penId}")
    public ApiResponse<Map<String, Object>> getListPenVariantByPenId(@PathVariable String penId) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "variant",
                        penVariantService.getListPenVariantByPenId(penId)
                )
        );
    }

    @GetMapping("/admin/{penId}")
    public ApiResponse<Map<String, Object>> adminGetListPenVariantByPenId(@PathVariable String penId) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "variant",
                        penVariantService.adminGetListPenVariantByPenId(penId)
                )
        );
    }

    @PatchMapping("/admin/{penVariantId}/{isVisible}")
    public ApiResponse<Map<String, Object>> setVisible(@PathVariable String penVariantId, @PathVariable boolean isVisible) {
        penVariantService.setVisible(penVariantId, isVisible);
        return new ApiResponse<>(
                "success",
                Map.of(
                        "variant", "Visibility updated"
                )
        );
    }

    @PatchMapping("/admin/update/{penVariantId}")
    public ApiResponse<Map<String, Object>> updatePenVariant(@PathVariable String penVariantId, @RequestBody PenVariantUpdateRequest penVariantUpdateRequest) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "variant",
                        penVariantService.updatePenVariant(penVariantId, penVariantUpdateRequest)
                )
        );
    }
}
