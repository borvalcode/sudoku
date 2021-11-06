package com.borvalcode.sudoku.domain.dto;

public enum ErrorType {
    UNKNOWN_ERROR,
    GENERATE_ERROR,
    INSERT_ERROR_SUDOKU_COMPLETE,
    INSERT_ERROR_NOT_VALID,
    INSERT_ERROR_NOT_EMPTY,
    VERIFY_ERROR,
    SOLVE_ERROR;
}
