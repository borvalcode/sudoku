package com.borvalcode.sudoku.core.usecase

import com.borvalcode.sudoku.core.entity.Difficulty
import com.borvalcode.sudoku.core.entity.Error
import com.borvalcode.sudoku.core.entity.ErrorType
import com.borvalcode.sudoku.core.entity.Sudoku
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.SudokuBoardApiService
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.GenerateResponse
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.ServiceError
import com.fasterxml.jackson.databind.ObjectMapper
import io.vavr.control.Either
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class GenerateSudokuTest {
    private val sudokuBoardApiService: SudokuBoardApiService = Mockito.mock(SudokuBoardApiService::class.java)
    private val generateSudoku: GenerateSudoku = GenerateSudoku(sudokuBoardApiService)

    @Test
    @Throws(IOException::class)
    fun should_return_a_full_sudoku() {
        Mockito.`when`(sudokuBoardApiService.generateSudoku(1, true)).thenReturn(aSudokuBoardApiResponse())
        val actualSudoku = generateSudoku.execute(Difficulty.NONE)
        Assertions.assertThat(actualSudoku.isRight).isTrue
        Assertions.assertThat(actualSudoku.get().sudoku).hasSameElementsAs(expectedSudoku().sudoku)
    }

    @Test
    fun should_return_generate_error_when_service_fails() {
        Mockito.`when`(sudokuBoardApiService.generateSudoku(1, true))
                .thenReturn(Either.left(ServiceError.IO))
        val actualSudoku = generateSudoku.execute(Difficulty.NONE)
        Assertions.assertThat(actualSudoku.isLeft).isTrue
        Assertions.assertThat(actualSudoku.left).isEqualTo(Error(ErrorType.GENERATE_ERROR))
    }

    @Throws(IOException::class)
    private fun aSudokuBoardApiResponse(): Either<ServiceError, GenerateResponse> {
        val apiResponse = Files.readString(Paths.get("src/test/resources/sudokuBoardApiResponse_1_true.json"))
        return Either.right(ObjectMapper().readValue(apiResponse, GenerateResponse::class.java))
    }

    companion object {
        private fun expectedSudoku(): Sudoku {
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