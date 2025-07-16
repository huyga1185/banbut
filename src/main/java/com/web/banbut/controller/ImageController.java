package com.web.banbut.controller;

import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(value = "/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageFile(@PathVariable String name) {
        return imageService.getImageFile(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/pen/{penId}/uploads")
    public ApiResponse<Map<String, Object>> uploadImage(@PathVariable String penId, @RequestParam("file") MultipartFile file) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "image", imageService.uploadImage(penId, file)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
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
