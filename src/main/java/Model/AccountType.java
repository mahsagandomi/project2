package Model;

import Exception.AccountTypeException;

/**
 * Enum representing different types of bank accounts.
 * <p>
 * This enum defines the types of accounts available, such as SAVING, CHECKING, CURRENT, and BUSINESS.
 * Each account type has a corresponding number (accountTypeNumber) associated with it.
 * </p>
 *
 * @author mahsa 1.0
 * @version 1.0
 * @since 1.0
 */
public enum AccountType {
    // Enum constants representing different account types, each associated with a unique number.
    SAVING(1), CHECKING(2), CURRENT(3), BUSINESS(4);
    // The account type number corresponding to each account type.
    private final int accountTypeNumber;

    /**
     * Constructor to initialize the account type with a specific number.
     *
     * @param accountTypeNumber the number representing the account type
     */
    AccountType(int accountTypeNumber) {
        this.accountTypeNumber = accountTypeNumber;
    }

    /**
     * Static method to convert a string value into an AccountType enum constant.
     * <p>
     * This method attempts to convert a string value to its corresponding AccountType.
     * If the provided value is not valid, an AccountTypeException is thrown.
     * </p>
     *
     * @param value the string representation of the account type (e.g., "SAVING")
     * @return the corresponding AccountType enum constant
     * @throws AccountTypeException if the provided string is not a valid AccountType
     */
    public static AccountType fromString(String value) throws AccountTypeException {
        try {
            // Try to convert the string value to the corresponding enum constant.
            return AccountType.valueOf(value);
        } catch (IllegalArgumentException e) {
            // If the string does not match any AccountType, throw an exception with a descriptive message.
            throw new AccountTypeException("Invalid account type: " + value);
        }
    }
}
