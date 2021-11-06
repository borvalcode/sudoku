package com.borvalcode.sudoku.core.usecase;

import com.borvalcode.sudoku.core.entity.Error;
import com.borvalcode.sudoku.core.entity.*;
import io.vavr.control.Either;

public class VerifyInsertValue implements UseCase<Insert, Boolean>{

    private final SolveSudoku solveSudoku;

    public VerifyInsertValue(SolveSudoku solveSudoku) {
        this.solveSudoku = solveSudoku;
    }

    public Either<Error, Boolean> execute(Insert insert) {
        return this.solveSudoku.execute(insert.getSudoku())
                .bimap(error -> Error.of(ErrorType.VERIFY_ERROR),
                        okResponse -> this.onSuccess(okResponse, insert.getCoordinate(), insert.getValue()));
    }

    private Boolean onSuccess(Sudoku sudoku, Coordinate coordinate, int value) {
        return getValue(sudoku, coordinate) == value;
    }

    private int getValue(Sudoku sudoku, Coordinate coordinate) {
        return sudoku.getSudoku().get(coordinate.getX()).get(coordinate.getY());
    }

}
