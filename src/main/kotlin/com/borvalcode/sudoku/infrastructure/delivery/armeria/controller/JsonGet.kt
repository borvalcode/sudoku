package com.borvalcode.sudoku.infrastructure.delivery.armeria.controller

import com.borvalcode.sudoku.infrastructure.delivery.armeria.JsonConverter
import com.linecorp.armeria.server.annotation.ProducesJson
import com.linecorp.armeria.server.annotation.ResponseConverter

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@ResponseConverter(JsonConverter::class)
@ProducesJson
annotation class JsonGet 