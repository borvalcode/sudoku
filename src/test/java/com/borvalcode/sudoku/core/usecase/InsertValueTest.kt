package com.borvalcode.sudoku.core.usecase

import com.borvalcode.sudoku.core.entity.*
import io.vavr.control.Either
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class InsertValueTest {
    private val verifyInsertValue: VerifyInsertValue = mock(VerifyInsertValue::class.java)
    private val insertValue: InsertValue = InsertValue(verifyInsertValue)

    @Test
    fun should_go_ok_if_validates() {
        val insert = Insert(
                sudoku = anIncompleteSudoku(),
                coordinate = Coordinate(8, 4),
                value = 1)
        `when`(verifyInsertValue.execute(insert)).thenReturn(Either.right(true))

        val execute = insertValue.execute(insert)

        assertThat(execute.isRight).isTrue
        assertThat(execute.get()).isEqualTo(aCompleteSudoku())
    }

    @Test
    fun should_return_error_when_sudoku_complete() {
        val insert = Insert(
                sudoku = aCompleteSudoku(),
                coordinate = Coordinate(1, 3),
                value = 8)

        val execute = insertValue.execute(insert)

        assertThat(execute.isLeft).isTrue
        assertThat(execute.left).isEqualTo(Error(ErrorType.INSERT_ERROR_SUDOKU_COMPLETE))
    }

    @Test
    fun should_return_error_when_coordinate_not_empty() {
        val insert = Insert(
                sudoku = aCompleteSudoku(),
                coordinate = Coordinate(2, 4),
                value = 8)

        val execute = insertValue.execute(insert)

        assertThat(execute.isLeft).isTrue
        assertThat(execute.left).isEqualTo(Error(ErrorType.INSERT_ERROR_NOT_EMPTY))
    }

    companion object {
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
                            listOf(5, 4, 7, 3, 0, 2, 9, 6, 8)))
        }
    }
}