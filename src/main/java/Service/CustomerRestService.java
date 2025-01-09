package Service;

import Business.ICustomerBean;
import Entity.Customer;
import Exception.CustomerNotFoundExceptin;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * REST Web Service for managing customer operations.
 * Provides endpoints for creating, retrieving, updating, and deleting customers.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

@Path("/CustomerRestService")
public class CustomerRestService {
    // Logger for tracking service events.
    private static final Logger logger = LogManager.getLogger(CustomerRestService.class);

    @EJB
    ICustomerBean iCustomerBean;

    // متد برای ایجاد مشتری
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public String createCustomer(Customer customer) {
        logger.info("Received request to create customer: {}", customer);
        try {
            iCustomerBean.createCustomer(customer);
            logger.info("Customer created successfully: {}", customer);
            return "Customer created successfully: " + customer.toString();
        } catch (Exception e) {
            logger.error("Error while creating customer", e);
            return "Error: " + e.getMessage();
        }
    }

    // متد برای یافتن مشتری
    @GET
    @Path("/{id}")
    @Produces("text/plain")
    public String findCustomer(@PathParam("id") String id) {
        logger.info("Received request to find customer with ID: {}", id);
        try {
            Customer customer = iCustomerBean.findCustomer(id);
            logger.info("Customer found: {}", customer);
            return "Customer found: " + customer.toString();
        } catch (CustomerNotFoundExceptin e) {
            logger.warn("Customer not found with ID: {}", id, e);
            return "Error: " + e.getMessage();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("text/plain")
    public String updateCustomer(@PathParam("id") String id, String customerJson) {
        logger.info("Received request to update customer with ID: {}", id);
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            CustomerUpdateDTO customerUpdate = objectMapper.readValue(customerJson, CustomerUpdateDTO.class);
            logger.info("Updating customer fields - Address: {}, Phone: {}", customerUpdate.getCustomerAddress(), customerUpdate.getCustomerPhone());
            iCustomerBean.updateCustomer(id, customerUpdate.getCustomerAddress(), customerUpdate.getCustomerPhone());
            logger.info("Customer updated successfully: {}", id);
            return "Customer updated successfully: " + id;
        } catch (CustomerNotFoundExceptin e) {
            logger.warn("Customer not found for update with ID: {}", id, e);
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            logger.error("Error updating customer with ID: {}", id, e);
            return "Error: " + e.getMessage();
        }
    }


    @DELETE
    @Path("/{id}")
    @Produces("text/plain")
    public String deleteCustomer(@PathParam("id") String id) {
        logger.info("Received request to delete customer with ID: {}", id);
        try {
            iCustomerBean.deleteCustomer(id);
            logger.info("Customer deleted successfully with ID: {}", id);
            return "Customer deleted successfully: " + id;
        } catch (CustomerNotFoundExceptin e) {
            logger.warn("Customer not found for deletion with ID: {}", id, e);
            return "Error: " + e.getMessage();
        }
    }
}

class CustomerUpdateDTO {
    private String customerAddress;
    private String customerPhone;

    // Getter and Setter methods
    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}

