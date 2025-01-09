package Exception;

/**
 * Custom exception class that represents an exception when a customer is not found.
 * <p>
 * This exception is thrown when an operation involving customer data fails because the customer
 * cannot be found in the system (e.g., when searching for a customer by ID and the customer does not exist).
 * </p>
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class CustomerNotFoundExceptin extends Exception {
    /**
     * Default constructor for CustomerNotFoundException.
     * <p>
     * This constructor creates a new instance of CustomerNotFoundExceptin without any detailed message.
     * </p>
     */
    public CustomerNotFoundExceptin() {
    }

    /**
     * Constructor for CustomerNotFoundExceptin with a detailed message.
     * <p>
     * This constructor creates a new instance of CustomerNotFoundExceptin with a custom error message
     * that can provide more information about why the customer was not found.
     * </p>
     *
     * @param message the error message describing the cause of the exception (e.g., customer not found)
     */
    public CustomerNotFoundExceptin(String message) {
        // Call the superclass constructor with the provided message
        super(message);
    }
}
