package com.borvalcode.sudoku.domain.usecase;

import com.borvalcode.sudoku.domain.dto.Error;
import com.borvalcode.sudoku.domain.dto.*;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class InsertValueTest {

    private final InsertValue insertValue;

    private final VerifyInsertValue verifyInsertValue;

    public InsertValueTest() {
        this.verifyInsertValue = Mockito.mock(VerifyInsertValue.class);
        this.insertValue = new InsertValue(this.verifyInsertValue);
    }

    @Test
    void should_go_ok_if_validates() {
        Insert insert = Insert.builder()
                .sudoku(anIncompleteSudoku())
                .coordinate(Coordinate.of(8, 4))
                .value(1)
                .build();

        when(this.verifyInsertValue.execute(insert))
                .thenReturn(Either.right(Boolean.TRUE));

        Either<Error, Sudoku> execute = this.insertValue.execute(insert);

        assertThat(execute.isRight()).isTrue();
        assertThat(execute.get()).isEqualTo(aCompleteSudoku());
    }

    @Test
    void should_return_error_when_sudoku_complete() {
        Insert insert = Insert.builder()
                .sudoku(aCompleteSudoku())
                .coordinate(Coordinate.of(1, 3))
                .value(8)
                .build();

        Either<Error, Sudoku> execute = this.insertValue.execute(insert);

        assertThat(execute.isLeft()).isTrue();
        assertThat(execute.getLeft()).isEqualTo(Error.of(ErrorType.INSERT_ERROR_SUDOKU_COMPLETE));
    }

    @Test
    void should_return_error_when_coordinate_not_empty() {
        Insert insert = Insert.builder()
                .sudoku(anIncompleteSudoku())
                .coordinate(Coordinate.of(2, 4))
                .value(8)
                .build();

        Either<Error, Sudoku> execute = this.insertValue.execute(insert);

        assertThat(execute.isLeft()).isTrue();
        assertThat(execute.getLeft()).isEqualTo(Error.of(ErrorType.INSERT_ERROR_NOT_EMPTY));
    }


    private static Sudoku aCompleteSudoku() {
        return Sudoku.of(List.of(
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9),
                List.of(4, 5, 6, 7, 8, 9, 1, 3, 2),
                List.of(7, 8, 9, 1, 2, 3, 4, 5, 6),
                List.of(2, 1, 4, 8, 3, 7, 6, 9, 5),
                List.of(3, 6, 5, 2, 9, 1, 8, 7, 4),
                List.of(9, 7, 8, 6, 4, 5, 2, 1, 3),
                List.of(6, 3, 2, 9, 7, 8, 5, 4, 1),
                List.of(8, 9, 1, 5, 6, 4, 3, 2, 7),
                List.of(5, 4, 7, 3, 1, 2, 9, 6, 8)
        ));
    }

    private static Sudoku anIncompleteSudoku() {
        return Sudoku.of(List.of(
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9),
                List.of(4, 5, 6, 7, 8, 9, 1, 3, 2),
                List.of(7, 8, 9, 1, 2, 3, 4, 5, 6),
                List.of(2, 1, 4, 8, 3, 7, 6, 9, 5),
                List.of(3, 6, 5, 2, 9, 1, 8, 7, 4),
                List.of(9, 7, 8, 6, 4, 5, 2, 1, 3),
                List.of(6, 3, 2, 9, 7, 8, 5, 4, 1),
                List.of(8, 9, 1, 5, 6, 4, 3, 2, 7),
                List.of(5, 4, 7, 3, 0, 2, 9, 6, 8)
        ));
    }
}