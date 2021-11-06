package com.borvalcode.sudoku.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.linecorp.armeria.common.HttpHeaders;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;

public final class ResponseBuilder {

    public static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writerWithDefaultPrettyPrinter();

    private static final HttpHeaders RESPONSE_HEADERS = HttpHeaders.of("Access-Control-Allow-Origin", "*", "Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

    private ResponseBuilder() {
    }

    public static <T> HttpResponse buildOkResponse(T value) {
        return buildResponse(HttpStatus.OK, value);
    }

    public static <T> HttpResponse buildBadRequestError(T error) {
        return buildResponse(HttpStatus.BAD_REQUEST, error);
    }

    public static <T> HttpResponse buildInternalServerError(T error) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, error);
    }

    public static <T> HttpResponse buildResponse(HttpStatus httpStatus, T value) {
        return HttpResponse.of(httpStatus,
                MediaType.JSON,
                prettyPrint(value),
                RESPONSE_HEADERS);
    }

    private static <T> String prettyPrint(T value) {
        try {
            return OBJECT_WRITER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return "JsonProcessingException";
        }
    }
}
