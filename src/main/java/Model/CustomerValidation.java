package Model;

import java.time.LocalDate;

import Exception.*;

/**
 * The CustomerValidation class provides methods to validate customer data such as ID, phone number, and birthdate.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class CustomerValidation {

    /**
     * Validates the customer ID.
     * <p>
     * This method checks whether the customer ID matches the required pattern of exactly 10 digits.
     * </p>
     *
     * @param customerId the customer ID to be validated
     * @return true if the customer ID matches the pattern, false otherwise
     */
    public static boolean customerIdValidation(String customerId) {
        // Check if the customer ID matches the pattern for 10 digits.
        if (customerId.matches("//d{10}")) {
            // Valid ID.
            return true;
        }
        // Invalid ID.
        return false;
    }

    /**
     * Validates the customer phone number.
     * <p>
     * This method checks whether the phone number starts with '0' and contains exactly 11 digits.
     * </p>
     *
     * @param customerPhone the customer phone number to be validated
     * @return true if the phone number matches the pattern, false otherwise
     */

    public static boolean customerPhoneValidation(String customerPhone) {
        // Check if the phone number starts with '0' and has exactly 11 digits.
        if (customerPhone.matches("0//d{10}")) {
            // Valid phone number.
            return true;
        }
        // Invalid phone number.
        return false;
    }

    /**
     * Validates the customer's birthdate.
     * <p>
     * This method ensures that the customer's birth year is greater than 1900.
     * </p>
     *
     * @param customerBirthDate the customer's birthdate to be validated
     * @throws BirthdateException if the birth year is not greater than 1900
     */
    public static void customerBirthDateValidation(LocalDate customerBirthDate) throws BirthdateException {
        // Check if the birth year is greater than 1900.
        if (customerBirthDate.getYear() > 1900) {
            // Valid birthdate.
            return;
        }
        // Throw an exception if the birth year is invalid.
        throw new BirthdateException("customer's birthdate must be more than 1999");
    }

}
