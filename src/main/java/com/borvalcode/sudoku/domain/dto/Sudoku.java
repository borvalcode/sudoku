package com.borvalcode.sudoku.domain.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@Data
public class Sudoku {

    @NonNull
    private List<List<Integer>> sudoku;

}
