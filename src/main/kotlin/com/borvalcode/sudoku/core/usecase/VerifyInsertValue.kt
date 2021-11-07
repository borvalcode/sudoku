package com.borvalcode.sudoku.core.usecase

import com.borvalcode.sudoku.core.entity.*
import io.vavr.control.Either

class VerifyInsertValue(private val solveSudoku: SolveSudoku) : UseCase<Insert, Boolean> {

    override fun execute(input: Insert): Either<Error, Boolean> {
        return solveSudoku
                .execute(input.sudoku)
                .bimap(
                        { Error(ErrorType.VERIFY_ERROR) },
                        { okResponse: Sudoku -> this.onSuccess(okResponse, input.coordinate, input.value) })
    }

    private fun onSuccess(sudoku: Sudoku, coordinate: Coordinate, value: Int): Boolean =
            getValue(sudoku, coordinate) == value

    private fun getValue(sudoku: Sudoku, coordinate: Coordinate): Int =
            sudoku.sudoku[coordinate.x][coordinate.y]
}