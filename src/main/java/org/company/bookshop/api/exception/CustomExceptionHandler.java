package org.company.bookshop.api.exception;

import jakarta.validation.constraints.NotNull;
import org.company.bookshop.api.util.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  @NotNull HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  @NotNull WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        List<String> errors = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())//bu xato aniqlangan maydonning nomini qaytaradi. Masalan, agar maydon nomi email bo'lsa, bu method email ni qaytaradi.
                .toList();
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> exp(BadRequestException exception) {
        return ResponseEntity.ok(new ApiResponse<>(exception.getMessage(), 400, true));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<String>> exp(NotFoundException exception) {
        return ResponseEntity.ok(new ApiResponse<>(exception.getMessage(), 404, true));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> exp(RuntimeException exception) {
        return ResponseEntity.ok(new ApiResponse<>(exception.getMessage(), 500, true));
    }


}
