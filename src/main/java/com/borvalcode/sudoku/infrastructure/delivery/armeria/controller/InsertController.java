package com.borvalcode.sudoku.infrastructure.delivery.armeria.controller;

import com.borvalcode.sudoku.core.entity.Insert;
import com.borvalcode.sudoku.core.usecase.InsertValue;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Post;

public class InsertController {

  private final InsertValue insertValue;

  public InsertController(InsertValue insertValue) {
    this.insertValue = insertValue;
  }

  @Post("/sudoku/insert")
  @JsonPost
  public HttpResponse insert(Insert insert) {
    return Controller.process(this.insertValue, insert);
  }
}
