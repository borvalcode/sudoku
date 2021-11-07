package com.borvalcode.sudoku.core.usecase

import com.borvalcode.sudoku.core.entity.Error
import io.vavr.control.Either

interface UseCase<In, Out> {
    fun execute(input: In): Either<Error, Out>
}