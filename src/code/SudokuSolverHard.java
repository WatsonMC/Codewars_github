package code;

public class SudokuSolverHard {

    /**
     * Method:
     * 1. perform initial validation screens
     *  - for each square check possible values by
     *      a. exlucde those already in square
     *      b. exclude those already in each row
     *  - for each grid of 3x3 squares
     *      a. check if any 2 squares share the same pair of possible values
     *      b. if so, exclude any other possible values for those two squares
     *  - for each square with only 1 possible value, set to that value and return to step 1
     *  - for each grid of 3x3 squares,
     *      - for each possible number (1-9)
     *          a. if only one square exists with that as the possible number, set that number return to step 1
     *  - for each row and column
     *      - for each possible number
     *          a. if only one square in the row or column exist which could have that number, set to that number and return to step 1
     *
     * When all steps have been completed without a trigger back to step 1, begin backtracking stage of algorithm.
     *
     *Suduko grid data structure operations
     * - search possible values in a square
     * - search possible squares for each value
     * - pull row and column values of given reference
     * - search 3x3 grid for paired possible values
     * - search possible values on a column
     * - search possible values on a row
     *
     *
     * 2. Backtracking
     *      for each square
     */

}
