package com.borvalcode.sudoku.domain.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class Error {

    @NonNull
    private ErrorType errorType;

}
