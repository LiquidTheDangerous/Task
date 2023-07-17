package org.example.controller.body;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ActionResultMessage {
    private String action;

    private boolean result;
}
