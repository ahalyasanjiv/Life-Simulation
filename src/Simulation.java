/** Tsz Yan Jamie Fung and Ahalya Sanjiv CSC 221 Final Group Project */

/**
 * Simulation is the driver class that tests the Earth.
 * Size of dimensions for the grid and number of iterations are taken from the user input.
 */

import java.util.*;
import javax.swing.JFrame;

public class Simulation {
    public static void main(String[] args) {
        LifeFrame textFieldFrame = new LifeFrame();
        textFieldFrame.setSize(300, 400);
        textFieldFrame.setResizable(false);
        textFieldFrame.setLocationRelativeTo(null);
        textFieldFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textFieldFrame.setVisible(true);
    }
}