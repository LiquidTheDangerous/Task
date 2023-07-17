package org.example.controller.util;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
public class HttpMethodToOperationMapperImpl implements HttpMethodToOperationMapper {
    @Override
    public String mapHttpMethodToOperationAsString(RequestMethod requestMethod) {
        String op = "";
        if (requestMethod == RequestMethod.POST) {
            op = "create";
        } else if (requestMethod == RequestMethod.DELETE) {
            op = "delete";
        } else if (requestMethod == RequestMethod.PUT) {
            op = "update";
        } else if (requestMethod == RequestMethod.GET) {
            op = "read";
        }
        return op;
    }
}
