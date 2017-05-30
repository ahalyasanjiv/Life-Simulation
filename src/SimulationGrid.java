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

public class SimulationGrid extends JFrame{
    private Earth earth;
    private JLabel turn;
    private JPanel grid;
    private int turnNum=0;
    private Updater updater = new Updater();
    private Timer timer  = new Timer(500, updater);

    private int rows;
    private int cols;
    private int iterations;
    private int outputStyle;

    public SimulationGrid(int rows, int cols, int iterations, int outputStyle){
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
                    } else if (entity instanceof Plant) {
                        JLabel newLabel = new JLabel();
                        newLabel.setText("*");
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

    private class Updater implements ActionListener{
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
