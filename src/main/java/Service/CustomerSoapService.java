package Service;

import Business.ICustomerBean;
import Entity.Customer;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import Exception.CustomerNotFoundExceptin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * SOAP Web Service for customer operations.
 * Provides methods for creating, finding, updating, and deleting customers.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

@WebService(name = "CustomerSoapService", targetNamespace = "http://CustomerService/")
public class CustomerSoapService {
    // Logger for monitoring service events.
    private static final Logger logger = LogManager.getLogger(CustomerSoapService.class);
    // Business logic for customer-related operations
    @EJB
    ICustomerBean iCustomerBean;

    /**
     * Creates a new customer.
     * If the customer already exists, returns a message indicating so.
     *
     * @param customer The customer to be created.
     * @return Success or error message.
     */

    @WebMethod
    public String createCustomer(Customer customer) {
        logger.info("Received request to create customer: {}", customer);
        try {
            // Check if customer already exists.
            iCustomerBean.findCustomer(customer.getCustomerId());
            logger.warn("Customer already exists with ID: {}", customer.getCustomerId());
            return "Customer already exists with ID: " + customer.getCustomerId();
        } catch (CustomerNotFoundExceptin e) {
            // Create the customer if not found.
            iCustomerBean.createCustomer(customer);
            logger.info("Customer created successfully: {}", customer);
            return "Customer created successfully.";
        } catch (Exception e) {
            logger.error("Error while creating customer with ID: {}", customer.getCustomerId(), e);
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Finds a customer by ID.
     *
     * @param id The ID of the customer to find.
     * @return The customer entity if found.
     * @throws CustomerNotFoundExceptin If the customer is not found.
     */

    @WebMethod
    public Customer findCustomer(String id) throws CustomerNotFoundExceptin {
        logger.info("Received request to find customer with ID: {}", id);
        // Retrieve the customer details.
        return iCustomerBean.findCustomer(id);
    }

    /**
     * Updates a customer's details.
     *
     * @param id      The ID of the customer to update.
     * @param address The new address of the customer.
     * @param phone   The new phone number of the customer.
     * @return Success or error message.
     */

    @WebMethod
    public String updateCustomer(String id, String address, String phone) {
        logger.info("Received request to update customer with ID: {}", id);
        try {
            iCustomerBean.updateCustomer(id, address, phone);
            // Update the customer's information.
            logger.info("Customer updated successfully: ID={}, Address={}, Phone={}", id, address, phone);
            return "Customer updated successfully.";
        } catch (CustomerNotFoundExceptin e) {
            logger.warn("Customer not found for update with ID: {}", id, e);
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            logger.error("Error while updating customer with ID: {}", id, e);
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id The ID of the customer to delete.
     * @return Success or error message.
     */

    @WebMethod
    public String deleteCustomer(String id) {
        try {
            // Delete the customer record.
            iCustomerBean.deleteCustomer(id);
            return "Customer deleted successfully.";
        } catch (CustomerNotFoundExceptin e) {
            return "Error: " + e.getMessage();
        }
    }
}
