package com.borvalcode.sudoku.infrastructure.gateway;

import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.ServiceError;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CallService<Req, Res> {

  private final ObjectMapper objectMapper;

  private final String domain;

  public CallService(String domain) {
    this(new ObjectMapper(), domain);
  }

  public CallService(ObjectMapper objectMapper, String domain) {
    this.objectMapper = objectMapper;
    this.domain = domain;
  }

  public Either<ServiceError, Res> get(String path, Class<Res> responseClass, String... headers) {

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(this.domain + path))
            .headers(headers)
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();

    try {
      HttpResponse<String> response =
          HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      return Either.right(objectMapper.readValue(response.body(), responseClass));
    } catch (IOException e) {
      return Either.left(ServiceError.IO);
    } catch (InterruptedException e) {
      return Either.left(ServiceError.INTERRUPTED);
    }
  }
}
