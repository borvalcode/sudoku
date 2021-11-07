package com.borvalcode.sudoku.infrastructure.delivery.armeria.controller

import com.borvalcode.sudoku.core.entity.Error
import com.borvalcode.sudoku.core.entity.ErrorType
import com.borvalcode.sudoku.core.usecase.UseCase
import com.borvalcode.sudoku.infrastructure.delivery.armeria.buildBadRequestError
import com.borvalcode.sudoku.infrastructure.delivery.armeria.buildInternalServerError
import com.borvalcode.sudoku.infrastructure.delivery.armeria.buildOkResponse
import com.linecorp.armeria.common.HttpResponse
import java.util.function.Function

object Controller {

    fun <In, Out> process(
            useCase: UseCase<In, Out>,
            `in`: In,
            onError: Function<Error, HttpResponse>,
            onSuccess: Function<Out, HttpResponse>): HttpResponse {
        return useCase.execute(`in`).fold(onError, onSuccess)
    }

    fun <In, Out> process(
            useCase: UseCase<In, Out>, `in`: In, onError: Function<Error, HttpResponse>): HttpResponse {
        return process(useCase, `in`, onError, ::buildOkResponse)
    }

    fun <In, Out> process(useCase: UseCase<In, Out>, `in`: In): HttpResponse {
        return process(useCase, `in`, this::onError)
    }

    private fun onError(error: Error): HttpResponse {
        return if (error.errorType === ErrorType.INSERT_ERROR_NOT_VALID) {
            buildBadRequestError(error)
        } else buildInternalServerError(error)
    }
}