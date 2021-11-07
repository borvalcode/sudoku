package com.borvalcode.sudoku.core.usecase;

import com.borvalcode.sudoku.core.entity.Coordinate;
import com.borvalcode.sudoku.core.entity.Error;
import com.borvalcode.sudoku.core.entity.ErrorType;
import com.borvalcode.sudoku.core.entity.Insert;
import com.borvalcode.sudoku.core.entity.Sudoku;
import io.vavr.control.Either;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InsertValue implements UseCase<Insert, Sudoku> {

  private final VerifyInsertValue verifyInsertValue;

  public InsertValue(VerifyInsertValue verifyInsertValue) {
    this.verifyInsertValue = verifyInsertValue;
  }

  @Override
  public Either<Error, Sudoku> execute(Insert insert) {
    if (isComplete(insert.getSudoku())) {
      return Either.left(Error.of(ErrorType.INSERT_ERROR_SUDOKU_COMPLETE));
    } else if (hasValue(insert.getSudoku(), insert.getCoordinate())) {
      return Either.left(Error.of(ErrorType.INSERT_ERROR_NOT_EMPTY));
    }
    Sudoku inserted = insert(insert.getSudoku(), insert.getCoordinate(), insert.getValue());
    return this.verifyInsertValue
        .execute(insert)
        .flatMap(okResponse -> this.onSuccess(okResponse, inserted));
  }

  private boolean isComplete(Sudoku sudoku) {
    return sudoku.getSudoku().stream().allMatch(row -> row.stream().noneMatch(cell -> cell == 0));
  }

  private boolean hasValue(Sudoku sudoku, Coordinate coordinate) {
    return sudoku.getSudoku().get(coordinate.getX()).get(coordinate.getY()) != 0;
  }

  private Sudoku insert(Sudoku sudoku, Coordinate coordinate, int value) {
    List<List<Integer>> mutableSudoku =
        sudoku.getSudoku().stream()
            .map(ArrayList::new)
            .collect(Collectors.toCollection(ArrayList::new));
    mutableSudoku.get(coordinate.getX()).set(coordinate.getY(), value);
    return Sudoku.of(mutableSudoku);
  }

  private Either<Error, Sudoku> onSuccess(Boolean valid, Sudoku sudoku) {
    if (!valid) {
      return Either.left(Error.of(ErrorType.INSERT_ERROR_NOT_VALID));
    }
    return Either.right(sudoku);
  }
}
