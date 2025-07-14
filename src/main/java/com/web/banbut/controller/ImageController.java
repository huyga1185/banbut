package com.web.banbut.controller;

import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{imageId}")
    public ApiResponse<Map<String, Object>> getImage(@PathVariable String imageId) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "image", imageService.getImage(imageId)
                )
        );
    }

    @PostMapping("/pen/{penId}/uploads")
    public ApiResponse<Map<String, Object>> uploadImage(@PathVariable String penId, @RequestParam("file") MultipartFile file) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "image", imageService.uploadImage(penId, file)
                )
        );
    }

    @DeleteMapping("/{imageId}")
    public ApiResponse<Map<String, Object>> deleteImage(@PathVariable String imageId) {
        imageService.deleteImage(imageId);
        return new ApiResponse<>(
                "success",
                Map.of(
                        "image", "image deleted"
                )
        );
    }
}
