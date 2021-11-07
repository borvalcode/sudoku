package com.borvalcode.sudoku.core.usecase

import com.borvalcode.sudoku.core.entity.Difficulty
import com.borvalcode.sudoku.core.entity.Error
import com.borvalcode.sudoku.core.entity.ErrorType
import com.borvalcode.sudoku.core.entity.Sudoku
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.SudokuBoardApiService
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.GenerateResponse
import io.vavr.control.Either

class GenerateSudoku(private val sudokuBoardApiService: SudokuBoardApiService) : UseCase<Difficulty, Sudoku> {
    override fun execute(input: Difficulty): Either<Error, Sudoku> =
            sudokuBoardApiService
                    .generateSudoku(diff(input), true)
                    .bimap(
                            { Error(ErrorType.GENERATE_ERROR) },
                            { okResponse: GenerateResponse -> onSuccess(input, okResponse) })

    private fun diff(difficulty: Difficulty): Int =
            when (difficulty) {
                Difficulty.HARD -> 3
                Difficulty.MEDIUM -> 2
                Difficulty.EASY -> 1
                else -> 1
            }

    private fun onSuccess(difficulty: Difficulty, apiResponse: GenerateResponse): Sudoku =
            if (difficulty == Difficulty.NONE) Sudoku(apiResponse.response.solution)
            else Sudoku(apiResponse.response.unsolvedSudoku)

}