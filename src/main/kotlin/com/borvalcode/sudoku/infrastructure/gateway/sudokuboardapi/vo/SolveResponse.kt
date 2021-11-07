package com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo

class SolveResponse(val response: Response) {

    class Response(val board: List<List<Int>>? = null,
                   val solution: List<List<Int>>,
                   val solvable: Boolean = false,
                   val error: String? = null)
}