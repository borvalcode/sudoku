package com.borvalcode.sudoku.application.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.ExceptionHandlerFunction;

public class DefaultExceptionHandler implements ExceptionHandlerFunction {
    @Override
    public HttpResponse handleException(ServiceRequestContext ctx,
                                        HttpRequest req, Throwable cause) {
        if (cause instanceof JsonProcessingException) {
            return HttpResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,
                    MediaType.JSON,
                    "Internal Server Error");
        }

        // To the next exception handler.
        return ExceptionHandlerFunction.fallthrough();
    }
}
