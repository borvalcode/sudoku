package com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo

class VerifyResponse(private val response: Response? = null) {

    class Response(private val board: String? = null,
                   private val solvable: Boolean = false,
                   private val error: String? = null)
}