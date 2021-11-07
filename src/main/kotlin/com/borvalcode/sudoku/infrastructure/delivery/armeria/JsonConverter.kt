package com.borvalcode.sudoku.infrastructure.delivery.armeria

import com.borvalcode.sudoku.infrastructure.delivery.armeria.JsonConverter
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.armeria.common.AggregatedHttpRequest
import com.linecorp.armeria.common.HttpHeaders
import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.common.ResponseHeaders
import com.linecorp.armeria.common.annotation.Nullable
import com.linecorp.armeria.server.ServiceRequestContext
import com.linecorp.armeria.server.annotation.RequestConverterFunction
import com.linecorp.armeria.server.annotation.ResponseConverterFunction
import org.slf4j.LoggerFactory
import java.lang.reflect.ParameterizedType

class JsonConverter() : RequestConverterFunction, ResponseConverterFunction {

    private val objectMapper = ObjectMapper()

    override fun convertRequest(
            ctx: ServiceRequestContext,
            request: AggregatedHttpRequest,
            expectedResultType: Class<*>?,
            expectedParameterizedResultType: @Nullable ParameterizedType?): @Nullable Any? {
        try {
            objectMapper.readValue(request.content().toStringUtf8(), expectedResultType)
        } catch (e: JsonProcessingException) {
            log.error("Error parsing json ", e)
        }
        return RequestConverterFunction.fallthrough()
    }

    override fun convertResponse(
            ctx: ServiceRequestContext,
            headers: ResponseHeaders,
            result: @Nullable Any?,
            trailers: HttpHeaders): HttpResponse = HttpResponse.of(objectMapper.writeValueAsString(result))

    companion object {
        private val log = LoggerFactory.getLogger(JsonConverter::class.java)
    }
}