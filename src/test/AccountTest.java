import static org.junit.jupiter.api.Assertions.*;

import Entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Exception.AccountTypeException;
import Model.AccountType;
/**
 * Test class for {@link Account}.
 * This class contains unit tests for the constructor and methods of the Account class.
 * The tests check the proper handling of account creation, account type setting, and validation.
 */

class AccountTest {
    //Account object to be used in the test cases.
    private Account account;
    /**
     * Sets up the test environment by initializing a valid Account object before each test.
     * @throws AccountTypeException if the account type is invalid.
     */
    @BeforeEach
    void setUp() throws AccountTypeException {
        // Initialize the account with a valid account type (SAVING)
        account = new Account(1, 123456, 500.0, AccountType.SAVING);
    }
    /**
     * Test case to verify that the constructor initializes the account correctly with valid data.
     * It checks the account ID, account number, balance, and account type.
     * @throws AccountTypeException if the account type is invalid.
     */

    @Test
    void testConstructorValidAccountType() throws AccountTypeException {
        // Verify that the account object is properly initialized
        assertNotNull(account);
        assertEquals(1, account.getAccountId());
        assertEquals(123456, account.getAccountNumber());
        assertEquals(500.0, account.getAccountBalance());
        assertEquals(AccountType.SAVING, account.getAccountType());
    }
    /**
     * Test case to verify that the constructor throws an AccountTypeException
     * when an invalid account type is provided.
     */

    @Test
    void testConstructorInvalidAccountType() {
        // Verify that an AccountTypeException is thrown when an invalid account type is provided
        assertThrows(AccountTypeException.class, () -> {
            new Account(1, 123456, 500.0, AccountType.fromString("INVALID"));
        });
    }
    /**
     * Test case to verify that the account type can be set correctly with a valid type.
     * It checks whether the account type is successfully updated.
     * @throws AccountTypeException if the account type is invalid.
     */

    @Test
    void testSetAccountTypeValid() throws AccountTypeException {
        // Set a valid account type (CHECKING) and verify the change
        account.setAccountType(AccountType.CHECKING);
        // Check if the account type is updated
        assertEquals(AccountType.CHECKING, account.getAccountType());
    }
    /**
     * Test case to verify that the setAccountType method throws an AccountTypeException
     * when an invalid account type is set.
     */
    @Test
    void testSetAccountTypeInvalid() {
        // Verify that an AccountTypeException is thrown when an invalid account type is set
        assertThrows(AccountTypeException.class, () -> {
            account.setAccountType(AccountType.fromString("INVALID"));
        });
    }
}
