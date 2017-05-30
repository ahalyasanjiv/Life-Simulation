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

public class LifeFrame extends JFrame{
    private final JTextField rowField;
    private final JTextField colField;
    private final JTextField iterationField;
    private final JButton submitButton;
    private final BorderLayout inputLayout;
    private final JPanel textFields;

    public LifeFrame(){
        super("Life Simulation");

        inputLayout = new BorderLayout(5,5);
        textFields = new JPanel();
        textFields.setLayout(new GridLayout(6, 1));

        rowField = new JTextField();
        textFields.add(new JLabel("Enter number of rows:"));
        textFields.add(rowField);

        colField = new JTextField();
        textFields.add(new JLabel("Enter number of columns:"));
        textFields.add(colField);

        iterationField = new JTextField();
        textFields.add(new JLabel("Enter number of iterations:"));
        textFields.add(iterationField);

        add(textFields,BorderLayout.NORTH);
        submitButton = new JButton("OK");
        add(submitButton,BorderLayout.SOUTH);

        //Event Handlers
        ButtonHandler handler = new ButtonHandler();
        rowField.addActionListener(handler);
        colField.addActionListener(handler);
        iterationField.addActionListener(handler);
        submitButton.addActionListener(handler);
    }

    private class ButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event) throws DimensionException, IterationException, NumberFormatException{
            try{
                int row = Integer.parseInt(rowField.getText());
                if (row <= 0) { throw new DimensionException();}

                int col = Integer.parseInt(colField.getText());
                if (col <= 0) {throw new DimensionException();}

                int iterations = Integer.parseInt(iterationField.getText());
                if (iterations <= 0) {throw new IterationException();}

                // System.out.print(row);
                SimulationGrid window = new SimulationGrid(row,col,iterations);
                window.setSize(row*50, col*50);
                window.setResizable(false);
                window.setLocationRelativeTo(null);
                window.setVisible(true);


            }
            //Catch invalid dimension sizes
            catch (DimensionException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }

            // Catch invalid number of iterations
            catch (IterationException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }

            // Catch general input that is not convertible to integer
            catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null, "Input must be a number greater than 0. Try again.");
            }

        }
    }

}
