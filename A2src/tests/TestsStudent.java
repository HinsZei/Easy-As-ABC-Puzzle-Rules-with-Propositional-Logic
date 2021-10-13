package tests;
/*
 * author Alice Toniolo, Ian Gent
 *
 */

import mains.Puzzle;
import mains.Grid;
import tests.SamplePuzzles;
import tests.SampleGrids;
import part1.CheckerABC;
import part2.ProceduralABC;
import part3.DeclarativeABC;

import tests.TestsBase;


public class TestsStudent {


    // Use this file to add your own tests
    // If you wish to do additional tests like e.g. Junit tests, please feel free

    TestsStudent() {
    }

    public static void test(Puzzle puzzle) {
        DeclarativeABC D = new DeclarativeABC();
        D.setup(puzzle);
        D.createClauses();
        boolean result = D.solvePuzzle();
        D.modelToGrid();
        //                System.out.println(result);
    }
}


