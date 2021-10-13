package part4;

import mains.Grid;
import mains.Puzzle;
import part2.ProceduralABC;
import part3.DeclarativeABC;

public class HintABC {

    //     For myself, my habitual approach is to complete the 100% certainty fill first and then complete the uncertainty.
    //     In this approach, a portion of the fill is completed using the Procedural reasoning approach
    //     and then the answer is given directly using the Declarative approach
    //    The Declarative reasoning section needs to be optimized,
    //    and it would be much better if the reasoning could be split up instead of going straight to the end in one step
    public void Hint(Puzzle puzzle, Grid grid) {
        System.out.println("Firstly start with the corners. If any corner has different clues in the row and column adjacent to it, there must be an X in that square");
        System.out.println("Ditto,if any corner has the same clues in the row and column adjacent to it, " );
        System.out.println("there must be an that letter in that square,here is the filled grid based on this rule:");
        grid = ProceduralABC.sameCorners(puzzle, grid);
        grid = ProceduralABC.differentCorners(puzzle, grid);
        puzzle.printPuzzleGrid(grid);
        System.out.println("If every clue on a side is given in full and a letter appears only once," );
        System.out.println(" then the corresponding square in the first row or column must be that letter");
        System.out.println("If one side of the clue shows a paired letter and the corresponding first row or column already shows this letter," );
        System.out.println(" then another square must be filled with blanks,here is the filled grid based on this rule:");
        grid = ProceduralABC.commonClues(puzzle, grid);
        grid = ProceduralABC.fillInBlankPairedLetter(puzzle, grid);
        puzzle.printPuzzleGrid(grid);
        System.out.println("Then you can try to see if there are any rows or columns in which all the letters already appear." );
        System.out.println(" If so, fill in the blanks in the other squares of the row or column,here is the filled grid based on this rule:");
        grid = ProceduralABC.fillInBlanksCol(puzzle, grid);
        grid = ProceduralABC.fillInBlanksRow(puzzle, grid);
        puzzle.printPuzzleGrid(grid);
        System.out.println("The last thing that is 100% certain is that if there is a row or column where only one letter does not appear and only one square is left unfilled, ");
        System.out.println("then this square must be filled with that letter,here is the filled grid based on this rule:");
        grid = ProceduralABC.onlyPlaceForLetterCol(puzzle, grid);
        grid = ProceduralABC.onlyPlaceForLetterRow(puzzle, grid);
        System.out.println("These are the facts that must hold true for the rules of the Easy As ABC puzzle. After the definite parts have been completed,");
        System.out.println(" the remaining squares need to be completed using inference. Deducing after converting the rules into constraints gives the final answer.");
        DeclarativeABC D = new DeclarativeABC();
        D.setup(puzzle, grid);
        D.createClauses();
        boolean result = D.solvePuzzle();
        if (result) {
            System.out.println("The solution is:");
            grid = D.modelToGrid();
            puzzle.printPuzzleGrid(grid);
        }
        else {
            System.out.println("Oops! There is no solution for this puzzle!");
        }

    }

    public static void giveHints(Puzzle puzzle) {
        HintABC H = new HintABC();
        H.Hint(puzzle, new Grid(puzzle.size()));

    }

}
