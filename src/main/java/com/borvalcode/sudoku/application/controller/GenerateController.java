package com.borvalcode.sudoku.application.controller;

import com.borvalcode.sudoku.domain.dto.Difficulty;
import com.borvalcode.sudoku.domain.usecase.GenerateSudoku;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;

public class GenerateController {

    private final GenerateSudoku generateSudoku;

    public GenerateController(GenerateSudoku generateSudoku) {
        this.generateSudoku = generateSudoku;
    }

    @Get("/sudoku/generate")
    @JsonGet
    public HttpResponse generate(@Param("difficulty") Difficulty difficulty) {
        return Controller.process(this.generateSudoku, difficulty);
    }

}
