package com.borvalcode.sudoku.core.usecase;

import static java.util.Optional.of;

import com.borvalcode.sudoku.core.entity.Difficulty;
import com.borvalcode.sudoku.core.entity.Error;
import com.borvalcode.sudoku.core.entity.ErrorType;
import com.borvalcode.sudoku.core.entity.Sudoku;
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.SudokuBoardApiService;
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.GenerateResponse;
import io.vavr.control.Either;

public class GenerateSudoku implements UseCase<Difficulty, Sudoku> {

  private final SudokuBoardApiService sudokuBoardApiService;

  public GenerateSudoku(SudokuBoardApiService sudokuBoardApiService) {
    this.sudokuBoardApiService = sudokuBoardApiService;
  }

  @Override
  public Either<Error, Sudoku> execute(Difficulty difficulty) {
    return this.sudokuBoardApiService
        .generateSudoku(diff(difficulty), true)
        .bimap(
            error -> Error.of(ErrorType.GENERATE_ERROR),
            okResponse -> onSuccess(difficulty, okResponse));
  }

  private int diff(Difficulty difficulty) {
    switch (difficulty) {
      case HARD:
        return 3;
      case MEDIUM:
        return 2;
      case EASY:
      default:
        return 1;
    }
  }

  private Sudoku onSuccess(Difficulty difficulty, GenerateResponse apiResponse) {
    return of(difficulty)
        .filter(Difficulty.NONE::equals)
        .map(isNone -> Sudoku.of(apiResponse.getResponse().getSolution()))
        .orElseGet(() -> Sudoku.of(apiResponse.getResponse().getUnsolvedSudoku()));
  }
}
