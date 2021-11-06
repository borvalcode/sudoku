package com.borvalcode.sudoku.core.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@Data
public class Sudoku {

    @NonNull
    private List<List<Integer>> sudoku;

}
