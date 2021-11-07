package com.borvalcode.sudoku.core.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.borvalcode.sudoku.core.entity.Error;
import com.borvalcode.sudoku.core.entity.ErrorType;
import com.borvalcode.sudoku.core.entity.Sudoku;
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.SudokuBoardApiService;
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.ServiceError;
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.SolveResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SolveSudokuTest {

  private final SudokuBoardApiService sudokuBoardApiService;
  private final SolveSudoku solveSudoku;

  SolveSudokuTest() {
    this.sudokuBoardApiService = mock(SudokuBoardApiService.class);
    this.solveSudoku = new SolveSudoku(this.sudokuBoardApiService);
  }

  @Test
  void should_solve_sudoku() throws IOException {

    when(this.sudokuBoardApiService.solveSudoku(anyString()))
        .thenReturn(aSudokuBoardApiSolveOkResponse());

    Either<Error, Sudoku> actualResponse = this.solveSudoku.execute(anIncompleteSudoku());

    assertThat(actualResponse.isRight()).isTrue();
    assertThat(actualResponse.get()).isEqualTo(aCompleteSudoku());
  }

  @Test
  void should_return_error() throws IOException {

    when(this.sudokuBoardApiService.solveSudoku(anyString()))
        .thenReturn(aSudokuBoardApiSolveErrorResponse());

    Either<Error, Sudoku> actualResponse = this.solveSudoku.execute(anIncompleteSudoku());

    assertThat(actualResponse.isLeft()).isTrue();
    assertThat(actualResponse.getLeft()).isEqualTo(Error.of(ErrorType.SOLVE_ERROR));
  }

  private Either<ServiceError, SolveResponse> aSudokuBoardApiSolveErrorResponse()
      throws IOException {
    String apiResponse =
        Files.readString(Paths.get("src/test/resources/sudokuBoardApiSolveResponse_error.json"));
    return Either.right(new ObjectMapper().readValue(apiResponse, SolveResponse.class));
  }

  private Either<ServiceError, SolveResponse> aSudokuBoardApiSolveOkResponse() throws IOException {
    String apiResponse =
        Files.readString(Paths.get("src/test/resources/sudokuBoardApiSolveResponse_ok.json"));
    return Either.right(new ObjectMapper().readValue(apiResponse, SolveResponse.class));
  }

  private static Sudoku anIncompleteSudoku() {
    return Sudoku.of(
        List.of(
            List.of(1, 2, 3, 4, 5, 6, 7, 8, 9),
            List.of(4, 5, 6, 7, 8, 9, 1, 3, 2),
            List.of(7, 8, 9, 1, 2, 3, 4, 5, 6),
            List.of(2, 1, 4, 8, 3, 7, 6, 9, 5),
            List.of(3, 6, 5, 2, 9, 1, 8, 7, 4),
            List.of(9, 7, 8, 6, 4, 5, 2, 1, 3),
            List.of(6, 3, 2, 9, 7, 8, 5, 4, 1),
            List.of(8, 9, 1, 5, 6, 4, 3, 2, 7),
            List.of(5, 4, 7, 3, 1, 2, 9, 6, 0)));
  }

  private static Sudoku aCompleteSudoku() {
    return Sudoku.of(
        List.of(
            List.of(1, 2, 3, 4, 5, 6, 7, 8, 9),
            List.of(4, 5, 6, 7, 8, 9, 1, 3, 2),
            List.of(7, 8, 9, 1, 2, 3, 4, 5, 6),
            List.of(2, 1, 4, 8, 3, 7, 6, 9, 5),
            List.of(3, 6, 5, 2, 9, 1, 8, 7, 4),
            List.of(9, 7, 8, 6, 4, 5, 2, 1, 3),
            List.of(6, 3, 2, 9, 7, 8, 5, 4, 1),
            List.of(8, 9, 1, 5, 6, 4, 3, 2, 7),
            List.of(5, 4, 7, 3, 1, 2, 9, 6, 8)));
  }
}
