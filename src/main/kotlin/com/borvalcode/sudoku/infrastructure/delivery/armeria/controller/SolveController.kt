package com.borvalcode.sudoku.infrastructure.delivery.armeria.controller

import com.borvalcode.sudoku.core.entity.Sudoku
import com.borvalcode.sudoku.core.usecase.SolveSudoku
import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.server.annotation.Post

class SolveController(private val solveSudoku: SolveSudoku) {

    @Post("/sudoku/solve")
    @JsonPost
    fun solve(sudoku: Sudoku): HttpResponse = Controller.process(solveSudoku, sudoku)
}