package com.borvalcode.sudoku.infrastructure.delivery.armeria.controller;

import com.borvalcode.sudoku.infrastructure.delivery.armeria.JsonConverter;
import com.linecorp.armeria.server.annotation.ProducesJson;
import com.linecorp.armeria.server.annotation.ResponseConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@ResponseConverter(JsonConverter.class)
@ProducesJson
public @interface JsonGet {
}
