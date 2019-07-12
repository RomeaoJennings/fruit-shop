package com.romeao.fruitshop.api.v1.exceptionhandlers;

import com.romeao.fruitshop.exceptions.BadRequestException;
import com.romeao.fruitshop.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class FruitShopExceptionHandler {

    private static final String STATUS_CODE = "statusCode";
    private static final String ERROR = "error";

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException exception) {
        Map<String, Object> response = buildResponse(HttpStatus.NOT_FOUND.value(), exception);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException exception) {
        Map<String, Object> response = buildResponse(HttpStatus.BAD_REQUEST.value(), exception);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> buildResponse(int statusCode, RuntimeException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_CODE, statusCode);
        response.put(ERROR, exception.getMessage());
        return response;
    }

}
