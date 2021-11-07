package com.borvalcode.sudoku.core.usecase

import com.borvalcode.sudoku.core.entity.*
import io.vavr.control.Either
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class VerifyInsertValueTest {
    private val solveSudoku: SolveSudoku = Mockito.mock(SolveSudoku::class.java)
    private val verifyInsertValue: VerifyInsertValue = VerifyInsertValue(solveSudoku)

    @Test
    fun should_return_error_response_when_input_empty() {
        val insert = Insert(
                sudoku = EMPTY_SUDOKU,
                coordinate = Coordinate(3, 4),
                value = 3
        )

        `when`(solveSudoku.execute(insert.sudoku))
                .thenReturn(Either.left(Error(ErrorType.SOLVE_ERROR)))

        val actualResponse = verifyInsertValue.execute(insert)
        assertThat(actualResponse.isLeft).isTrue
        assertThat(actualResponse.left).isEqualTo(Error(ErrorType.VERIFY_ERROR))
    }

    @Test
    fun should_return_ok_response_when_valid_insert() {
        val insert = Insert(
                sudoku = anIncompleteSudoku(),
                coordinate = Coordinate(8, 8),
                value = 8
        )

        `when`(solveSudoku.execute(insert.sudoku)).thenReturn(Either.right(aCompleteSudoku()))

        val actualResponse = verifyInsertValue.execute(insert)

        assertThat(actualResponse.isRight).isTrue
        assertThat(actualResponse.get()).isTrue
    }

    @Test
    fun should_return_invalid_error_when_invalid_insert() {
        val insert = Insert(
                sudoku = anIncompleteSudoku(),
                coordinate = Coordinate(8, 8),
                value = 7
        )

        `when`(solveSudoku.execute(insert.sudoku)).thenReturn(Either.right(aCompleteSudoku()))

        val actualResponse = verifyInsertValue.execute(insert)

        assertThat(actualResponse.isRight).isTrue
        assertThat(actualResponse.get()).isFalse
    }

    companion object {
        private val EMPTY_SUDOKU: Sudoku = Sudoku(ArrayList())
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