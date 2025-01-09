import static org.junit.jupiter.api.Assertions.*;

import Entity.Account;
import Entity.Customer;
import Model.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import Exception.*;

/**
 * Unit tests for the {@link Customer} class.
 * This test class validates the constructor, setters, and methods of the {@link Customer} class.
 */
class CustomerTest {
    // A sample customer object used for testing.
    private Customer customer;
    /**
     * Sets up the test environment by initializing the customer object before each test.
     * This method is called before each test to ensure a fresh customer object is used.
     * @throws BirthdateException if the birthdate validation fails during customer creation.
     */
    @BeforeEach
    void setUp() throws BirthdateException {
        // Create and initialize a customer object for testing
        customer = new Customer("1234567890", "Ali", "Reza", "Tehran", "09123456789", LocalDate.of(2000, 1, 1));
    }
    /**
     * Tests the valid construction of a customer with a valid birthdate.
     * Ensures that the customer object is created correctly with expected values.
     * @throws BirthdateException if the birthdate is invalid.
     */
    @Test
    void testConstructorValidBirthdate() throws BirthdateException {
        // Assert that the customer is created and fields are correctly initialized
        assertNotNull(customer);
        assertEquals("1234567890", customer.getCustomerId());
        assertEquals("Ali", customer.getCustomerName());
        assertEquals("Reza", customer.getCustomerFamily());
        assertEquals(LocalDate.of(2000, 1, 1), customer.getCustomerBirthday());
    }
    /**
     * Tests the creation of a customer with an invalid birthdate.
     * Verifies that a {@link BirthdateException} is thrown if the birthdate is too early.
     */
    @Test
    void testConstructorInvalidBirthdate() {
        // Assert that creating a customer with an invalid birthdate throws a BirthdateException
        assertThrows(BirthdateException.class, () -> {
            new Customer("1234567890", "Ali", "Reza", "Tehran", "09123456789", LocalDate.of(1800, 1, 1));
        });
    }
    /**
     * Tests the valid setting of the customer's birthdate.
     * Ensures that the birthdate is correctly updated.
     * @throws BirthdateException if the birthdate is invalid.
     */
    @Test
    void testSetCustomerBirthdayValid() throws BirthdateException {
        // Change the customer's birthdate and verify the update
        customer.setCustomerBirthday(LocalDate.of(2002, 5, 10));
        assertEquals(LocalDate.of(2002, 5, 10), customer.getCustomerBirthday());
    }
    /**
     * Tests the setting of an invalid birthdate.
     * Ensures that a {@link BirthdateException} is thrown when trying to set a birthdate too early.
     */
    @Test
    void testSetCustomerBirthdayInvalid() {
        // Assert that setting an invalid birthdate throws a BirthdateException
        assertThrows(BirthdateException.class, () -> {
            customer.setCustomerBirthday(LocalDate.of(1800, 6, 20));
        });
    }
    /**
     * Tests adding an account to the customer.
     * Ensures that the account is successfully added and associated with the customer.
     * @throws AccountTypeException if the account type is invalid.
     * @throws BirthdateException if the birthdate validation fails during customer creation.
     */
    @Test
    void testAddAccount() throws BirthdateException, AccountTypeException {
        // Create a new account and add it to the customer
        Account account = new Account(1, 123456, 1000.0, AccountType.SAVING);
        customer.addAccount(account);

        assertNotNull(customer.getAccount());
        assertEquals(1, customer.getAccount().size());
        assertEquals(customer, account.getCustomer());
    }
    /**
     * Tests setting a valid phone number for the customer.
     * Ensures that the phone number is correctly updated.
     */
    @Test
    void testSetCustomerPhoneValid() {
        // Set a new valid phone number and verify it
        customer.setCustomerPhone("09123456789");
        assertEquals("09123456789", customer.getCustomerPhone());
    }
    /**
     * Tests setting a valid customer ID.
     * Ensures that the customer ID is correctly updated.
     * @throws BirthdateException if the birthdate validation fails during customer creation.
     */
    @Test
    void testSetCustomerIdValid() throws BirthdateException {
        // Set a new valid customer ID and verify it
        customer.setCustomerId("0987654321");
        assertEquals("0987654321", customer.getCustomerId());
    }


}
