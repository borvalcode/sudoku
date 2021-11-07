package com.borvalcode.sudoku.infrastructure.delivery.armeria.controller

import com.borvalcode.sudoku.core.entity.Insert
import com.borvalcode.sudoku.core.usecase.InsertValue
import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.server.annotation.Post

class InsertController(private val insertValue: InsertValue) {

    @Post("/sudoku/insert")
    @JsonPost
    fun insert(insert: Insert): HttpResponse = Controller.process(insertValue, insert)
}