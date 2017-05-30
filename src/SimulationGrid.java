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

    public SimulationGrid(int rows, int cols, int iterations){
        super("Life Simulation");

        this.rows = rows;
        this.cols = cols;
        this.iterations = iterations;

        earth = new Earth(rows, cols);
        turn = new JLabel();
        add(turn, BorderLayout.NORTH);

        updateGrid();

        turn.setText("Turn " + Integer.toString(turnNum));

        timer.setRepeats(true);
        timer.start();

    }

    private void updateGrid(){
        grid = new JPanel();
        grid.setLayout(new GridLayout(cols,rows,0,0));
        Entity[][] currentGrid = earth.getGrid();
        for (Entity[] row : currentGrid){
            for(Entity entity: row){
                if (entity instanceof Carnivore){
                    grid.add(new JLabel("@"));
                } else if (entity instanceof Herbivore){
                    grid.add(new JLabel("&"));
                } else if (entity instanceof Plant){
                    grid.add(new JLabel("*"));
                } else if (entity == null){
                    grid.add(new JLabel("."));
                }
            }
        }
        add(grid, BorderLayout.CENTER);
    }

    private class Updater implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event) {
            turnNum++;
            turn.setText("Turn "+  Integer.toString(turnNum));
            earth.clockCycle();
            updateGrid();
            System.out.println(earth);
            turn.repaint();
            grid.repaint();

            if (turnNum == iterations){
                timer.stop();
            }
        }
    }

}
