package part3;


import java.util.ArrayList;
import java.util.List;

import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.formulas.Literal;
import org.logicng.formulas.Variable;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;
import org.logicng.solvers.MiniSat;
import org.logicng.solvers.SATSolver;
import org.logicng.datastructures.Tristate;
import org.logicng.datastructures.Assignment;

import mains.Grid;
import mains.Puzzle;
import tests.TestsStaff;

public class DeclarativeABC {

    private Puzzle puzzle;
    private Grid grid;

    FormulaFactory f;
    PropositionalParser p;
    MiniSat solver;
    Tristate result;

    public DeclarativeABC() {
    }

    public DeclarativeABC(Puzzle puzzle) {
        this.setup(puzzle);
    }

    public DeclarativeABC(Puzzle puzzle, Grid grid) {
        this.setup(puzzle, grid);
    }

    public boolean isUnknown() {
        return result == Tristate.UNDEF;
    }

    public boolean isTrue() {
        return result == Tristate.TRUE;
    }

    public boolean isFalse() {
        return result == Tristate.FALSE;
    }

    public void setup(Puzzle puzzle) {
        this.setup(puzzle, new Grid(puzzle.size()));
    }

    public void setup(Puzzle puzzle, Grid grid) {
        this.puzzle = puzzle;
        this.grid = grid;
        this.f = new FormulaFactory();
        this.p = new PropositionalParser(f);
        this.solver = MiniSat.miniSat(f);
        this.result = Tristate.UNDEF;
    }

    // represent the letter in square by ijk where i,j = chars[i][j], k=letter[k], k+1 = 'x', n = length of grid(puzzle), m = length of letters
    // Each method corresponds to a constraint and the result returned is the CNF format of the constraint

    //Each square should be filled in with at least one letter or 'x', for 0<i,j<n, 0<k<=m , ij0|ij1|ij2...|ijm = true
    public String AtLeastALetterPerSquare(char[] letters, Grid grid) {
        StringBuffer constraint = new StringBuffer();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.size(); j++) {
                constraint.append("(");
                for (int k = 0; k <= letters.length; k++) {
                    constraint.append(i).append(j).append(k).append("|");
                }
                constraint.deleteCharAt(constraint.length() - 1).append(")").append("&");
            }
        }
        constraint.deleteCharAt(constraint.length() - 1);
        //        System.out.println(constraint);
        return constraint.toString();
    }

    //Each square should be filled in with at most one letter or 'x', for 0<i,j<n, 0<k<l<=m , ¬ijk|¬ijl = true
    public String AtMostALetterPerSquare(char[] letters, Grid grid) {
        StringBuffer constraint = new StringBuffer();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.size(); j++) {
                for (int k = 0; k <= letters.length; k++) {
                    for (int l = k + 1; l <= letters.length; l++) {
                        constraint.append("(");
                        constraint.append("~").append(i).append(j).append(k).append("|").append("~").append(i).append(j).append(l);
                        constraint.append(")");
                        constraint.append("&");
                    }
                }
            }
        }
        constraint.deleteCharAt(constraint.length() - 1);
        //        System.out.println(constraint);
        return constraint.toString();
    }

    //For each row, each letter appears at least once. for 0<i,j<n, 0<k<m , i0k|i1k|...|ijk = true
    public String AtLeastEveryLetterPerRow(char[] letters, Grid grid) {
        StringBuffer constraint = new StringBuffer();
        for (int k = 0; k < letters.length; k++) {
            for (int i = 0; i < grid.size(); i++) {
                constraint.append("(");
                for (int j = 0; j < grid.size(); j++) {
                    constraint.append(i).append(j).append(k).append("|");
                }
                constraint.deleteCharAt(constraint.length() - 1).append(")").append("&");
            }
        }
        constraint.deleteCharAt(constraint.length() - 1);
        //        System.out.println(constraint);
        return constraint.toString();
    }

    //For each row, each letter appears at most once. for 0<i<n 0<j<l<n, 0<k<m , ¬ijk|¬ilk
    public String AtMostEveryLetterPerRow(char[] letters, Grid grid) {
        StringBuffer constraint = new StringBuffer();
        for (int k = 0; k < letters.length; k++) {
            for (int i = 0; i < grid.size(); i++) {
                for (int j = 0; j < grid.size(); j++) {
                    for (int l = j + 1; l < grid.size(); l++) {
                        constraint.append("(");
                        constraint.append("~").append(i).append(j).append(k).append("|").append("~").append(i).append(l).append(k);
                        constraint.append(")");
                        constraint.append("&");
                    }
                }
            }
        }
        if (constraint.length() > 1) {
            constraint.deleteCharAt(constraint.length() - 1);
        }
        //        System.out.println(constraint);
        return constraint.toString();
    }

    //Similar to Row.For each column, each letter appears at least once. for 0<i,j<n, 0<k<m , 0jk|1jk|...|ijk = true
    public String AtLeastEveryLetterPerCol(char[] letters, Grid grid) {
        StringBuffer constraint = new StringBuffer();
        for (int k = 0; k < letters.length; k++) {
            for (int j = 0; j < grid.size(); j++) {
                constraint.append("(");
                for (int i = 0; i < grid.size(); i++) {
                    constraint.append(i).append(j).append(k).append("|");
                }
                constraint.deleteCharAt(constraint.length() - 1).append(")").append("&");
            }
        }
        constraint.deleteCharAt(constraint.length() - 1);
        //        System.out.println(constraint);
        return constraint.toString();
    }

    //Similar to Row.For each column, each letter appears at most once.for 0<i<l<n 0<j<n, 0<k<m , ¬ijk|¬ljk
    public String AtMostEveryLetterPerCol(char[] letters, Grid grid) {
        StringBuffer constraint = new StringBuffer();
        for (int k = 0; k < letters.length; k++) {
            for (int j = 0; j < grid.size(); j++) {
                for (int i = 0; i < grid.size(); i++) {
                    for (int l = i + 1; l < grid.size(); l++) {
                        constraint.append("(");
                        constraint.append("~").append(i).append(j).append(k).append("|").append("~").append(l).append(j).append(k);
                        constraint.append(")");
                        constraint.append("&");
                    }
                }
            }
        }
        if (constraint.length() > 1) {
            constraint.deleteCharAt(constraint.length() - 1);
        }
        //        System.out.println(constraint);
        return constraint.toString();
    }

    //For each row or column, the first non-blank square must be the first n-m, otherwise all the letters cannot all appear.
    //and when the n-m square is a clue letter, all previous squares must be empty. Suppose the clue letter is k,x = 'x'
    //row: for 0<i,j<n, i0k|i1k^i0x|i2k^i1x^i0x|...|i(n-m)k^i(n-m-1)k^...i0x = 1, similar with column.
    public String ClueConsistency(Puzzle puzzle, Grid grid) {
        int[] top = new int[puzzle.size()];
        int[] left = new int[puzzle.size()];
        int[] bottom = new int[puzzle.size()];
        int[] right = new int[puzzle.size()];
        char[] letter = puzzle.letters;
        int size = grid.size() - letter.length;
        StringBuffer constraint = new StringBuffer();
        //top
        for (int i = 0; i < puzzle.size(); i++) {
            for (int j = 0; j < letter.length; j++) {

                if (puzzle.top[i] == Puzzle.unfilledChar) {
                    top[i] = '_';
                    continue;
                }
                if (puzzle.top[i] == letter[j]) {
                    top[i] = j;
                    continue;
                }
            }
        }
        for (int j = 0; j < grid.size(); j++) {
            if (top[j] != '_') {
                constraint.append("(");
                for (int i = 0; i < size + 1; i++) {
                    if (i > 0) {
                        constraint.append("(");
                        constraint.append(i).append(j).append(top[j]).append("&");
                        for (int k = 0; k < i; k++) {
                            constraint.append(k).append(j).append(letter.length).append("&");
                        }
                        constraint.deleteCharAt(constraint.length() - 1).append(")").append("|");
                    }
                    else {
                        constraint.append(i).append(j).append(top[j]).append("|");
                    }
                    //constraint.append(i).append(j).append(letter.length).append("|");
                }
                constraint.deleteCharAt(constraint.length() - 1).append(")").append("&");
            }
        }
        //left
        for (int i = 0; i < puzzle.size(); i++) {
            for (int j = 0; j < letter.length; j++) {
                if (puzzle.left[i] == Puzzle.unfilledChar) {
                    left[i] = '_';
                    continue;
                }
                if (puzzle.left[i] == letter[j]) {
                    left[i] = j;
                    continue;
                }

            }
        }
        for (int i = 0; i < grid.size(); i++) {
            if (left[i] != '_') {
                constraint.append("(");
                for (int j = 0; j < size + 1; j++) {
                    //                    constraint.append(i).append(j).append(letter.length).append("|");
                    if (j > 0) {
                        constraint.append("(");
                        constraint.append(i).append(j).append(left[i]).append("&");
                        for (int k = 0; k < j; k++) {
                            constraint.append(i).append(k).append(letter.length).append("&");
                        }
                        constraint.deleteCharAt(constraint.length() - 1).append(")").append("|");
                    }
                    else {
                        constraint.append(i).append(j).append(left[i]).append("|");
                    }

                }
                constraint.deleteCharAt(constraint.length() - 1).append(")").append("&");
            }
        }
        //bottom
        for (int i = 0; i < puzzle.size(); i++) {
            for (int j = 0; j < letter.length; j++) {

                if (puzzle.bottom[i] == Puzzle.unfilledChar) {
                    bottom[i] = '_';
                    continue;
                }
                if (puzzle.bottom[i] == letter[j]) {
                    bottom[i] = j;
                    continue;
                }
            }
        }
        for (int j = 0; j < grid.size(); j++) {
            if (bottom[j] != '_') {
                constraint.append("(");
                for (int i = 0; i < size + 1; i++) {
                    //                    constraint.append(grid.size() - 1 - i).append(j).append(letter.length).append("|");
                    if (i > 0) {
                        constraint.append("(");
                        constraint.append(grid.size() - 1 - i).append(j).append(bottom[j]).append("&");
                        for (int k = 0; k < i; k++) {
                            constraint.append(grid.size() - 1 - k).append(j).append(letter.length).append("&");
                        }
                        constraint.deleteCharAt(constraint.length() - 1).append(")").append("|");
                    }
                    else {
                        constraint.append(grid.size() - 1 - i).append(j).append(bottom[j]).append("|");
                    }
                }
                constraint.deleteCharAt(constraint.length() - 1).append(")").append("&");
            }
        }
        //right
        for (int i = 0; i < puzzle.size(); i++) {
            for (int j = 0; j < letter.length; j++) {
                if (puzzle.right[i] == Puzzle.unfilledChar) {
                    right[i] = '_';
                }
                if (puzzle.right[i] == letter[j]) {
                    right[i] = j;
                    continue;
                }

            }
        }
        for (int i = 0; i < grid.size(); i++) {
            if (right[i] != '_') {
                constraint.append("(");
                for (int j = 0; j < size + 1; j++) {

                    //                    constraint.append(i).append(grid.size() - 1 - j).append(letter.length).append("|");
                    if (j > 0) {
                        constraint.append("(");
                        constraint.append(i).append(grid.size() - 1 - j).append(right[i]).append("&");
                        for (int k = 0; k < j; k++) {
                            constraint.append(i).append(grid.size() - 1 - k).append(letter.length).append("&");
                        }
                        constraint.deleteCharAt(constraint.length() - 1).append(")").append("|");
                    }
                    else {
                        constraint.append(i).append(grid.size() - 1 - j).append(right[i]).append("|");
                    }
                }
                constraint.deleteCharAt(constraint.length() - 1).append(")").append("&");
            }
        }
        //corner
        if (top[0] != left[0] && top[0] != '_' && left[0] != '_') {
            constraint.append(0).append(0).append(letter.length).append("&");
        }
        if (top[grid.size() - 1] != right[0] && top[grid.size() - 1] != '_' && right[0] != '_') {
            constraint.append(0).append(grid.size() - 1).append(letter.length).append("&");
        }
        if (left[grid.size() - 1] != bottom[0] && left[grid.size() - 1] != '_' && bottom[0] != '_') {
            constraint.append(grid.size() - 1).append(0).append(letter.length).append("&");
        }
        if (right[grid.size() - 1] != bottom[grid.size() - 1] && right[grid.size() - 1] != '_' && bottom[grid.size() - 1] != '_') {
            constraint.append(grid.size() - 1).append(grid.size() - 1).append(letter.length).append("&");
        }
        constraint.deleteCharAt(constraint.length() - 1);
        //        System.out.println(constraint);
        return constraint.toString();
    }

    public String RemoveEmptyString(ArrayList<String> strings) {
        StringBuffer cnf = new StringBuffer();
        for (String constraint : strings) {
            if (! constraint.equals("")) {
                cnf.append(constraint).append("&");
            }
        }
        cnf.deleteCharAt(cnf.length() - 1);
        return cnf.toString();
    }

    public void createClauses() {
        // Complete this code
        char[] letters = puzzle.letters;
        ArrayList<String> strings = new ArrayList<String>();
        strings.add(AtLeastALetterPerSquare(letters, grid));
        strings.add(AtMostALetterPerSquare(letters, grid));
        strings.add(AtLeastEveryLetterPerRow(letters, grid));
        strings.add(AtMostEveryLetterPerRow(letters, grid));
        strings.add(AtLeastEveryLetterPerCol(letters, grid));
        strings.add(AtMostEveryLetterPerCol(letters, grid));
        strings.add(ClueConsistency(puzzle, grid));
        String cnf = RemoveEmptyString(strings);
        try {
            Formula formula = p.parse(cnf);
            solver.add(formula);
        } catch (ParserException e) {
            e.printStackTrace();
        }

    }

    public boolean solvePuzzle() {
        result = solver.sat();
        if (result.name().equals("TRUE")) {
            return true;
        }
        else {
            return false;
        }
        // Complete this code
        // only there to return some kind of result
    }


    /**
     * Model to grid grid.
     *
     * @return the grid
     *
     * see i j k as the index of chars and letters, parse it to letters to fill in the grid and present the solution.
     *
     */
    public Grid modelToGrid() {
        Assignment model = solver.model();
        //                System.out.println(model);
        String set;
        int i, j, k;
        char value;
        if (model != null) {
            List<Variable> pos = model.positiveVariables();
            for (Variable variable : pos) {
                set = variable.name();
                i = Integer.parseInt(set.charAt(0) + "");
                j = Integer.parseInt(set.charAt(1) + "");
                k = Integer.parseInt(set.charAt(2) + "");
                if (k == puzzle.letters.length) {
                    value = Puzzle.blankSymbol;
                }
                else {
                    value = puzzle.letters[k];
                }
                grid.chars[i][j] = value;
            }
        }
        // Complete this code
        return grid; // only there to return some kind of result
    }


}
