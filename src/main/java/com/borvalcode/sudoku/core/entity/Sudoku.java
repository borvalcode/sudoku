package com.borvalcode.sudoku.core.entity;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@Data
public class Sudoku {

  @NonNull private List<List<Integer>> sudoku;
}
