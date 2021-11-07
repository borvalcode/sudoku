package com.borvalcode.sudoku.infrastructure.delivery.armeria

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.armeria.common.HttpHeaders
import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.common.HttpStatus
import com.linecorp.armeria.common.MediaType

private val OBJECT_WRITER = ObjectMapper().writerWithDefaultPrettyPrinter()

private val RESPONSE_HEADERS = HttpHeaders.of(
        "Access-Control-Allow-Origin",
        "*",
        "Access-Control-Allow-Headers",
        "Origin, X-Requested-With, Content-Type, Accept")

fun <T> buildOkResponse(value: T): HttpResponse = buildResponse(HttpStatus.OK, value)

fun <T> buildBadRequestError(error: T): HttpResponse = buildResponse(HttpStatus.BAD_REQUEST, error)

fun <T> buildInternalServerError(error: T): HttpResponse = buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, error)

fun <T> buildResponse(httpStatus: HttpStatus, value: T): HttpResponse =
        HttpResponse.of(httpStatus, MediaType.JSON, prettyPrint(value), RESPONSE_HEADERS)

private fun <T> prettyPrint(value: T): String {
    return try {
        OBJECT_WRITER.writeValueAsString(value)
    } catch (e: JsonProcessingException) {
        "JsonProcessingException"
    }
}