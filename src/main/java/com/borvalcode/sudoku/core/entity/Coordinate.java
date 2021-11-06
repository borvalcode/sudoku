package com.borvalcode.sudoku.core.entity;


import lombok.*;

@Data
@AllArgsConstructor(staticName="of")
@NoArgsConstructor
public final class Coordinate {

    private int x;

    private int y;

}
