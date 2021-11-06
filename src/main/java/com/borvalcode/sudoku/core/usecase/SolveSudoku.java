package com.borvalcode.sudoku.core.usecase;

import com.borvalcode.sudoku.core.entity.Error;
import com.borvalcode.sudoku.core.entity.ErrorType;
import com.borvalcode.sudoku.core.entity.Sudoku;
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.SudokuBoardApiService;
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.ServiceError;
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.SolveResponse;
import io.vavr.control.Either;

import static com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.SudokuBoardApiMapper.param;

public class SolveSudoku implements UseCase<Sudoku, Sudoku>{

    private final SudokuBoardApiService sudokuBoardApiService;

    public SolveSudoku(SudokuBoardApiService sudokuBoardApiService) {
        this.sudokuBoardApiService = sudokuBoardApiService;
    }

    @Override
    public Either<Error, Sudoku> execute(Sudoku sudoku) {
        return this.sudokuBoardApiService.solveSudoku(param(sudoku))
                .fold(this::onError, this::onSuccess);
    }

    private Either<Error, Sudoku> onError(ServiceError serviceError) {
        return Either.left(Error.of(ErrorType.SOLVE_ERROR));
    }

    private Either<Error, Sudoku> onSuccess(SolveResponse solveResponse) {
        if (!solveResponse.getResponse().isSolvable()) {
            return Either.left(Error.of(ErrorType.SOLVE_ERROR));
        }
        return Either.right(Sudoku.of(solveResponse.getResponse().getSolution()));
    }
}
