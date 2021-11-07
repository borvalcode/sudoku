package com.borvalcode.sudoku.core.usecase

import com.borvalcode.sudoku.core.entity.Error
import com.borvalcode.sudoku.core.entity.ErrorType
import com.borvalcode.sudoku.core.entity.Sudoku
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.SudokuBoardApiService
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.ServiceError
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.SolveResponse
import com.fasterxml.jackson.databind.ObjectMapper
import io.vavr.control.Either
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class SolveSudokuTest internal constructor() {
    private val sudokuBoardApiService: SudokuBoardApiService = mock(SudokuBoardApiService::class.java)
    private val solveSudoku: SolveSudoku = SolveSudoku(sudokuBoardApiService)

    @Test
    @Throws(IOException::class)
    fun should_solve_sudoku() {

        `when`(sudokuBoardApiService.solveSudoku(anyString()))
                .thenReturn(aSudokuBoardApiSolveOkResponse())

        val actualResponse = solveSudoku.execute(anIncompleteSudoku())

        assertThat(actualResponse.isRight).isTrue
        assertThat(actualResponse.get()).isEqualTo(aCompleteSudoku())
    }

    @Test
    @Throws(IOException::class)
    fun should_return_error() {
        `when`(sudokuBoardApiService.solveSudoku(anyString()))
                .thenReturn(aSudokuBoardApiSolveErrorResponse())
        val actualResponse = solveSudoku.execute(anIncompleteSudoku())
        assertThat(actualResponse.isLeft).isTrue
        assertThat(actualResponse.left).isEqualTo(Error(ErrorType.SOLVE_ERROR))
    }

    @Throws(IOException::class)
    private fun aSudokuBoardApiSolveErrorResponse(): Either<ServiceError, SolveResponse> {
        val apiResponse = Files.readString(Paths.get("src/test/resources/sudokuBoardApiSolveResponse_error.json"))
        return Either.right(ObjectMapper().readValue(apiResponse, SolveResponse::class.java))
    }

    @Throws(IOException::class)
    private fun aSudokuBoardApiSolveOkResponse(): Either<ServiceError, SolveResponse> {
        val apiResponse = Files.readString(Paths.get("src/test/resources/sudokuBoardApiSolveResponse_ok.json"))
        return Either.right(ObjectMapper().readValue(apiResponse, SolveResponse::class.java))
    }

    companion object {
        private fun anIncompleteSudoku(): Sudoku {
            return Sudoku(
                    listOf(
                            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
                            listOf(4, 5, 6, 7, 8, 9, 1, 3, 2),
                            listOf(7, 8, 9, 1, 2, 3, 4, 5, 6),
                            listOf(2, 1, 4, 8, 3, 7, 6, 9, 5),
                            listOf(3, 6, 5, 2, 9, 1, 8, 7, 4),
                            listOf(9, 7, 8, 6, 4, 5, 2, 1, 3),
                            listOf(6, 3, 2, 9, 7, 8, 5, 4, 1),
                            listOf(8, 9, 1, 5, 6, 4, 3, 2, 7),
                            listOf(5, 4, 7, 3, 1, 2, 9, 6, 0)))
        }

        private fun aCompleteSudoku(): Sudoku {
            return Sudoku(
                    listOf(
                            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
                            listOf(4, 5, 6, 7, 8, 9, 1, 3, 2),
                            listOf(7, 8, 9, 1, 2, 3, 4, 5, 6),
                            listOf(2, 1, 4, 8, 3, 7, 6, 9, 5),
                            listOf(3, 6, 5, 2, 9, 1, 8, 7, 4),
                            listOf(9, 7, 8, 6, 4, 5, 2, 1, 3),
                            listOf(6, 3, 2, 9, 7, 8, 5, 4, 1),
                            listOf(8, 9, 1, 5, 6, 4, 3, 2, 7),
                            listOf(5, 4, 7, 3, 1, 2, 9, 6, 8)))
        }
    }

}