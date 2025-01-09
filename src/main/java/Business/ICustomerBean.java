package Business;

import Entity.Customer;
import Exception.CustomerNotFoundExceptin;

import java.util.List;

/**
 * Interface ICustomerBean
 * <p>
 * This interface defines the core operations for managing {@link Customer} entities,
 * including creation, retrieval, updates, and deletion. It serves as a blueprint for any class
 * implementing customer-related business logic. Classes implementing this interface are
 * responsible for defining the actual behavior for managing customers in the business layer.
 * </p>
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public interface ICustomerBean {
    /**
     * Creates a new {@link Customer} in the database.
     *
     * @param customer The {@link Customer} object that needs to be created.
     * @throws IllegalArgumentException if the provided customer is invalid.
     */
    void createCustomer(Customer customer);

    /**
     * Finds and retrieves a customer by their unique identifier.
     *
     * @param id the unique identifier of the customer.
     * @return the {@link Customer} object if found.
     * @throws CustomerNotFoundExceptin if no customer is found with the given ID.
     */
    Customer findCustomer(String id) throws CustomerNotFoundExceptin;

    /**
     * Updates the details of an existing customer, specifically their address and phone number.
     *
     * @param id      the unique identifier of the customer to update.
     * @param address the new address to set.
     * @param phone   the new phone number to set.
     * @throws CustomerNotFoundExceptin if no customer is found with the given ID.
     */
    void updateCustomer(String id, String address, String phone) throws CustomerNotFoundExceptin;

    /**
     * Deletes a customer by their unique identifier.
     *
     * @param id the unique identifier of the customer to delete.
     * @throws CustomerNotFoundExceptin if no customer is found with the given ID.
     */
    void deleteCustomer(String id) throws CustomerNotFoundExceptin;

    /**
     * Finds and retrieves customers whose balance is greater than the specified amount.
     *
     * @param balance the balance amount to compare.
     * @return a list of {@link Customer} objects with a balance greater than the given amount.
     */
    List<Customer> findCustomersWithBalance(double balance);
}
