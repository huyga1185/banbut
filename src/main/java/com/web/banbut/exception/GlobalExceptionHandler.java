package com.web.banbut.exception;

import com.web.banbut.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Object>> handleAppException(AppException e) {
        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                null,
                Map.of(
                        "code",e.getErrorCode().getCode(),
                        "message",e.getMessage()
                )
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
