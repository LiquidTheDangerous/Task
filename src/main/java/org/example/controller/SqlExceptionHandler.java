package org.example.controller;

import org.example.service.HttpMethodToOperationMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;

@ControllerAdvice
public class SqlExceptionHandler {

    private final HttpMethodToOperationMapper httpMethodToOperationMapper;

    public SqlExceptionHandler(HttpMethodToOperationMapper httpMethodToOperationMapper) {
        this.httpMethodToOperationMapper = httpMethodToOperationMapper;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception, HandlerMethod method) {
        var mapping = method.getMethodAnnotation(RequestMapping.class);
        if (mapping == null) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        var httpMethod = mapping.method()[0];

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        new ErrorBody(
                                new ActionResultMessage(
                                        httpMethodToOperationMapper
                                                .mapHttpMethodToOperationAsString(httpMethod), false), exception.getMessage()));
    }
}
