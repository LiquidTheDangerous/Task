package org.example.controller.handler;

import org.example.controller.body.ActionResultMessage;
import org.example.controller.body.ErrorBody;
import org.example.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResourceNotFoundExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
        var message = exception.getMessage();
        var action = exception.getAction();

        return new ResponseEntity<>(
                new ErrorBody(
                        new ActionResultMessage(action, false),
                        message
                ),
                HttpStatus.NOT_FOUND);
    }


}
