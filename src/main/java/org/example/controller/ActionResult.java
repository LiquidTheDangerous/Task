package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ActionResult {
    private String action;

    private boolean result;
}
