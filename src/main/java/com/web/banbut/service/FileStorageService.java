package com.web.banbut.service;

import com.web.banbut.dto.response.FileResponse;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final Path root = Paths.get("uploads");

    public void resetStorage() {
        try {
            FileSystemUtils.deleteRecursively(root);
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new AppException(ErrorCode.COULD_NOT_RESET_UPLOAD_FOLDER);
        }
    }

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e)
        {
            throw new AppException(ErrorCode.COULD_NOT_CREATE_FOLDER);
        }
    }

    private String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    public void save(MultipartFile multipartFile) {
        try {
            String originalFileName = multipartFile.getOriginalFilename();

            if (originalFileName == null)
                throw new AppException(ErrorCode.INVALID_FILE);

            String extension = getExtension(originalFileName).toLowerCase();

            if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png"))
                throw new AppException(ErrorCode.INVALID_EXTENSION);

            Files.copy(multipartFile.getInputStream(), this.root.resolve(originalFileName));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException)
                throw new AppException(ErrorCode.FILE_NAME_EXISTED);
            throw new AppException(ErrorCode.COULD_NOT_STORE_THE_FILE);
        }
    }

    public Resource load(String fileName) {
        try {
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable())
                return resource;
            else
                throw new AppException(ErrorCode.COULD_NOT_READ_THE_FILE);
        } catch (MalformedURLException e) {
            throw new AppException(ErrorCode.INVALID_FILE_URL);
        }
    }

    public void deleteByName(String fileName) {
        try {
            Path filePath = root.resolve(fileName);
            boolean isSuccess = Files.deleteIfExists(filePath);
            if (!isSuccess)
                throw new AppException(ErrorCode.FILE_DOES_NOT_EXISTS);
        } catch (IOException e) {
            throw new AppException(ErrorCode.COULD_NOT_DELETE_THE_FILE);
        }
    }

    public FileResponse buildFileResponseFromStoredFile(String fileName) {
        try {
            Path filePath = root.resolve(fileName);
            long size = Files.size(filePath);
            String fileType = Files.probeContentType(filePath);

            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/images/")
                    .path(fileName)
                    .toUriString();

            return new FileResponse(fileName, downloadUrl, fileType, size);
        } catch (IOException e) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
    }
}
