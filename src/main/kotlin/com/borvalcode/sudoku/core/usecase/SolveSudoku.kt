package com.borvalcode.sudoku.core.usecase

import com.borvalcode.sudoku.core.entity.Error
import com.borvalcode.sudoku.core.entity.ErrorType
import com.borvalcode.sudoku.core.entity.Sudoku
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.SudokuBoardApiService
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.param
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.ServiceError
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.SolveResponse
import io.vavr.control.Either

class SolveSudoku(private val sudokuBoardApiService: SudokuBoardApiService) : UseCase<Sudoku, Sudoku> {

    override fun execute(input: Sudoku): Either<Error, Sudoku> =
            sudokuBoardApiService
                    .solveSudoku(param(input))
                    .fold(this::onError, this::onSuccess)

    private fun onError(serviceError: ServiceError): Either<Error, Sudoku> =
            Either.left(Error(ErrorType.SOLVE_ERROR))

    private fun onSuccess(solveResponse: SolveResponse): Either<Error, Sudoku> =
            if (!solveResponse.response.solvable) {
                Either.left(Error(ErrorType.SOLVE_ERROR))
            } else Either.right(Sudoku(solveResponse.response.solution))
}