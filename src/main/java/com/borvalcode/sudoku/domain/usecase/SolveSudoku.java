package com.borvalcode.sudoku.domain.usecase;

import com.borvalcode.sudoku.domain.dto.Error;
import com.borvalcode.sudoku.domain.dto.ErrorType;
import com.borvalcode.sudoku.domain.dto.Sudoku;
import com.borvalcode.sudoku.infrastructure.sudokuboardapi.SudokuBoardApiService;
import com.borvalcode.sudoku.infrastructure.sudokuboardapi.vo.ServiceError;
import com.borvalcode.sudoku.infrastructure.sudokuboardapi.vo.SolveResponse;
import io.vavr.control.Either;

import static com.borvalcode.sudoku.infrastructure.sudokuboardapi.SudokuBoardApiMapper.param;

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
