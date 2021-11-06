package com.borvalcode.sudoku.infrastructure.sudokuboardapi;

import com.borvalcode.sudoku.domain.dto.Sudoku;

import java.util.List;
import java.util.stream.Collectors;

public class SudokuBoardApiMapper {

    public static String param(Sudoku sudoku) {
        return sudoku.getSudoku().stream()
                .flatMap(List::stream)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
