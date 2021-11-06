package com.borvalcode.sudoku.application.controller;

import com.borvalcode.sudoku.application.JsonConverter;
import com.linecorp.armeria.server.annotation.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@RequestConverter(JsonConverter.class)
@ResponseConverter(JsonConverter.class)
@ConsumesJson
@ProducesJson
public @interface JsonPost{
}
