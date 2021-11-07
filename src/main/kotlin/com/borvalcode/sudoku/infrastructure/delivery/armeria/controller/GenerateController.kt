package com.borvalcode.sudoku.infrastructure.delivery.armeria.controller

import com.borvalcode.sudoku.core.entity.Difficulty
import com.borvalcode.sudoku.core.usecase.GenerateSudoku
import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Param

class GenerateController(private val generateSudoku: GenerateSudoku) {

    @Get("/sudoku/generate")
    @JsonGet
    fun generate(@Param("difficulty") difficulty: Difficulty): HttpResponse =
            Controller.process(generateSudoku, difficulty)
}