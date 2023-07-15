package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ApiBody<T>{

    ActionResult actionResult;
    T body;
}
