/**
 * DimensionException is a type of Exception that catches invalid dimension sizes.
 */

public class DimensionException extends Exception {
    private String message;

    public DimensionException() {
        message = "Dimensions must be greater than 0. Try again.\n";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
