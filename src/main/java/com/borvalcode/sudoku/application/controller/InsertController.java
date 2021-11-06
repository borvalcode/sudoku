package com.borvalcode.sudoku.application.controller;

import com.borvalcode.sudoku.domain.dto.Insert;
import com.borvalcode.sudoku.domain.usecase.InsertValue;
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


