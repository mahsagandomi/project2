package Model;

import Exception.*;

/**
 * The AccountValidation class is used to validate banking account information.
 * This class includes methods for validating various aspects such as account balance and account type.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public class AccountValidation {
    /**
     * Validates the account balance.
     * <p>
     * This method checks whether the account balance is valid (greater than or equal to zero).
     * </p>
     *
     * @param accountBalance the account balance to be checked
     * @return true if the account balance is greater than or equal to zero, otherwise false
     */
    public static boolean accountBalanceValidation(int accountBalance) {
        // Check if the account balance is greater than or equal to zero.
        if (accountBalance >= 0) {
            // Returns true if the balance is valid.
            return true;
        }
        // Returns false if the balance is invalid.
        return false;
    }

    /**
     * Validates the account type.
     * <p>
     * This method checks whether the provided account type is one of the valid account types.
     * </p>
     *
     * @param accountType the account type to be checked
     * @throws AccountTypeException if the account type is invalid
     */
    public static void accountTypeValidation(AccountType accountType) throws AccountTypeException {
        // Check if the account type is one of the valid types (SAVING, CHECKING, CURRENT, or BUSINESS).
        if (accountType.equals(AccountType.SAVING) || accountType.equals(AccountType.CHECKING) || accountType.equals(AccountType.CURRENT) || accountType.equals(AccountType.BUSINESS)) {
            // Do nothing if the account type is valid.

        } else {
            // Throw an exception if the account type is invalid.
            throw new AccountTypeException("Invalid account type");
        }

    }
}
