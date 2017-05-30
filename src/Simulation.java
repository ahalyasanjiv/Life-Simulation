/**
 * Simulation is a class tests the Earth.
 * Size of dimensions for the grid and number of iterations are taken from the user input.
 */

import java.util.*;
public class Simulation {
    public static void main(String[] args) {
//        // Get dimensions of grid from user
//        Scanner input = new Scanner(System.in);
//        int rows = 0;
//        int columns = 0;
//        int iterations;
//        String str_rows,str_columns,str_iterations;
//        boolean gotRows = false;
//        boolean gotColumns = false;
//
//        while (true) {
//            try {
//                if (!gotRows) {
//                    // Get number of rows from user
//                    System.out.println("Enter number of rows: ");
//                    str_rows = input.next();
//                    rows = Integer.parseInt(str_rows);
//                    if (rows < 0)
//                        throw new DimensionException();
//                    else
//                        gotRows = true;
//                }
//                if (!gotColumns) {
//                    // Get number of columns from user
//                    System.out.println("Enter number of columns: ");
//                    str_columns = input.next();
//                    columns = Integer.parseInt(str_columns);
//                    if (columns < 0)
//                        throw new DimensionException();
//                    else
//                        gotColumns = true;
//                }
//
//                // Get number of iterations from user
//                System.out.println("Enter number of iterations: ");
//                str_iterations = input.next();
//                iterations = Integer.parseInt(str_iterations);
//                if (iterations < 0)
//                    throw new IterationException();
//                else
//                    break;
//
//            }
//
//            // Catch invalid dimension sizes
//            catch (DimensionException exception) {
//                System.out.println(exception.getMessage());
//            }
//
//            // Catch invalid number of iterations
//            catch (IterationException exception) {
//                System.out.println(exception.getMessage());
//            }
//
//            // Catch general input that is not convertible to integer
//            catch (NumberFormatException exception){
//                System.out.println("Input must be a number greater than 0. Try again.\n");
//            }
//
//        }
//
//
//
//        Earth grid = new Earth(rows,columns);
//        // Run specified number of clock cycles for grid
//        System.out.println("Cycle 0");
//        System.out.println(grid);
//        for(int i = 1; i <= iterations; i++) {
//            System.out.println("Cycle " + (i));
//            grid.clockCycle();
//            System.out.println(grid);
//        }
        LifeFrame textFieldFrame = new LifeFrame();
        textFieldFrame.setSize(300, 350);
        textFieldFrame.setResizable(false);
        textFieldFrame.setLocationRelativeTo(null);
        textFieldFrame.setVisible(true);
    }
}