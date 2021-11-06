package com.borvalcode.sudoku.application.controller;

import com.borvalcode.sudoku.application.ResponseBuilder;
import com.borvalcode.sudoku.domain.dto.Error;
import com.borvalcode.sudoku.domain.dto.ErrorType;
import com.borvalcode.sudoku.domain.usecase.UseCase;
import com.linecorp.armeria.common.HttpResponse;

import java.util.function.Function;

import static com.borvalcode.sudoku.application.ResponseBuilder.buildBadRequestError;
import static com.borvalcode.sudoku.application.ResponseBuilder.buildInternalServerError;

public class Controller {

    public static <In, Out> HttpResponse process(UseCase<In, Out> useCase, In in,
                                                 Function<Error, HttpResponse> onError,
                                                 Function<Out, HttpResponse> onSuccess) {
        return useCase.execute(in)
                .fold(onError, onSuccess);
    }

    public static <In, Out> HttpResponse process(UseCase<In, Out> useCase, In in,
                                                 Function<Error, HttpResponse> onError) {
        return process(useCase, in, onError, ResponseBuilder::buildOkResponse);
    }

    public static <In, Out> HttpResponse process(UseCase<In, Out> useCase, In in) {
        return process(useCase, in, Controller::onError);
    }

    private static HttpResponse onError(Error error) {
        if (error.getErrorType() == ErrorType.INSERT_ERROR_NOT_VALID) {
            return buildBadRequestError(error);
        }
        return buildInternalServerError(error);
    }
}
