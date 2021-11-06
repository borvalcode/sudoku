package com.borvalcode.sudoku.domain.usecase;

import com.borvalcode.sudoku.domain.dto.Difficulty;
import com.borvalcode.sudoku.domain.dto.Error;
import com.borvalcode.sudoku.domain.dto.ErrorType;
import com.borvalcode.sudoku.domain.dto.Sudoku;
import com.borvalcode.sudoku.infrastructure.sudokuboardapi.SudokuBoardApiService;
import com.borvalcode.sudoku.infrastructure.sudokuboardapi.vo.GenerateResponse;
import com.borvalcode.sudoku.infrastructure.sudokuboardapi.vo.ServiceError;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenerateSudokuTest {

    private final SudokuBoardApiService sudokuBoardApiService;
    private final GenerateSudoku generateSudoku;

    public GenerateSudokuTest() {
        sudokuBoardApiService = mock(SudokuBoardApiService.class);
        this.generateSudoku = new GenerateSudoku(sudokuBoardApiService);
    }

    @Test
    void should_return_a_full_sudoku() throws IOException {

        when(this.sudokuBoardApiService.generateSudoku(1, true))
                .thenReturn(aSudokuBoardApiResponse());

        Either<Error, Sudoku> actualSudoku = this.generateSudoku.execute(Difficulty.NONE);

        assertThat(actualSudoku.isRight()).isTrue();
        assertThat(actualSudoku.get().getSudoku()).hasSameElementsAs(expectedSudoku().getSudoku());

    }

    @Test
    void should_return_generate_error_when_service_fails() {

        when(this.sudokuBoardApiService.generateSudoku(1, true))
                .thenReturn(Either.left(ServiceError.IO));

        Either<Error, Sudoku> actualSudoku = this.generateSudoku.execute(Difficulty.NONE);

        assertThat(actualSudoku.isLeft()).isTrue();
        assertThat(actualSudoku.getLeft()).isEqualTo(Error.of(ErrorType.GENERATE_ERROR));

    }

    private Either<ServiceError, GenerateResponse> aSudokuBoardApiResponse() throws IOException {
        String apiResponse = Files.readString(Paths.get("src/test/resources/sudokuBoardApiResponse_1_true.json"));
        return Either.right(new ObjectMapper().readValue(apiResponse, GenerateResponse.class));
    }

    private static Sudoku expectedSudoku() {
        return Sudoku.of(List.of(
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9),
                List.of(4, 5, 6, 7, 8, 9, 1, 3, 2),
                List.of(7, 8, 9, 1, 2, 3, 4, 5, 6),
                List.of(2, 1, 4, 8, 3, 7, 6, 9, 5),
                List.of(3, 6, 5, 2, 9, 1, 8, 7, 4),
                List.of(9, 7, 8, 6, 4, 5, 2, 1, 3),
                List.of(6, 3, 2, 9, 7, 8, 5, 4, 1),
                List.of(8, 9, 1, 5, 6, 4, 3, 2, 7),
                List.of(5, 4, 7, 3, 1, 2, 9, 6, 8)
        ));
    }

}