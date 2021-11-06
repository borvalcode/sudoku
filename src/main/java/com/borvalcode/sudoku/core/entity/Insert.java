package com.borvalcode.sudoku.core.entity;

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

