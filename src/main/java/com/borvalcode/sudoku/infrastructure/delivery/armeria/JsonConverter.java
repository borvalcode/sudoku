package com.borvalcode.sudoku.infrastructure.delivery.armeria;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.armeria.common.AggregatedHttpRequest;
import com.linecorp.armeria.common.HttpHeaders;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.ResponseHeaders;
import com.linecorp.armeria.common.annotation.Nullable;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.RequestConverterFunction;
import com.linecorp.armeria.server.annotation.ResponseConverterFunction;
import java.lang.reflect.ParameterizedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonConverter implements RequestConverterFunction, ResponseConverterFunction {

  private static final Logger log = LoggerFactory.getLogger(JsonConverter.class);
  private final ObjectMapper objectMapper;

  public JsonConverter() {
    this.objectMapper = new ObjectMapper();
  }

  @Override
  public @Nullable Object convertRequest(
      ServiceRequestContext ctx,
      AggregatedHttpRequest request,
      Class<?> expectedResultType,
      @Nullable ParameterizedType expectedParameterizedResultType) {

    try {
      return this.objectMapper.readValue(request.content().toStringUtf8(), expectedResultType);
    } catch (JsonProcessingException e) {
      log.error("Error parsing json ", e);
    }

    return RequestConverterFunction.fallthrough();
  }

  @Override
  public HttpResponse convertResponse(
      ServiceRequestContext ctx,
      ResponseHeaders headers,
      @Nullable Object result,
      HttpHeaders trailers)
      throws Exception {
    return HttpResponse.of(this.objectMapper.writeValueAsString(result));
  }
}
