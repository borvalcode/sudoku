package com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi

import com.borvalcode.sudoku.core.entity.Sudoku


fun param(sudoku: Sudoku): String =
            sudoku.sudoku.asSequence()
                .flatten()
                .map { int -> int.toString() }
                .joinToString()
