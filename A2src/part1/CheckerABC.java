package part1;

import mains.Grid;
import mains.Puzzle;

public class CheckerABC {

    public CheckerABC() {
    }

    //Default returns true if there are characters in the grid other than 'x' '_' and legal letters then return false
    public static boolean legitChars(Puzzle puzzle, char[][] chars) {
        char unfilled = Puzzle.unfilledChar;
        char blank = Puzzle.blankSymbol;
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[i].length; j++) {
                if (chars[i][j] != unfilled && chars[i][j] != blank && ! puzzle.validLetter(chars[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    //Check for clues on all 4 sides, the first letter that is not x and _ returns false if it is not the same as the clue letter
    public static boolean legitClues(Puzzle puzzle, char[][] chars) {
        char unfilled = Puzzle.unfilledChar;
        char blank = Puzzle.blankSymbol;
        char[] top = puzzle.top;
        char[] bottom = puzzle.bottom;
        char[] left = puzzle.left;
        char[] right = puzzle.right;
        boolean legitleft = true;
        boolean legittop = true;
        boolean legitbottom = true;
        boolean legitright = true;
        char temp;
        //inquire top side clues
        for (int i = 0; i < top.length; i++) {
            if (top[i] != unfilled) {
                for (int j = 0; j < chars.length; j++) {
                    temp = chars[j][i];
                    if (temp != unfilled && temp != blank) {
                        if (temp != top[i]) {
                            legittop = false;
                        }
                        break;
                    }

                }
            }
        }
        //inquire left side clues
        for (int i = 0; i < left.length; i++) {
            if (left[i] != unfilled) {
                for (int j = 0; j < chars[i].length; j++) {
                    temp = chars[i][j];
                    if (temp != unfilled && temp != blank) {
                        if (temp != left[i]) {
                            legitleft = false;
                        }
                        break;
                    }

                }
            }
        }
        //inquire bottom side clues
        for (int i = 0; i < bottom.length; i++) {
            if (bottom[i] != unfilled) {
                for (int j = chars.length - 1; j < 0; j--) {
                    temp = chars[j][i];
                    if (temp != unfilled && temp != blank) {
                        if (temp != bottom[i]) {
                            legitbottom = false;
                        }
                        break;
                    }
                }
            }

        }
        //inquire right side clues
        for (int i = 0; i < right.length; i++) {
            if (right[i] != unfilled) {
                for (int j = chars[i].length - 1; j < 0; j--) {
                    temp = chars[i][j];
                    if (temp != unfilled && temp != blank) {
                        if (temp != right[i]) {
                            legitright = false;
                        }
                        break;
                    }

                }
            }
        }


        return legitleft && legittop && legitbottom && legitright;
    }

    //Indexof != -1 checks for all letters, while indexof == lastindexof determines if a letter is repeated,If the above conditions are not met, return false
    public static boolean legitFillin(Puzzle puzzle, char[][] chars) {
        //check all the letter have appeared and not duplicated by rows and columns
        char[] letters = puzzle.letters;
        boolean legitrow = true;
        for (int i = 0; i < chars.length; i++) {
            String row = new String(chars[i]);
            for (int j = 0; j < letters.length; j++) {
                if (row.indexOf(Puzzle.unfilledChar) == - 1) {
                    legitrow = legitrow && (row.indexOf(letters[j]) != - 1);
                    if (! legitrow) {
                        return false;
                    }
                }
                if (row.indexOf(letters[j]) != row.lastIndexOf(letters[j])) {
                    return false;
                }
            }


        }
        boolean legitcolumn = true;
        for (int i = 0; i < chars[0].length; i++) {
            StringBuffer column = new StringBuffer();
            for (int j = 0; j < chars.length; j++) {
                column.append(chars[j][i]);
            }
            for (int j = 0; j < letters.length; j++) {
                if (column.toString().indexOf(Puzzle.unfilledChar) == - 1) {
                    legitcolumn = legitcolumn && (column.toString().indexOf(letters[j]) != - 1);
                    if (! legitcolumn) {
                        return false;
                    }
                }
                if (column.toString().indexOf(letters[j]) != column.toString().lastIndexOf(letters[j])) {
                    return false;
                }
            }

        }

        return true;
    }

    public static boolean isConsistent(Puzzle puzzle, Grid grid) {
        return isConsistent(puzzle, grid.chars);
    }

    /**
     * Is consistent boolean.
     *
     * @param puzzle the puzzle
     * @param chars  the chars
     * @return the boolean
     * Consistency requires three conditions to be met.
     * 1. the characters in the grid must be the letters in the puzzle
     * 2. the first non blank symbol in that row/column direction is the clued letter
     * 3. if a column or row has been filled, all letters must appear and only once
     */
    public static boolean isConsistent(Puzzle puzzle, char[][] chars) {
        //        rule1
        boolean legitChars = legitChars(puzzle, chars);
        //        rule2
        boolean legitClues = legitClues(puzzle, chars);
        //        rule3
        boolean legitFillin = legitFillin(puzzle, chars);

        return legitChars && legitClues && legitFillin;
    }

    public static boolean isFullGrid(Puzzle puzzle, Grid grid) {
        return isFullGrid(puzzle, grid.chars);
    }


    /**
     * Is full grid boolean.
     *
     * @param puzzle the puzzle
     * @param chars  the chars/grid
     * @return the boolean
     * <p>
     * Returns true by default.
     * Returns false if the length of a row is not equal to the length of the puzzle,
     * or false if there are illegal characters or unfilled squares in the grid
     */
    public static boolean isFullGrid(Puzzle puzzle, char[][] chars) {
        int length = puzzle.size();
        //		int column = puzzle.left.length;
        if (chars.length != length) {
            return false;
        }
        for (int i = 0; i < chars.length; i++) {
            if (chars[i].length != length) {
                return false;
            }
            for (int j = 0; j < chars[i].length; j++) {
                if (! puzzle.validLetter(chars[i][j]) && chars[i][j] != Puzzle.blankSymbol) {
                    return false;
                }
            }
        }
        return true;

    }


    public static boolean isSolution(Puzzle puzzle, Grid grid) {
        return isFullGrid(puzzle, grid) && isConsistent(puzzle, grid);
    }
}
