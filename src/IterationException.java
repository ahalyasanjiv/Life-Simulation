/**
 * IterationException is a type of Exception that catches invalid number of iterations.
 */

public class IterationException extends Exception{
    private String message;

    public IterationException() {
        message = "Number of iterations must be greater than 0. Try again.\n";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
