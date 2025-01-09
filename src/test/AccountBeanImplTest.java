import Business.AccountBeanImpl;
import Entity.Account;
import Exception.AccountNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link AccountBeanImpl} class.
 * It provides unit tests for methods like createAccount, findAccount, and updateAccount.
 * The tests use Mockito to mock interactions with the database via the EntityManager.
 */
public class AccountBeanImplTest {

    // Mocked EntityManager for simulating database interactions
    @Mock
    private EntityManager entityManager;

    // The class under test, which will have its methods tested
    @InjectMocks
    private AccountBeanImpl accountBeanImpl;

    // Test account data for use in the test methods
    private Account account;

    /**
     * Sets up the test data before each test method is executed.
     * Initializes an Account object with dummy data for testing.
     */
    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Setup a test account object
        account = new Account();
        account.setAccountId(1);
        account.setAccountNumber(12345);
    }

    /**
     * Tests the creation of a new account.
     * Mocks the behavior of the EntityManager and verifies that the account is persisted.
     */
    @Test
    public void testCreateAccount() {
        // Given: Mocking the persist method of the EntityManager
        doNothing().when(entityManager).persist(account);

        // When: Calling the createAccount method
        accountBeanImpl.createAccount(account);

        // Then: Verify that the persist method is called once
        verify(entityManager, times(1)).persist(account);
    }

    /**
     * Tests the retrieval of an account by ID.
     * Mocks the behavior of the EntityManager to simulate a successful account retrieval.
     *
     * @throws AccountNotFoundException if the account is not found (not expected in this test).
     */
    @Test
    public void testFindAccount() throws AccountNotFoundException {
        // Given: Mocking the retrieval of an account from the database
        Query mockQuery = mock(Query.class);  // Mock a Query
        when(entityManager.createNamedQuery("selectAccountQuery")).thenReturn(mockQuery);  // Mock createNamedQuery
        when(mockQuery.setParameter("id", 1)).thenReturn(mockQuery);  // Mock setParameter
        when(mockQuery.getSingleResult()).thenReturn(account);  // Mock getSingleResult to return the test account

        // When: Calling the findAccount method
        Account foundAccount = accountBeanImpl.findAccount(1);

        // Then: Verify that the account is found and the expected values are returned
        assertNotNull(foundAccount);
        assertEquals(1, foundAccount.getAccountId());
        verify(entityManager, times(1)).createNamedQuery("selectAccountQuery");  // Verify that createNamedQuery is called once
        verify(mockQuery, times(1)).setParameter("id", 1);  // Verify that setParameter is called once with the correct ID
        verify(mockQuery, times(1)).getSingleResult();  // Verify that getSingleResult is called once
    }

    /**
     * Tests the scenario where an account is not found in the database.
     * Mocks the behavior of the EntityManager to simulate a NoResultException being thrown.
     */
    @Test
    public void testFindAccount_ThrowsException() {
        // Given: Simulating the case where the account is not found
        when(entityManager.createNamedQuery("selectAccountQuery")).thenReturn(mock(Query.class));
        when(entityManager.createNamedQuery("selectAccountQuery").setParameter("id", 1)).thenReturn(mock(Query.class));
        when(entityManager.createNamedQuery("selectAccountQuery").setParameter("id", 1).getSingleResult()).thenThrow(new NoResultException());

        // When & Then: Verifying that an AccountNotFoundException is thrown when the account is not found
        assertThrows(AccountNotFoundException.class, () -> accountBeanImpl.findAccount(1));
    }

    /**
     * Tests updating an existing account's details.
     * Mocks the behavior of the EntityManager to simulate account retrieval and update.
     * Verifies that the account's account number is updated and merged in the database.
     *
     * @throws AccountNotFoundException if the account is not found (not expected in this test).
     */
    @Test
    public void testUpdateAccount() throws AccountNotFoundException {
        // Given: Mocking the retrieval of the account for updating
        when(entityManager.createNamedQuery("selectAccountQuery")).thenReturn(mock(Query.class));
        when(entityManager.createNamedQuery("selectAccountQuery").setParameter("id", 1)).thenReturn(mock(Query.class));
        when(entityManager.createNamedQuery("selectAccountQuery").setParameter("id", 1).getSingleResult()).thenReturn(account);

        // When: Calling the updateAccount method to update the account number
        accountBeanImpl.updateAccount(1, 54321);

        // Then: Verify that the account number is updated and the merge method is called
        assertEquals(54321, account.getAccountNumber());
        verify(entityManager, times(1)).merge(account);  // Verify that merge is called once to update the account
    }
}
