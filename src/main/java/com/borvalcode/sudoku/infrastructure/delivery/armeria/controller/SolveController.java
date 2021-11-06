package com.borvalcode.sudoku.infrastructure.delivery.armeria.controller;

import com.borvalcode.sudoku.core.entity.Sudoku;
import com.borvalcode.sudoku.core.usecase.SolveSudoku;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Post;

public class SolveController {

    private final SolveSudoku solveSudoku;

    public SolveController(SolveSudoku solveSudoku) {
        this.solveSudoku = solveSudoku;
    }

    @Post("/sudoku/solve")
    @JsonPost
    public HttpResponse solve(Sudoku sudoku) {
        return Controller.process(this.solveSudoku, sudoku);
    }
}
