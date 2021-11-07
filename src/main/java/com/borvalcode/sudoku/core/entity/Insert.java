package com.borvalcode.sudoku.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Insert {

  @NonNull private Sudoku sudoku;

  @NonNull private Coordinate coordinate;

  private int value;
}
