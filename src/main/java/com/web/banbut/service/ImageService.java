package com.web.banbut.service;

import com.web.banbut.dto.response.FileResponse;
import com.web.banbut.dto.response.ImageResponse;
import com.web.banbut.dto.response.ImageUploadResponse;
import com.web.banbut.entity.Image;
import com.web.banbut.entity.Pen;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.ImageRepository;
import com.web.banbut.repository.PenRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {
    private final FileStorageService fileStorageService;
    private final PenRepository penRepository;
    private final ImageRepository imageRepository;

    public ImageService(FileStorageService fileStorageService, PenRepository penRepository, ImageRepository imageRepository) {
        this.fileStorageService = fileStorageService;
        this.penRepository = penRepository;
        this.imageRepository = imageRepository;
    }

    public ResponseEntity<byte[]> getImageFile(String name) {
        Image image = imageRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_FOUND));

        Path imagePath = Paths.get("uploads", image.getName());

        try {
            byte[] imageBytes = Files.readAllBytes(imagePath);

            // Optional: tự xác định định dạng ảnh
            String contentType = Files.probeContentType(imagePath);

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageBytes);

        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_DOES_NOT_EXISTS);
        }
    }


    public ImageUploadResponse uploadImage(String penId, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Pen pen = penRepository.findById(penId).orElseThrow(() -> new AppException(ErrorCode.PEN_NOT_FOUND));
        if (imageRepository.existsByName(fileName))
            throw new AppException(ErrorCode.IMAGE_EXISTED);
        Image image = new Image(fileName, pen);
        image = imageRepository.save(image);
        fileStorageService.save(file);
        FileResponse fileResponse = fileStorageService.buildFileResponseFromStoredFile(fileName);
        return new ImageUploadResponse(
                image.getImageId(),
                fileResponse,
                image.getCreatedAt(),
                image.getPen().getPenId()
        );
    }

    public void deleteImage(String imageId) {
        String fileName = imageRepository.findById(imageId).orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_FOUND)).getName();
        fileStorageService.deleteByName(fileName);
        imageRepository.deleteById(imageId);
    }
}
