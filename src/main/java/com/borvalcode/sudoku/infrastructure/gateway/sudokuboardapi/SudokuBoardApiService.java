package com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi;

import com.borvalcode.sudoku.infrastructure.gateway.CallService;
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.ServiceError;
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.GenerateResponse;
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.SolveResponse;
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.VerifyResponse;
import io.vavr.control.Either;

public class SudokuBoardApiService {

    private final CallService<Void, GenerateResponse> generateCallService;
    private final CallService<Void, VerifyResponse> verifyCallService;
    private final CallService<Void, SolveResponse> solveCallService;

    private static final String[] HEADERS = {"x-rapidapi-host", "sudoku-board.p.rapidapi.com",
            "x-rapidapi-key", "ed9f57f586msh832768573864c9cp12f37ejsn09161213f24a"};

    public SudokuBoardApiService() {
        generateCallService = new CallService<>();
        verifyCallService = new CallService<>();
        solveCallService = new CallService<>();
    }

    public Either<ServiceError, GenerateResponse> generateSudoku(int diff, boolean solved) {
        return generateCallService.get("https://sudoku-board.p.rapidapi.com/new-board?diff=" + diff + "&stype=list&solu=" + solved,
                GenerateResponse.class,
                HEADERS
        );
    }

    public Either<ServiceError, VerifyResponse> verifySudoku(String sudoku) {
        return verifyCallService.get("https://sudoku-board.p.rapidapi.com/verify-board?sudo=" + sudoku,
                VerifyResponse.class,
                HEADERS
        );
    }

    public Either<ServiceError, SolveResponse> solveSudoku(String sudoku) {
        return solveCallService.get("https://sudoku-board.p.rapidapi.com/solve-board?sudo=" + sudoku + "&stype=list",
                SolveResponse.class,
                HEADERS
        );
    }
}
