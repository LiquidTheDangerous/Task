package org.example.exceptions;

import lombok.Getter;



public class ResourceNotFoundException extends RuntimeException {

    @Getter
    private String action;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message, String action){
        super(message);
        this.action = action;
    }
}
