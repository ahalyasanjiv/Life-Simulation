/**
 * Created by macaron on 5/29/17.
 */
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.Timer;

/**
 * The SimulationFrame class provides the GUI for the simulation.
 */
public class SimulationFrame extends JFrame{
    /**
     * Earth object used for simulation.
     */
    private Earth earth;

    /**
     * JLabel for displaying the current turn, or when window display is turned off, simulation status of the terminal is shown.
     */
    private JLabel turn;

    /**
     * JPanel for holding the grid.
     */
    private JPanel grid;

    /**
     * Used for storing the current turn.
     */
    private int turnNum=0;

    /**
     * ActionListener for updating the grid and turn number. Used with a timer to update every 500 milliseconds
     */
    private Updater updater = new Updater();

    /**
     * Timer for scheduling updates every 500 milliseconds.
     */
    private Timer timer  = new Timer(500, updater);

    /**
     * Stores the number of rows in the simulation.
     */
    private int rows;

    /**
     * Stores the number of columns in the simulation.
     */
    private int cols;

    /**
     * Stores the numbers of iterations in the simulation.
     */
    private int iterations;

    /**
     * Stores the output style retrieve from the outputList in LifeFrame class.
     * 0. Display in new window and terminal
     * 1. Display in new window only
     * 2. Display in terminal only
     */
    private int outputStyle;

    /**
     * Constructor for the SimulationFrame.
     *
     * @param rows Number of rows retrieved from user
     * @param cols Number of columns retrieved from user
     * @param iterations Number of iterations retrieved from user
     * @param outputStyle Retrieved from JComboBox outputList
     */
    public SimulationFrame(int rows, int cols, int iterations, int outputStyle){
        super("Life Simulation");

        this.rows = rows;
        this.cols = cols;
        this.iterations = iterations;
        this.outputStyle = outputStyle;

        earth = new Earth(rows, cols);
        turn = new JLabel();

        updateGrid();

        if (outputStyle != 2) {
            turn.setText("Turn " + Integer.toString(turnNum));
            add(turn, BorderLayout.NORTH);
        } else {
            turn.setText("Please check the terminal...");
            add(turn, BorderLayout.CENTER);
        }

        timer.setRepeats(true);
        timer.start();

    }

    /**
     * Method for updating the grid. If only window output is selected, the method will only display the output to a new SimulationFrame.
     * If only terminal output is selected, the SimulationFrame will display text regarding the status of the simulation.
     */
    private void updateGrid(){
        Entity[][] currentGrid = earth.getGrid();

        if (outputStyle == 0  || outputStyle == 2) {
            System.out.println("Turn " + Integer.toString(turnNum));
            System.out.println(earth);
        }

        if (outputStyle == 0 || outputStyle == 1) {
            grid = new JPanel();
            grid.setLayout(new GridLayout(rows, cols, 0, 0));

            int fontSize = Math.min(rows, cols) * 50 / 30;
            if (fontSize < 16) {
                fontSize = 16;
            }

            for (Entity[] row : currentGrid) {
                for (Entity entity : row) {
                    if (entity instanceof Carnivore) {
                        JLabel newLabel = new JLabel();
                        newLabel.setText("@");
                        newLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, fontSize));
                        grid.add(newLabel);
                    } else if (entity instanceof Herbivore) {
                        JLabel newLabel = new JLabel();
                        newLabel.setText("&");
                        newLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, fontSize));
                        grid.add(newLabel);
                    } else if (entity instanceof Bush) {
                        JLabel newLabel = new JLabel();
                        newLabel.setText("*");
                        newLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, fontSize));
                        grid.add(newLabel);
                    } else if (entity instanceof Grass) {
                        JLabel newLabel = new JLabel();
                        newLabel.setText("\"");
                        newLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, fontSize));
                        grid.add(newLabel);
                    } else if (entity instanceof Rock) {
                        JLabel newLabel = new JLabel();
                        newLabel.setText("#");
                        newLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, fontSize));
                        grid.add(newLabel);
                    } else if (entity == null) {
                        JLabel newLabel = new JLabel();
                        newLabel.setText(".");
                        newLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, fontSize));
                        grid.add(newLabel);
                    }
                }
            }
            add(grid, BorderLayout.CENTER);
        }
    }

    /**
     * The Updater private class is an inner class under SimulationFrame. It is used to refresh the components in the frame
     * so that the updates display.
     */
    private class Updater implements ActionListener{
        /**
         * Overriden method for the handler. It updates the earth and increments turn,
         * and displays to terminal/SimulationFrame accordingly.
         * @param event
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            turnNum++;
            earth.clockCycle();

            if (outputStyle != 2) {
                turn.setText("Turn "+  Integer.toString(turnNum));
                remove(grid);
            }

            updateGrid();

            if (outputStyle !=2) {
                turn.repaint();
                grid.repaint();
            }

            if (turnNum == iterations){
                timer.stop();
                if (outputStyle == 2 ) {
                    turn.setText("Simulation finished in the terminal!");
                }
            }
        }
    }

}
