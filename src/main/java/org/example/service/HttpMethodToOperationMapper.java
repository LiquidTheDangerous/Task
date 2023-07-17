package org.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
public interface HttpMethodToOperationMapper {
    String mapHttpMethodToOperationAsString(RequestMethod requestMethod);
}
