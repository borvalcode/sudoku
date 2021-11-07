package com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo

import com.fasterxml.jackson.annotation.JsonProperty

class GenerateResponse(val response: Response) {

    class Response(val difficulty: String, val solution: List<List<Int>>, @JsonProperty("unsolved-sudoku")
    val unsolvedSudoku: List<List<Int>>)
}