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
import javax.swing.JComboBox;

/**
 * The LifeFrame class provides the GUI for getting the number of rows, columns, and iterations from the user.
 */
public class LifeFrame extends JFrame{
    /**
     * JTextField for inputting the number of rows.
     */
    private final JTextField rowField;

    /**
     * JTextField for inputting the number of columns.
     */
    private final JTextField colField;

    /**
     * JTextField for inputting the number of iterations.
     */
    private final JTextField iterationField;

    /**
     * JButton for submitting all inputs. Take note that pressing "Enter" also works.
     */
    private final JButton submitButton;

    /**
     * JPanel for holding the rowField, colField, and iterationField JTextFields, and also the outputList JComboBox.
     */
    private final JPanel textFields;

    /**
     * JComboBox for the different output styles:
     * 0. Display in new window and terminal
     * 1. Display in new window only
     * 2. Display in terminal only
     */
    private final JComboBox<String> outputList;

    private final String[] outputOptions = {"Display in new window and terminal",
            "Display in new window only", "Display in terminal only"};

    /**
     * Constructor for LifeFrame.
     */
    public LifeFrame(){
        super("Life Simulation");

        //USER INPUT TEXT FIELDS
        textFields = new JPanel();
        textFields.setLayout(new GridLayout(8, 1));

        rowField = new JTextField();
        textFields.add(new JLabel("Enter number of rows:"));
        textFields.add(rowField);

        colField = new JTextField();
        textFields.add(new JLabel("Enter number of columns:"));
        textFields.add(colField);

        iterationField = new JTextField();
        textFields.add(new JLabel("Enter number of iterations:"));
        textFields.add(iterationField);

        //JLIST FOR STYLE OF OUTPUTTING THE SIMULATION
        outputList = new JComboBox<>(outputOptions);
        outputList.setSelectedIndex(0);
        textFields.add(outputList);

        JLabel checkBoxWarning = new JLabel("<html><p style=\"font-size:8px\">" +
                "**Large number of rows, columns, or iterations should only be printed to terminal." +
                "</p></html>");
        textFields.add(checkBoxWarning);

        //LABEL THAT GOES AT TOP OF JFRAME
        JLabel cautionLabel = new JLabel();
        cautionLabel.setText("<html> <center><p style=\"font-size:16px\"><b>WELCOME TO LIFE SIMULATION!!! </b></p>" +
                "* is a plant, & is a herbivore, @ is a carnivore, and # is a rock. <br>"+
                "<p style=\"font-size:8px\"><i>CAUTION: Choosing dimensions too big may cause the grid to be cut off.</i></p><center></html>");
        //cautionLabel.setFont(new Font("Comic Sans MS", Font.PLAIN,12));

        //ADDING EVERYTHING TO THE JFRAME
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

    /**
     * The ButtonHandler private class is an inner class under LifeFrame. It is a handler for taking user inputs.
     */
    private class ButtonHandler implements ActionListener{
        /**
         * Overriden method for the handler. Takes user inputs and makes a SimulationFrame frame or prints the output to terminal
         * according to user choice.
         * @param event
         * @throws DimensionException Thrown when row or column input is <= 0
         * @throws IterationException Thrown when iteration input is <= 0
         * @throws NumberFormatException Thrown when the inputs are not integers
         */
        @Override
        public void actionPerformed(ActionEvent event) throws DimensionException, IterationException, NumberFormatException{
            try{
                int row = Integer.parseInt(rowField.getText());
                if (row <= 0) { throw new DimensionException();}

                int col = Integer.parseInt(colField.getText());
                if (col <= 0) {throw new DimensionException();}

                int iterations = Integer.parseInt(iterationField.getText());
                if (iterations <= 0) {throw new IterationException();}

                int outputSel = outputList.getSelectedIndex();

                //System.out.print(outputSel);
                SimulationFrame window = new SimulationFrame(row,col,iterations, outputSel);
                if (outputSel !=2) { //if only terminal output
                    window.setSize(col * 60, row * 60);
                } else {
                    window.setSize(300,100);
                }
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
