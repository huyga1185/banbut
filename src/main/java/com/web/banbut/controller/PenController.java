package com.web.banbut.controller;

import com.web.banbut.dto.request.PenCreationRequest;
import com.web.banbut.dto.request.PenUpdateRequest;
import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.service.PenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pen")
public class PenController {
    private final PenService penService;

    public PenController(PenService penService) {
        this.penService = penService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-pen")
    public ApiResponse<Map<String, Object>> createPen(@RequestBody PenCreationRequest penCreationRequest) {
        return new ApiResponse<>("success", Map.of(
                "pen", penService.createPen(penCreationRequest)
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update-pen/{penId}")
    public ApiResponse<Map<String, Object>> updatePen(@PathVariable String penId, @RequestBody PenUpdateRequest penUpdateRequest) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "pen", penService.updatePen(penId, penUpdateRequest)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/set-visible/{penId}/{isVisible}")
    public ApiResponse<Map<String, String>> setVisible(@PathVariable String penId, @PathVariable boolean isVisible) {
        penService.setVisible(penId, isVisible);
        return new ApiResponse<>(
                "success",
                Map.of(
                        "message","Visibility updated"
                )
        );
    }

    @GetMapping("/{penId}")
    public ApiResponse<Map<String, Object>> getPenById(@PathVariable String penId) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "pen", penService.getPenById(penId)
                )
        );
    }

    @GetMapping("/get-list-preview")
    public ApiResponse<Map<String, Object>> getListPreview(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return new ApiResponse<>(
                "success",
                Map.of(
                    "pens: ", penService.getListPenPreview(page, size)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/get-list")
    public ApiResponse<Map<String, Object>> adminGetListPen() {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "pen: ", penService.adminGetListPen()
                )
        );
    }
}
