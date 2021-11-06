package com.borvalcode.sudoku.domain.dto;


import lombok.*;

@Data
@AllArgsConstructor(staticName="of")
@NoArgsConstructor
public final class Coordinate {

    private int x;

    private int y;

}
