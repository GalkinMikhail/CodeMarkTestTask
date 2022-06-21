package com.example.codemarktesttask.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class BaseControllerAdvice {
    Date date = new Date();

    @ExceptionHandler
    public Object EmptyData(EmptyDataException ex) {
        return response(HttpStatus.NO_CONTENT, ex);
    }

    @ExceptionHandler
    public Object InvalidPassword(InvalidPasswordException ex) {
        return response(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler
    public Object InvalidData(InvalidDataException ex){
        return response(HttpStatus.BAD_REQUEST,ex);
    }

    private Object response(HttpStatus status, AbstractException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("techInfo", ex.getTechInfo());
        body.put("status", status.toString());
        body.put("timestamp", String.valueOf(date));
        return new ResponseEntity<>(body, headers, status);
    }
}
