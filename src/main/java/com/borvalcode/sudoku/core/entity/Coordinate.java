package com.borvalcode.sudoku.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public final class Coordinate {

  private int x;

  private int y;
}
