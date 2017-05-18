/**
 * Created by ahalyasanjiv on 5/17/17.
 */
import java.util.Scanner;
public class Simulation {
    public static void main(String[] args) {
        // Get dimensions of grid from user
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of rows: ");
        int rows = input.nextInt();
        System.out.println("Enter number of columns: ");
        int columns = input.nextInt();
        // Get number of iterations from user
        System.out.println("Enter number of iterations: ");
        int iterations = input.nextInt();
        Earth grid = new Earth(rows,columns);
        // Run specified number of clock cycles for grid
        System.out.println("Cycle 0");
        System.out.println(grid);
        for(int i = 1; i <= iterations; i++) {
            System.out.println("Cycle " + (i));
            grid.clockCycle();
            System.out.println(grid);
        }
    }
}