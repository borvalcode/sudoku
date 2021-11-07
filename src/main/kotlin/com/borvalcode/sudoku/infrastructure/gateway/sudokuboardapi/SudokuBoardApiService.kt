package com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi

import com.borvalcode.sudoku.infrastructure.gateway.CallService
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.GenerateResponse
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.ServiceError
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.SolveResponse
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo.VerifyResponse
import io.vavr.control.Either

private val DOMAIN = "https://sudoku-board.p.rapidapi.com"
private val HEADERS = arrayOf(
        "x-rapidapi-host",
        "sudoku-board.p.rapidapi.com",
        "x-rapidapi-key",
        "ed9f57f586msh832768573864c9cp12f37ejsn09161213f24a"
)

class SudokuBoardApiService(private val generateCallService: CallService<Void, GenerateResponse>,
                            private val verifyCallService: CallService<Void, VerifyResponse>,
                            private val solveCallService: CallService<Void, SolveResponse>) {

    constructor() :
        this(CallService(DOMAIN), CallService(DOMAIN), CallService(DOMAIN))

    fun generateSudoku(diff: Int, solved: Boolean): Either<ServiceError, GenerateResponse> =
            generateCallService.get(
                    "/new-board?diff=$diff&stype=list&solu=$solved",
                    GenerateResponse::class.java, *HEADERS)

    fun verifySudoku(sudoku: String): Either<ServiceError, VerifyResponse> =
            verifyCallService.get(
                    "/verify-board?sudo=$sudoku",
                    VerifyResponse::class.java,
                    *HEADERS)

    fun solveSudoku(sudoku: String): Either<ServiceError, SolveResponse> =
            solveCallService.get(
                    "/solve-board?sudo=$sudoku&stype=list",
                    SolveResponse::class.java,
                    *HEADERS)



}