package com.borvalcode.sudoku.infrastructure.gateway

import com.fasterxml.jackson.databind.ObjectMapper
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.ServiceError
import io.vavr.control.Either
import java.io.IOException
import java.lang.InterruptedException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CallService<Req, Res>(private val objectMapper: ObjectMapper, private val domain: String) {

    constructor(domain: String) : this (ObjectMapper(), domain)

    operator fun get(path: String, responseClass: Class<Res>, vararg headers: String): Either<ServiceError, Res> {
        val request = HttpRequest.newBuilder()
                .uri(URI.create(domain + path))
                .headers(*headers)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build()
        return try {
            val response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())
            Either.right(objectMapper.readValue(response.body(), responseClass))
        } catch (e: IOException) {
            Either.left(ServiceError.IO)
        } catch (e: InterruptedException) {
            Either.left(ServiceError.INTERRUPTED)
        }
    }
}