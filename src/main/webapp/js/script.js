(function () {

    var generateButton = document.getElementById("generate");
    var solveButton = document.getElementById("solve");
    var sudokuCells = document.getElementsByClassName("sudoku-cell");
    var numberCells = document.getElementsByClassName("number-cell");
    var solvedDiv = document.getElementById("solved");

    generateButton.addEventListener("click", function(e) {
          e.preventDefault();
          generate();
      });

    solveButton.addEventListener("click", function(e) {
          e.preventDefault();
          solve();
      });

    Array.from(sudokuCells).forEach(sudokuCell => {
        sudokuCell.addEventListener("click", function(e) {
            e.preventDefault();
             activate(this);
         });
    });

    Array.from(numberCells).forEach(numberCell => {
        numberCell.addEventListener("click", function(e) {
            e.preventDefault();
             setValue(this);
         });
    });

    generate();

    function generate() {
        reset();
        fetch("sudoku/generate?difficulty=" + $("#difficulty").val())
        .then(response => response.json())
        .then(data => printResponse(data))
        .catch(error => console.log("Error: " + error));
    }

    function solve() {
        fetch("sudoku/solve", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(getSolveJsonData())
        })
        .then(response => response.json())
        .then(data => {
            printResponse(data);
        })
        .catch(error => console.log("Error: " + error))
    }

    function inactivateAll() {
        Array.from(sudokuCells).forEach(sudokuCell => {
            sudokuCell.classList.remove("active");
            sudokuCell.classList.remove("selected");
            sudokuCell.classList.remove("referenced");
        });
    }

    function printSelectorNumbers() {
        Array.from(numberCells).forEach((numberCell, i) => {
            numberCell.textContent = i+1;
        });
    }

    function reset() {
        solvedDiv.classList.add("hidden");
        inactivateAll();
        printSelectorNumbers();
    }

    function printResponse(response) {
        response["sudoku"].forEach(function(row, x) {
         row.forEach(function(cell, y) {
            paintSudoku(x, y, cell);
         });
        });
        for (let i = 1; i <= 9; i++) {
            removeNumberIfFinished(i);
        }
        printSolvedIfSolved();
        activateOthers();
    }

    function removeValidationError() {
        var validationErrorTd = document.getElementsByClassName("validationError");
        if (validationErrorTd.length > 0) {
            validationErrorTd[0].textContent = " ";
            validationErrorTd[0].classList.remove("validationError")
        }
    }

    function getSolveJsonData() {
        removeValidationError();
        var jsonData = {};
        jsonData["sudoku"] = getSudoku();
        return jsonData;
    }

    function activate(object) {
        inactivateAll();
        object.classList.add("active");
        activateOthers();
    }

    function activateOthers() {
        var object = document.querySelector(".sudokutable td.active");
        if (object.textContent != ' ') {
            Array.from(sudokuCells).forEach((sudokuCell, index) => {
                if (sudokuCell.textContent == object.textContent) {
                    sudokuCell.classList.add("selected");
                    var row = getRow(index);
                    var col = getCol(index);
                    Array.from(sudokuCells).forEach((sudokuCellIn, indexIn) => {
                      if (getRow(indexIn) == row || getCol(indexIn) == col) {
                        addReference(sudokuCellIn);
                      }
                    });
                    var boxRow = getBoxRow(row);
                    var boxCol = getBoxCol(col);
                    for (var i=0; i< 3; i++) {
                      for (var j=0; j< 3; j++) {
                        addReference(sudokuCells[getIndex(boxRow*3 + i, boxCol*3 + j)]);
                      }
                    }
                }
            });
         }
    }

    function addReference(sudokuCell) {
      sudokuCell.classList.remove("referenced");
      sudokuCell.classList.add("referenced");
    }

    function getIndex(row, col) {
      return row * 9 + col;
    }

    function getBoxRow(row) {
      return Math.floor(row / 3);
    }

    function getBoxCol(col) {
      return Math.floor(col / 3);
    }

    function getRow(index) {
      return Math.floor(index / 9);
    }

    function getCol(index) {
      return index % 9;
    }

    function paintSudoku(x, y, cell) {
        $("#sudoku").find("tr:eq(" + x + ")").find("td:eq(" + y + ")").text(cell != "0" ? cell : " ");
    }

    function getSudoku() {
        var rows =  [];
        $(".sudokutable tr").each(function(e, el) {
            var cells =  [];
            $(el).find("td").each(function(e1, el1) {
                var value = $(el1).text() == ' ' ? 0 : parseInt($(el1).text());
                cells.push(value);
            });
            rows.push(cells);
        });
        return rows;
    }

    function getJsonData(object) {
        var jsonData = {};
        jsonData["sudoku"] = {};
        jsonData["sudoku"]["sudoku"] = getSudoku();
        jsonData["coordinate"] = {
            "x": $("#sudoku td.active").parent().index(),
            "y": $("#sudoku td.active").index()
        }
        jsonData["value"] = parseInt($(object).text());
        return jsonData;
    }

    function setValue(object) {
        $("#sudoku td.validationError").text(' ');
        $("#sudoku td.validationError").removeClass("validationError");
        if($("#sudoku td.active").length == 1 && $("#sudoku td.active").text() == ' ') {
            callInsert(object);
        }
    }

    function callInsert(object) {

        fetch("sudoku/insert", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(getJsonData(object))
        })
        .then(response => response.json())
        .then(data => {
            if (data.errorType == "INSERT_ERROR_NOT_VALID") {
               $("#sudoku td.active").addClass("validationError");
               $("#sudoku td.active").text($(object).text());
            } else if (data.sudoku != null) {
                printResponse(data);
            }
        })
        .catch(function(error) {
           console.error("Error inserting: " + error)
           if (error.responseJSON.errorType == "INSERT_ERROR_NOT_VALID") {
           }
         });

    }

    function printSolvedIfSolved() {
        var counter = 0;
        $(".sudokutable td").each(function(e, el) {
            if ($(el).text() == " ") {
                counter++;
            }
        });
        if (counter == 0) {
            $("#solved").removeClass("hidden");
        }

    }

    function removeNumberIfFinished(number) {
        var counter = 0;
        $(".sudokutable td").each(function(e, el) {
            if ($(el).text() == number) {
                counter++;
            }
        });
        if (counter == 9) {
            $(".numbers td").each(function(e, el) {
                if ($(el).text() == number) {
                    $(el).text(' ');
                }
            });
        }
    }
})()