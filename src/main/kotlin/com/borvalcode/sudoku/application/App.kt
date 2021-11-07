package com.borvalcode.sudoku.application

import com.borvalcode.sudoku.core.usecase.GenerateSudoku
import com.borvalcode.sudoku.core.usecase.InsertValue
import com.borvalcode.sudoku.core.usecase.SolveSudoku
import com.borvalcode.sudoku.core.usecase.VerifyInsertValue
import com.borvalcode.sudoku.infrastructure.delivery.armeria.controller.GenerateController
import com.borvalcode.sudoku.infrastructure.delivery.armeria.controller.InsertController
import com.borvalcode.sudoku.infrastructure.delivery.armeria.controller.SolveController
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.SudokuBoardApiService
import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.docs.DocService
import com.linecorp.armeria.server.file.FileService
import com.linecorp.armeria.server.logging.AccessLogWriter
import java.nio.file.Paths

fun main() {
    val sudokuBoardApiService = SudokuBoardApiService()

    val generateSudoku = GenerateSudoku(sudokuBoardApiService)
    val solveSudoku = SolveSudoku(sudokuBoardApiService)
    val verifyInsertValue = VerifyInsertValue(solveSudoku)
    val insertValue = InsertValue(verifyInsertValue)

    Server.builder()
            .http(4567)
            .annotatedService(GenerateController(generateSudoku))
            .annotatedService(InsertController(insertValue))
            .annotatedService(SolveController(solveSudoku))
            .serviceUnder("/docs", DocService())
            .serviceUnder("/", FileService.of(Paths.get("src/main/webapp")))
            .accessLogWriter(AccessLogWriter.common(), true)
            .build().start().join()
}