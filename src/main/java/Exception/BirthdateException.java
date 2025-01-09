package Exception;

/**
 * Custom exception class that represents an exception related to an invalid birthdate.
 * <p>
 * This exception is thrown when an invalid or incorrect birthdate is encountered
 * while performing operations related to customer information.
 * </p>
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class BirthdateException extends Exception {

    /**
     * Default constructor for BirthdateException.
     * <p>
     * This constructor creates a new BirthdateException instance without any detailed message.
     * </p>
     */
    public BirthdateException() {
    }

    /**
     * Constructor for BirthdateException with a detailed message.
     * <p>
     * This constructor creates a new BirthdateException instance with a custom error message
     * that can provide more information about the cause of the exception.
     * </p>
     *
     * @param message the error message describing the cause of the exception
     */
    public BirthdateException(String message) {
        // Call the superclass constructor with the provided message
        super(message);
    }
}
