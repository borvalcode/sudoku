package com.borvalcode.sudoku.core.usecase;

import com.borvalcode.sudoku.core.entity.Error;
import io.vavr.control.Either;

public interface UseCase<In, Out> {

  Either<Error, Out> execute(In in);
}
