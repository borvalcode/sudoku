package com.borvalcode.sudoku.core.usecase

import com.borvalcode.sudoku.core.entity.*
import io.vavr.control.Either

class InsertValue(private val verifyInsertValue: VerifyInsertValue) : UseCase<Insert, Sudoku> {
    override fun execute(input: Insert): Either<Error, Sudoku> {
        if (isComplete(input.sudoku)) {
            return Either.left(Error(ErrorType.INSERT_ERROR_SUDOKU_COMPLETE))
        } else if (hasValue(input.sudoku, input.coordinate)) {
            return Either.left(Error(ErrorType.INSERT_ERROR_NOT_EMPTY))
        }
        val inserted = insert(input.sudoku, input.coordinate, input.value)
        return verifyInsertValue
                .execute(input)
                .flatMap { okResponse: Boolean -> this.onSuccess(okResponse, inserted) }
    }

    private fun isComplete(sudoku: Sudoku): Boolean =
            sudoku.sudoku.all { row -> row.none { cell -> cell == 0 } }

    private fun hasValue(sudoku: Sudoku, coordinate: Coordinate): Boolean {
        return sudoku.sudoku[coordinate.x][coordinate.y] != 0
    }

    private fun insert(sudoku: Sudoku, coordinate: Coordinate, value: Int): Sudoku {
        val mutableSudoku: List<MutableList<Int>> = sudoku.sudoku.asSequence()
                .map { c -> ArrayList(c) }
                .toMutableList()
        mutableSudoku[coordinate.x][coordinate.y] = value
        return Sudoku(mutableSudoku)
    }

    private fun onSuccess(valid: Boolean, sudoku: Sudoku): Either<Error?, Sudoku> =
            if (!valid) Either.left(Error(ErrorType.INSERT_ERROR_NOT_VALID))
            else Either.right(sudoku)
}