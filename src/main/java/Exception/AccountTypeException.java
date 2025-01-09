package Exception;

/**
 * Custom exception class that represents an exception related to an invalid account type.
 * <p>
 * This exception is thrown when an invalid or unsupported account type is encountered
 * while performing operations related to an account.
 * </p>
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class AccountTypeException extends Exception {
    /**
     * Default constructor for AccountTypeException.
     * <p>
     * This constructor creates a new AccountTypeException instance without any detailed message.
     * </p>
     */
    public AccountTypeException() {
    }

    /**
     * Constructor for AccountTypeException with a detailed message.
     * <p>
     * This constructor creates a new AccountTypeException instance with a custom error message
     * that can provide more information about the cause of the exception.
     * </p>
     *
     * @param message the error message describing the cause of the exception
     */

    public AccountTypeException(String message) {
        // Call the superclass constructor with the provided message
        super(message);
    }
}
