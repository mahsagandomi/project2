import Business.CustomerBeanImpl;
import Entity.Customer;
import Exception.*;
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
 * Test class for the {@link CustomerBeanImpl} class.
 * It provides unit tests for methods like createCustomer, findCustomer, and updateCustomer.
 * The tests use Mockito to mock interactions with the database via the EntityManager.
 */
public class CustomerBeanImplTest {

    // Mocked EntityManager for simulating database interactions
    @Mock
    private EntityManager entityManager;

    // The class under test, which will have its methods tested
    @InjectMocks
    private CustomerBeanImpl customerBeanImpl;

    // Test customer data for use in the test methods
    private Customer customer;

    /**
     * Sets up the test data before each test method is executed.
     * Initializes a Customer object with dummy data for testing.
     *
     * @throws BirthdateException if there is an issue with the birthdate format (not used in this test setup).
     */
    @BeforeEach
    public void setUp() throws BirthdateException {
        MockitoAnnotations.openMocks(this); // Initialize mock annotations
        customer = new Customer();
        customer.setCustomerId("123");
        customer.setCustomerAddress("Test Address");
        customer.setCustomerPhone("123456789");
    }

    /**
     * Tests the creation of a new customer when the customer does not already exist.
     * Mocks the behavior of the EntityManager and verifies that the customer is persisted.
     *
     * @throws CustomerNotFoundExceptin if a customer is not found, but it won't be thrown in this test.
     */
    @Test
    public void testCreateCustomer() throws CustomerNotFoundExceptin {
        // Mocking Query behavior for findCustomer
        Query mockQuery = mock(Query.class);
        when(entityManager.createNamedQuery("selectCustomerQuery")).thenReturn(mockQuery);
        when(mockQuery.setParameter("id", customer.getCustomerId())).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenThrow(new NoResultException()); // Simulate customer not found

        // Act: Calling the createCustomer method
        customerBeanImpl.createCustomer(customer);

        // Assert: Verifying persist is called
        verify(entityManager, times(1)).persist(customer);
    }

    /**
     * Tests the scenario where an attempt to create a customer is made, but the customer already exists.
     * Mocks the behavior of the EntityManager and verifies that the customer is not persisted.
     */
    @Test
    public void testCreateCustomer_AlreadyExists() {
        // Given: A customer already exists
        when(entityManager.createNamedQuery("selectCustomerQuery"))
                .thenReturn(mock(Query.class));
        when(entityManager.createNamedQuery("selectCustomerQuery").setParameter("id", "123"))
                .thenReturn(mock(Query.class));
        when(entityManager.createNamedQuery("selectCustomerQuery").setParameter("id", "123").getSingleResult())
                .thenReturn(customer);  // Simulate customer already exists

        // When: Trying to create a new customer
        customerBeanImpl.createCustomer(customer);

        // Then: Verify that persist is not called
        verify(entityManager, times(0)).persist(customer);
    }

    /**
     * Tests the retrieval of a customer by ID.
     * Mocks the behavior of the EntityManager to simulate a successful customer retrieval.
     *
     * @throws CustomerNotFoundExceptin if the customer is not found (but it won't happen in this test).
     */
    @Test
    public void testFindCustomer() throws CustomerNotFoundExceptin {
        // Given: Mocking the retrieval of a customer from the database
        Query mockQuery = mock(Query.class); // Mock Query
        when(entityManager.createNamedQuery("selectCustomerQuery")).thenReturn(mockQuery);
        when(mockQuery.setParameter("id", "123")).thenReturn(mockQuery); // Mock setParameter
        when(mockQuery.getSingleResult()).thenReturn(customer); // Mock getSingleResult

        // When: Calling the findCustomer method
        Customer foundCustomer = customerBeanImpl.findCustomer("123");

        // Then: Verify the customer is found
        assertNotNull(foundCustomer);
        assertEquals("123", foundCustomer.getCustomerId());
        verify(entityManager, times(1)).createNamedQuery("selectCustomerQuery");
    }

    /**
     * Tests the scenario where a customer is not found in the database.
     * Mocks the behavior of the EntityManager to simulate a NoResultException being thrown.
     */
    @Test
    public void testFindCustomer_ThrowsException() {
        // Given: Mocking the case where customer is not found
        Query mockQuery = mock(Query.class); // Mock Query
        when(entityManager.createNamedQuery("selectCustomerQuery")).thenReturn(mockQuery);
        when(mockQuery.setParameter("id", "123")).thenReturn(mockQuery); // Mock setParameter
        when(mockQuery.getSingleResult()).thenThrow(new NoResultException()); // Simulate NoResultException

        // When & Then: Verify that CustomerNotFoundExceptin is thrown
        assertThrows(CustomerNotFoundExceptin.class, () -> customerBeanImpl.findCustomer("123"));
    }

    /**
     * Tests updating an existing customer's details.
     * Mocks the behavior of the EntityManager to simulate customer retrieval and update.
     * Verifies that the customer's data is updated in the database.
     *
     * @throws CustomerNotFoundExceptin if the customer is not found (but it won't happen in this test).
     */
    @Test
    public void testUpdateCustomer() throws CustomerNotFoundExceptin {
        // Given: Mocking the retrieval of the customer for update
        when(entityManager.createNamedQuery("selectCustomerQuery")).thenReturn(mock(Query.class));
        when(entityManager.createNamedQuery("selectCustomerQuery").setParameter("id", "123")).thenReturn(mock(Query.class));
        when(entityManager.createNamedQuery("selectCustomerQuery").setParameter("id", "123").getSingleResult()).thenReturn(customer);

        // When: Updating the customer's address and phone number
        customerBeanImpl.updateCustomer("123", "New Address", "987654321");

        // Then: Verify that the customer's data has been updated
        assertEquals("New Address", customer.getCustomerAddress());
        assertEquals("987654321", customer.getCustomerPhone());
        verify(entityManager, times(1)).merge(customer);
    }
}
