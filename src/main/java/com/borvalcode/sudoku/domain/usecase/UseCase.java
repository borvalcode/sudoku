package com.borvalcode.sudoku.domain.usecase;

import com.borvalcode.sudoku.domain.dto.Error;
import io.vavr.control.Either;

public interface UseCase <In, Out>{

    Either<Error, Out> execute(In in);
}
