package com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.vo;

import java.util.List;
import lombok.Data;

@Data
public class SolveResponse {

  private Response response;

  @Data
  public static class Response {

    private List<List<Integer>> board;

    private List<List<Integer>> solution;

    private boolean solvable;

    private String error;
  }
}
