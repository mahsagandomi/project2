package Business;

import Entity.Customer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import Exception.CustomerNotFoundExceptin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Stateless session bean implementation for managing Customer entities.
 * Provides operations to create, find, update, and delete customers,
 * as well as retrieve customers with a balance above a specified threshold.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

@Stateless(name = "customerBean")
public class CustomerBeanImpl implements ICustomerBean {
    // Logger for logging information, warnings, and errors.
    private static final Logger logger = LogManager.getLogger(CustomerBeanImpl.class);
    //EntityManager for database interactions.
    @PersistenceContext(unitName = "PersistenceUnit")
    EntityManager entityManager;
    /**
     * Creates a new customer in the database.
     * If the customer already exists, logs an error message.
     */
    Customer customer;

    @Override
    public void createCustomer(Customer customer) {
        try {
            // Check if the customer already exists
            findCustomer(customer.getCustomerId());
            logger.error("Customer is already");
        } catch (CustomerNotFoundExceptin e) {
            // Persist the new customer
            logger.info("Creating new customer with ID: {}", customer.getCustomerId());
            entityManager.persist(customer);
            logger.info("Creating new customer with ID: {}", customer.getCustomerId());
        }

    }

    /**
     * Finds a customer by their ID.
     *
     * @param id the ID of the customer to find
     * @return the Customer entity
     * @throws CustomerNotFoundExceptin if no customer is found with the specified ID
     */

    @Override
    public Customer findCustomer(String id) throws CustomerNotFoundExceptin {
        try {
            // Log the search for the customer
            logger.info("Searching for customer with ID: {}", id);
            return (Customer) entityManager.createNamedQuery("selectCustomerQuery").setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            // Log the warning if customer is not found and throw a custom exception
            logger.warn("Customer with ID: {} could not be found", id);
            throw new CustomerNotFoundExceptin("Customer could not found.");
        }
    }

    /**
     * Updates the address and phone number of an existing customer.
     *
     * @param id      the ID of the customer to update
     * @param address the new address for the customer
     * @param phone   the new phone number for the customer
     * @throws CustomerNotFoundExceptin if no customer is found with the specified ID
     */

    @Override
    public void updateCustomer(String id, String address, String phone) throws CustomerNotFoundExceptin {
        logger.info("Updating customer with ID: {}", id);
        // Find the customer by ID
        Customer customer = findCustomer(id);
        // Update customer details
        customer.setCustomerAddress(address);
        customer.setCustomerPhone(phone);
        // Merge the updated customer back into the database
        entityManager.merge(customer);
        logger.info("Customer with ID: {} updated successfully", id);
    }

    /**
     * Deletes a customer by their ID.
     *
     * @param id the ID of the customer to delete
     * @throws CustomerNotFoundExceptin if no customer is found with the specified ID
     */
    @Override
    public void deleteCustomer(String id) throws CustomerNotFoundExceptin {
        logger.info("Attempting to delete customer with ID: {}", id);
        findCustomer(id);
        // Execute delete query
        entityManager.createNamedQuery("deleteCustomerQuery").setParameter("id", id).executeUpdate();
        logger.info("Customer with ID: {} deleted successfully", id);
    }

    /**
     * Finds customers with a balance greater than the specified amount.
     *
     * @param balance the balance threshold
     * @return a list of customers with a balance greater than the specified amount
     */

    @Override
    public List<Customer> findCustomersWithBalance(double balance) {
        logger.info("Searching for customers with balance greater than: {}", balance);
        List<Customer> customers = entityManager.createNamedQuery("findCustomersWithBalance", Customer.class)
                .setParameter("balance", balance)
                .getResultList();
        logger.info("Found {} customers with balance greater than {}", customers.size(), balance);
        return customers;
    }
}
