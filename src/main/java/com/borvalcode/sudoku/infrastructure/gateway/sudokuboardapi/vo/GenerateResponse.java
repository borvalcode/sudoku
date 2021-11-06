package com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GenerateResponse {

    private Response response;

    @Data
    public static class Response {
        private String difficulty;
        private List<List<Integer>> solution;

        @JsonProperty("unsolved-sudoku")
        private List<List<Integer>> unsolvedSudoku;

    }
}
