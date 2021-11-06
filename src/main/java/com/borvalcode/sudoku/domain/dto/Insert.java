package com.borvalcode.sudoku.domain.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Insert {

    @NonNull
    private Sudoku sudoku;

    @NonNull
    private Coordinate coordinate;

    private int value;



}

