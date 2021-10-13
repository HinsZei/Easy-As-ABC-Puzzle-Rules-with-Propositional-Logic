package part2;

import mains.Grid;
import mains.Puzzle;

import java.util.HashMap;
import java.util.Map;

public class ProceduralABC {

    public ProceduralABC() {
    }

    // The same as the onlyPlaceForLetterRow except for iteration(by column)
    public static Grid onlyPlaceForLetterCol(Puzzle puzzle, Grid grid) {
        // Complete this code
        Grid grid1 = new Grid(grid);
        char[][] chars = grid1.chars;
        char[] letters = puzzle.letters;
        String column;
        int emptyplace;
        char charTofill = Puzzle.unfilledChar;
        int charsNotFilled;
        for (int i = 0; i < chars[0].length; i++) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int j = 0; j < chars.length; j++) {
                stringBuffer.append(chars[j][i]);
            }
            column = stringBuffer.toString();
            if (column.indexOf(Puzzle.unfilledChar) != - 1 && column.indexOf(Puzzle.unfilledChar) == column.lastIndexOf(Puzzle.unfilledChar)) {
                emptyplace = column.indexOf(Puzzle.unfilledChar);
                charsNotFilled = 0;
                for (int j = 0; j < letters.length; j++) {
                    if (column.indexOf(letters[j]) == - 1) {
                        charTofill = letters[j];
                        charsNotFilled++;
                    }
                }
                if (charsNotFilled == 1) {
                    chars[emptyplace][i] = charTofill;
                }
            }
        }
        return grid1; // only there so it returns some kind of result
    }

    // Iterate row by row, using indexOf to determine if there is only one unfilled square in the row,
    // if so then iterate through the letters to determine if only one letter is missing (charsNotFilled) and
    // if this is the case then fill the letter
    public static Grid onlyPlaceForLetterRow(Puzzle puzzle, Grid grid) {
        // Complete this code
        Grid grid1 = new Grid(grid);
        char[][] chars = grid1.chars;
        char[] letters = puzzle.letters;
        String row;
        int emptyplace;
        char charTofill = Puzzle.unfilledChar;
        int charsNotFilled;
        for (int i = 0; i < chars.length; i++) {
            row = new String(chars[i]);
            if (row.indexOf(Puzzle.unfilledChar) != - 1 && row.indexOf(Puzzle.unfilledChar) == row.lastIndexOf(Puzzle.unfilledChar)) {
                emptyplace = row.indexOf(Puzzle.unfilledChar);
                charsNotFilled = 0;
                for (int j = 0; j < letters.length; j++) {
                    if (row.indexOf(letters[j]) == - 1) {
                        charTofill = letters[j];
                        charsNotFilled++;
                    }
                }
                if (charsNotFilled == 1) {
                    chars[i][emptyplace] = charTofill;
                }
            }
        }
        return grid1; // only there so it returns some kind of result
    }

    //Same as the fillInBlanksRow() except for iteration(by column)
    public static Grid fillInBlanksCol(Puzzle puzzle, Grid grid) {
        Grid grid1 = new Grid(grid);
        char[][] chars = grid1.chars;
        char[] letters = puzzle.letters;
        boolean allLettersAppreared = true;
        String column;
        for (int i = 0; i < chars[0].length; i++) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int j = 0; j < chars.length; j++) {
                stringBuffer.append(chars[j][i]);
            }
            column = stringBuffer.toString();
            allLettersAppreared = true;
            for (int j = 0; j < letters.length; j++) {
                allLettersAppreared = allLettersAppreared && (column.indexOf(letters[j]) != - 1);
            }
            if (allLettersAppreared) {
                for (int j = 0; j < chars.length; j++) {
                    if (chars[j][i] == Puzzle.unfilledChar) {
                        chars[j][i] = Puzzle.blankSymbol;
                    }
                }
            }
        }
        // Complete this code
        return grid1; // only there so it returns some kind of result
    }

    //Use indexOf to determine if all letters are present, if so fill all remaining squares with blanks
    public static Grid fillInBlanksRow(Puzzle puzzle, Grid grid) {
        Grid grid1 = new Grid(grid);
        char[][] chars = grid1.chars;
        char[] letters = puzzle.letters;
        boolean allLettersAppreared = true;
        String row;
        for (int i = 0; i < chars.length; i++) {
            row = new String(chars[i]);
            allLettersAppreared = true;
            for (int j = 0; j < letters.length; j++) {
                allLettersAppreared = allLettersAppreared && (row.indexOf(letters[j]) != - 1);
            }
            if (allLettersAppreared) {
                chars[i] = row.replace(Puzzle.unfilledChar, Puzzle.blankSymbol).toCharArray();
            }
        }
        // Complete this code
        return grid1; // only there so it returns some kind of result
    }

    //Determine the adjacent clues in the 4 corners, if they are not '_' and not equal, the squares in the corners must be empty
    public static Grid differentCorners(Puzzle puzzle, Grid grid) {
        Grid grid1 = new Grid(grid);
        char[][] chars = grid1.chars;
        //top left corner
        if (puzzle.top[0] != puzzle.left[0] && puzzle.top[0] != Puzzle.unfilledChar && puzzle.left[0] != Puzzle.unfilledChar) {
            if (chars[0][0] == Puzzle.unfilledChar) {
                chars[0][0] = Puzzle.blankSymbol;
            }
        }
        //top right corner
        if (puzzle.top[puzzle.size() - 1] != puzzle.right[0] && puzzle.top[puzzle.size() - 1] != Puzzle.unfilledChar && puzzle.right[0] != Puzzle.unfilledChar) {
            if (chars[0][grid.size() - 1] == Puzzle.unfilledChar) {
                chars[0][grid.size() - 1] = Puzzle.blankSymbol;
            }
        }
        //bottom left corner
        if (puzzle.bottom[0] != puzzle.left[puzzle.size() - 1] && puzzle.bottom[0] != Puzzle.unfilledChar && puzzle.left[puzzle.size() - 1] != Puzzle.unfilledChar) {
            if (chars[grid.size() - 1][0] == Puzzle.unfilledChar) {
                chars[grid.size() - 1][0] = Puzzle.blankSymbol;
            }
        }
        //bottom right corner
        if (puzzle.bottom[puzzle.size() - 1] != puzzle.right[puzzle.size() - 1] && puzzle.bottom[puzzle.size() - 1] != Puzzle.unfilledChar && puzzle.right[puzzle.size() - 1] != Puzzle.unfilledChar) {
            if (chars[grid.size() - 1][grid.size() - 1] == Puzzle.unfilledChar) {
                chars[grid.size() - 1][grid.size() - 1] = Puzzle.blankSymbol;
            }
        }
        // Complete this code
        return grid1; // only there so it returns some kind of result
    }

    //Use a hashmap to find letters that appear only once in the clue and then fill in the adjacent rows or columns
    public static Grid commonClues(Puzzle puzzle, Grid grid) {
        Grid grid1 = new Grid(grid);
        char[][] chars = grid1.chars;
        char[] top = puzzle.top;
        char[] bottom = puzzle.bottom;
        char[] left = puzzle.left;
        char[] right = puzzle.right;
        Map<Character, Boolean> notDuplicatedLetter = new HashMap<Character, Boolean>();
        //top
        if (new String(top).indexOf(Puzzle.unfilledChar) == - 1) {
            for (char c : top) {
                notDuplicatedLetter.put(c, ! notDuplicatedLetter.containsKey(c));
            }
            for (char c : top) {
                if (notDuplicatedLetter.get(c)) {
                    chars[0][new String(top).indexOf(c)] = c;
                }
            }
            notDuplicatedLetter.clear();
        }

        //bottom
        if (new String(bottom).indexOf(Puzzle.unfilledChar) == - 1) {
            for (char c : bottom) {
                notDuplicatedLetter.put(c, ! notDuplicatedLetter.containsKey(c));
            }
            for (char c : bottom) {
                if (notDuplicatedLetter.get(c)) {
                    chars[grid.size() - 1][new String(bottom).indexOf(c)] = c;
                }
            }
            notDuplicatedLetter.clear();
        }


        //left
        if (new String(left).indexOf(Puzzle.unfilledChar) == - 1) {
            for (char c : left) {
                notDuplicatedLetter.put(c, ! notDuplicatedLetter.containsKey(c));
            }
            for (char c : left) {
                if (notDuplicatedLetter.get(c)) {
                    chars[new String(left).indexOf(c)][0] = c;
                }
            }
            notDuplicatedLetter.clear();
        }


        //right
        if (new String(right).indexOf(Puzzle.unfilledChar) == - 1) {
            for (char c : right) {
                notDuplicatedLetter.put(c, ! notDuplicatedLetter.containsKey(c));
            }
            for (char c : right) {
                if (notDuplicatedLetter.get(c)) {
                    chars[new String(right).indexOf(c)][grid.size() - 1] = c;
                }
            }
            notDuplicatedLetter.clear();
        }
        // Complete this code
        return grid1; // only there so it returns some kind of result
    }

    //  Additional constraint for Part4，Similar to differentCoreners, if two clues are the same then the corner square must be the same letter
    public static Grid sameCorners(Puzzle puzzle, Grid grid) {
        Grid grid1 = new Grid(grid);
        char[] top = puzzle.top;
        char[] left = puzzle.left;
        char[] bottom = puzzle.bottom;
        char[] right = puzzle.right;
        if (top[0] == left[0] && top[0] != '_' && left[0] != '_') {
            grid1.chars[0][0] = top[0];
        }
        if (top[grid.size() - 1] == right[0] && top[grid.size() - 1] != '_' && right[0] != '_') {
            grid1.chars[0][grid.size() - 1] = right[0];
        }
        if (left[grid.size() - 1] == bottom[0] && left[grid.size() - 1] != '_' && bottom[0] != '_') {
            grid1.chars[grid.size() - 1][0] = bottom[0];
        }
        if (right[grid.size() - 1] == bottom[grid.size() - 1] && right[grid.size() - 1] != '_' && bottom[grid.size() - 1] != '_') {
            grid1.chars[grid.size() - 1][grid.size() - 1] = right[grid.size() - 1];
        }
        return grid1;
    }

    //Additional constraint for Part4, use hashmap to find recurring letters that are in the clue(indexOf),
    // if the letter already appears in an adjacent row or column, then the rest of the places must be blank
    public static Grid fillInBlankPairedLetter(Puzzle puzzle, Grid grid) {
        Grid grid1 = new Grid(grid);
        char[][] chars = grid1.chars;
        char[] top = puzzle.top;
        char[] bottom = puzzle.bottom;
        char[] left = puzzle.left;
        char[] right = puzzle.right;
        String row, col;
        Map<Character, Boolean> duplicatedLetter = new HashMap<Character, Boolean>();
        //top
        for (char c : top) {
            duplicatedLetter.put(c, duplicatedLetter.containsKey(c));
        }
        row = new String(chars[0]);
        for (char c : top) {
            if (duplicatedLetter.get(c)) {
                int index = row.indexOf(c);
                for (int i = 0; i < top.length; i++) {
                    if (i != index && top[i] == c && index != - 1) {
                        chars[0][i] = Puzzle.blankSymbol;
                    }
                }
            }
        }
        duplicatedLetter.clear();
        //bottom
        for (char c : bottom) {
            duplicatedLetter.put(c, duplicatedLetter.containsKey(c));
        }
        row = new String(chars[grid.size() - 1]);
        for (char c : bottom) {
            if (duplicatedLetter.get(c)) {
                int index = row.indexOf(c);
                for (int i = 0; i < bottom.length; i++) {
                    if (i != index && bottom[i] == c && index != - 1) {
                        chars[grid.size() - 1][i] = Puzzle.blankSymbol;
                    }
                }
            }
        }
        duplicatedLetter.clear();
        //left
        for (char c : left) {
            duplicatedLetter.put(c, duplicatedLetter.containsKey(c));
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < left.length; i++) {
            stringBuffer.append(chars[i][0]);
        }
        col = stringBuffer.toString();
        for (char c : left) {
            if (duplicatedLetter.get(c)) {
                int index = col.indexOf(c);
                for (int i = 0; i < left.length; i++) {
                    if (i != index && left[i] == c && index != - 1) {
                        chars[i][0] = Puzzle.blankSymbol;
                    }
                }
            }
        }
        duplicatedLetter.clear();
        //right
        for (char c : right) {
            duplicatedLetter.put(c, duplicatedLetter.containsKey(c));
        }
        stringBuffer = new StringBuffer();
        for (int i = 0; i < right.length; i++) {
            stringBuffer.append(chars[i][grid.size() - 1]);
        }
        col = stringBuffer.toString();
        for (char c : right) {
            if (duplicatedLetter.get(c)) {
                int index = col.indexOf(c);
                for (int i = 0; i < right.length; i++) {
                    if (i != index && right[i] == c && index != - 1) {
                        chars[i][grid.size() - 1] = Puzzle.blankSymbol;
                    }
                }
            }
        }
        duplicatedLetter.clear();
        return grid1;
    }
}
