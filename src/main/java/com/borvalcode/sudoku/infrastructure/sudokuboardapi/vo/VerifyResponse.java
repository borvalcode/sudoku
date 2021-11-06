package com.borvalcode.sudoku.infrastructure.sudokuboardapi.vo;

import lombok.Data;

@Data
public class VerifyResponse {

    private Response response;

    @Data
    public static class Response {
        private String board;
        private boolean solvable;
        private String error;

    }
}
