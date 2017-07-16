/**
 * DimensionException is a type of Exception that catches invalid dimension sizes.
 */

public class DimensionException extends RuntimeException {
    /**
     * Holds the exception message.
     */
    private String message;

    /**
     * Constructor for DimensionException
     */
    public DimensionException() {
        message = "Dimensions must be greater than 0. Try again.\n";
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
