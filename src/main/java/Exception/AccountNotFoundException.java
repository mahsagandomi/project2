package Exception;

/**
 * Custom exception class that represents an exception when an account is not found.
 * <p>
 * This exception is thrown when an account is not found in the system, typically when trying to
 * retrieve or perform operations on an account that does not exist.
 * </p>
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public class AccountNotFoundException extends Exception {
    /**
     * Default constructor for AccountNotFoundException.
     * <p>
     * This constructor creates a new AccountNotFoundException instance without any detailed message.
     * </p>
     */
    public AccountNotFoundException() {
    }

    /**
     * Constructor for AccountNotFoundException with a detailed message.
     * <p>
     * This constructor creates a new AccountNotFoundException instance with a custom error message
     * that can provide more information about the exception.
     * </p>
     *
     * @param message the error message describing the cause of the exception
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
}
