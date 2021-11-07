package com.borvalcode.sudoku.infrastructure.delivery.armeria.controller

import com.borvalcode.sudoku.infrastructure.delivery.armeria.JsonConverter
import com.linecorp.armeria.server.annotation.ConsumesJson
import com.linecorp.armeria.server.annotation.ProducesJson
import com.linecorp.armeria.server.annotation.RequestConverter
import com.linecorp.armeria.server.annotation.ResponseConverter

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@RequestConverter(JsonConverter::class)
@ResponseConverter(JsonConverter::class)
@ConsumesJson
@ProducesJson
annotation class JsonPost 