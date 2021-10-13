# Easy As ABC Puzzle Rules with Propositional Logic
## Intro
 Expanded implementation of CS5011 P2 in University of St Andrews. This project is research-led. some parts are what the constraint programming research group are actively researching at St Andrews, and they don’t have all the answers about how to do it.

 ‘Easy as ABC’ is a puzzle game in which we have to fill letters in a grid. (It must be said that most people do not find it that “easy”.) Each cell in the grid has to be filled in either with one of the letters in the puzzle, or left blank. The rules of the puzzle state that each letter in the puzzle should appear once in each row and column (but not twice). Also, where a letter outside the grid is given, it has to be the first letter visible in that row/column and direction.

 
 Part 1: Checking Correctness Procedurally
 Implemented basic rules

 Part 2: Making Deductions Procedurally
 Given a puzzle and a partially filled in grid, produce a grid in which some deductions are made and filled in the grid based on the rules and clues

 Part 3: Solving Puzzles Declaratively
 Encoded the problem using propositional logic, and then use the [LogicNG](https://github.com/logic-ng/LogicNG) library to use a SAT solver to solve the puzzles.

 Part 4: Creating a Hint System
 Implemented a basic version. The idea of implementing a hint system is to make it as similar as possible to the way humans solve problems. Therefore, it should deduce procedurally and then declaratively, like a human being, starting with a certain number of definite squares and then filling in the indefinite ones.


 ## Usage
 **A2main.java** contains example code which scans input and makes an appropriate call.

 The package **mains** contains Puzzle.java and Grid.java. with definitions of the two key data structures for puzzles and partial or complete solutions of the puzzles. Some basic data consistency methods are provided, as is a method for printing out a grid together with its puzzle.

 Within the **tests** package, a number of sample puzzles and grids are provided. These are defined as Enum.

code should run using the following command:<br>
java A2main <TEST|SOLVE|HINT> <test-set|problem-to-solve|problem-get-hints-for><br>
For example, to test all of parts 1 to 3 on the staff tests you would run<br>
java A2main TEST StaffAll