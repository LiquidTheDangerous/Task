package org.example.controller.body;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ApiBody<T>{

    ActionResultMessage actionResult;
    T body;
}
