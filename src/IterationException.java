/**
 * IterationException is a type of Exception that catches invalid number of iterations.
 */

public class IterationException extends RuntimeException{
    /**
     * Holds the exception message.
     */
    private String message;

    /**
     * Constructor for IterationException
     */
    public IterationException() {
        message = "Number of iterations must be greater than 0. Try again.\n";
    }

    /**
     * Gets exception message.
     * @return Exception message.
     */
    @Override
    public String getMessage() {
        return message;
    }
}
