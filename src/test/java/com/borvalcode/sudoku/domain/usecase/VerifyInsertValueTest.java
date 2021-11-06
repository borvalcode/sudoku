package com.borvalcode.sudoku.domain.usecase;

import com.borvalcode.sudoku.domain.dto.Error;
import com.borvalcode.sudoku.domain.dto.*;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VerifyInsertValueTest {

    private static final Sudoku EMPTY_SUDOKU = Sudoku.of(new ArrayList<>());
    private final VerifyInsertValue verifyInsertValue;
    private final SolveSudoku solveSudoku;

    public VerifyInsertValueTest() {
        this.solveSudoku = mock(SolveSudoku.class);
        this.verifyInsertValue = new VerifyInsertValue(this.solveSudoku);
    }

    @Test
    void should_return_error_response_when_input_empty() throws IOException {
        /*when(this.sudokuBoardApiService.solveSudoku(anyString()))
                .thenReturn(aSudokuBoardApiVerifyErrorResponse());*/

        Insert insert = Insert.builder()
                .sudoku(EMPTY_SUDOKU)
                .coordinate(Coordinate.of(3, 4))
                .value(3)
                .build();

        when(this.solveSudoku.execute(insert.getSudoku()))
                .thenReturn(Either.left(Error.of(ErrorType.SOLVE_ERROR)));

        Either<Error, Boolean> actualResponse = this.verifyInsertValue.execute(insert);

        assertThat(actualResponse.isLeft()).isTrue();
        assertThat(actualResponse.getLeft()).isEqualTo(Error.of(ErrorType.VERIFY_ERROR));
    }

    @Test
    void should_return_ok_response_when_valid_insert() throws IOException {
       /* when(this.sudokuBoardApiService.solveSudoku(anyString()))
                .thenReturn(aSudokuBoardApiVerifyOkResponse());*/

        Insert insert = Insert.builder()
                .sudoku(anIncompleteSudoku())
                .coordinate(Coordinate.of(8, 8))
                .value(8)
                .build();

        when(this.solveSudoku.execute(insert.getSudoku()))
                .thenReturn(Either.right(aCompleteSudoku()));

        Either<Error, Boolean> actualResponse = this.verifyInsertValue.execute(insert);

        assertThat(actualResponse.isRight()).isTrue();
        assertThat(actualResponse.get()).isTrue();
    }

    @Test
    void should_return_invalid_error_when_invalid_insert() throws IOException {
        /*when(this.sudokuBoardApiService.solveSudoku(anyString()))
                .thenReturn(aSudokuBoardApiVerifyOkResponse());*/

        Insert insert = Insert.builder()
                .sudoku(anIncompleteSudoku())
                .coordinate(Coordinate.of(8, 8))
                .value(7)
                .build();

        when(this.solveSudoku.execute(insert.getSudoku()))
                .thenReturn(Either.right(aCompleteSudoku()));

        Either<Error, Boolean> actualResponse = this.verifyInsertValue.execute(insert);

        assertThat(actualResponse.isRight()).isTrue();
        assertThat(actualResponse.get()).isFalse();;
    }

    /*private Either<ServiceError, SolveResponse> aSudokuBoardApiVerifyErrorResponse() throws IOException {
        String apiResponse = Files.readString(Paths.get("src/test/resources/sudokuBoardApiSolveResponse_error.json"));
        return Either.right(new ObjectMapper().readValue(apiResponse, SolveResponse.class));
    }

    private Either<ServiceError, SolveResponse> aSudokuBoardApiVerifyOkResponse() throws IOException {
        String apiResponse = Files.readString(Paths.get("src/test/resources/sudokuBoardApiSolveResponse_ok.json"));
        return Either.right(new ObjectMapper().readValue(apiResponse, SolveResponse.class));
    }*/

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
                List.of(5, 4, 7, 3, 1, 2, 9, 6, 0)
        ));
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

}