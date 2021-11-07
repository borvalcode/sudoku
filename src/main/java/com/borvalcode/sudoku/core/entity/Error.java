package com.borvalcode.sudoku.core.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class Error {

  @NonNull private ErrorType errorType;
}
