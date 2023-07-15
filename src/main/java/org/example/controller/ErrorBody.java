package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorBody {
    private ActionResult actionResult;
    private String message;
}
