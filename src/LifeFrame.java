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
    private final JPanel textFields;

    public LifeFrame(){
        super("Life Simulation");

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

        JLabel cautionLabel = new JLabel();
        cautionLabel.setText("<html> <center><p style=\"font-size:16px\"><b>WELCOME TO LIFE SIMULATION!!! </b></p>" +
                "* is a plant, & is a herbivore, @ is a carnivore, and # is a rock. <br>"+
                "<p style=\"font-size:8px\"><i>CAUTION: Choosing dimensions too big may cause the grid to be cut off</i></p><center></html>");
        //cautionLabel.setFont(new Font("Comic Sans MS", Font.PLAIN,12));

        add(textFields,BorderLayout.CENTER);
        submitButton = new JButton("I'm ready to ROLL");
        add(submitButton,BorderLayout.SOUTH);
        add(cautionLabel, BorderLayout.NORTH);

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
                window.setSize(col*60, row*60);
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
                JOptionPane.showMessageDialog(null, "Input must be an integer greater than 0. Try again.");
            }

        }
    }

}
