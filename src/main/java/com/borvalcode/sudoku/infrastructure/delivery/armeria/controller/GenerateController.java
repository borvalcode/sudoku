package com.borvalcode.sudoku.infrastructure.delivery.armeria.controller;

import com.borvalcode.sudoku.core.entity.Difficulty;
import com.borvalcode.sudoku.core.usecase.GenerateSudoku;
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
