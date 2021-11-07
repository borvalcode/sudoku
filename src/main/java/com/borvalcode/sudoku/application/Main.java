package com.borvalcode.sudoku.application;

import com.borvalcode.sudoku.core.usecase.GenerateSudoku;
import com.borvalcode.sudoku.core.usecase.InsertValue;
import com.borvalcode.sudoku.core.usecase.SolveSudoku;
import com.borvalcode.sudoku.core.usecase.VerifyInsertValue;
import com.borvalcode.sudoku.infrastructure.delivery.armeria.controller.GenerateController;
import com.borvalcode.sudoku.infrastructure.delivery.armeria.controller.InsertController;
import com.borvalcode.sudoku.infrastructure.delivery.armeria.controller.SolveController;
import com.borvalcode.sudoku.infrastructure.gateway.sudokuboardapi.SudokuBoardApiService;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.file.FileService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

public class Main {

  public static void main(String[] args) {

    SudokuBoardApiService sudokuBoardApiService = new SudokuBoardApiService();

    GenerateSudoku generateSudoku = new GenerateSudoku(sudokuBoardApiService);
    SolveSudoku solveSudoku = new SolveSudoku(sudokuBoardApiService);
    VerifyInsertValue verifyInsertValue = new VerifyInsertValue(solveSudoku);
    InsertValue insertValue = new InsertValue(verifyInsertValue);

    Server server =
        Server.builder()
            .http(4567)
            .annotatedService(new GenerateController(generateSudoku))
            .annotatedService(new InsertController(insertValue))
            .annotatedService(new SolveController(solveSudoku))
            .serviceUnder("/docs", new DocService())
            .serviceUnder("/", FileService.of(Paths.get("src/main/webapp")))
            .accessLogWriter(AccessLogWriter.common(), true)
            .build();

    CompletableFuture<Void> future = server.start();
    future.join();
  }
}
