package Web.Controller;

import Business.ICustomerBean;
import Entity.Customer;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Exception.CustomerNotFoundExceptin;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import Exception.BirthdateException;

/**
 * Servlet to handle customer-related CRUD operations:
 * - Create a customer (POST)
 * - Retrieve customer details (GET)
 * - Delete a customer (DELETE)
 * - Update customer details (PUT)
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */


@WebServlet(name = "CustomerServlet", urlPatterns = "/Customer")
public class CustomerServlet extends HttpServlet {
    // Logger to track the operations in this servlet
    private static final Logger logger = LogManager.getLogger(CustomerServlet.class);

    // Injecting the ICustomerBean to interact with the business logic for customer management
    @EJB
    ICustomerBean iCustomerBean;

    /**
     * Handles POST requests for creating a new customer.
     *
     * @param req  The HttpServletRequest object containing the customer data.
     * @param resp The HttpServletResponse object used to send responses.
     * @throws IOException If an error occurs during I/O operations.
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Received request to create a new customer.");
        // Extracting customer details from request parameters
        String customerId = req.getParameter("customerID");
        String name = req.getParameter("customerName");
        String family = req.getParameter("customerFamily");
        String address = req.getParameter("customerAddress");
        String phone = req.getParameter("customerPhone");
        String birthday = req.getParameter("customerBirthday");
        // Parsing the birthdate to LocalDate
        LocalDate birthday2 = LocalDate.parse(birthday);
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        // HTML structure for the response
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Create Customer</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");
        // Checking if all required parameters are provided
        if (name != null && family != null && address != null && phone != null && birthday != null) {
            try {
                // Creating a new Customer object
                Customer customer = new Customer(customerId, name, family, address, phone, birthday2);
                logger.info("Customer created successfully: " + customer);
                // Calling the business logic to create the customer
                iCustomerBean.createCustomer(customer);

                out.println("<h1 style='color: green;'>Customer Created Successfully</h1>");
            } catch (BirthdateException e) {
                // Handling invalid birthdate error
                logger.error("Error creating customer: Invalid birthdate", e);
                out.println("<p style='color: red;'>customer's birthdate must be more than 1999</p>");
            }
            // Displaying customer details in the response
            out.println("<p>Name: " + name + "</p>");
            out.println("<p>Family: " + family + "</p>");
            out.println("<p>Address: " + address + "</p>");
            out.println("<p>Phone: " + phone + "</p>");
            out.println("<p>Birthday: " + birthday + "</p>");
        } else {
            // If any field is missing, show an error
            logger.warn("All fields are required for customer creation.");
            out.println("<p style='color: red;'>All fields are required.</p>");
        }

        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Handles GET requests to fetch customer details by customer ID.
     *
     * @param req  The HttpServletRequest object containing the request parameters.
     * @param resp The HttpServletResponse object used to send responses.
     * @throws IOException If an error occurs during I/O operations.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Received GET request to fetch customer details.");
        // Retrieving the customer ID from the request parameters
        String id = req.getParameter("id");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        // HTML structure for the response
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Customer info</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");
        try {
            // Fetching the customer details from the database or business logic
            logger.info("Fetching customer with ID: " + id);
            out.println("<h1>Customer Details</h1>");
            out.println("<p>The result is: " + iCustomerBean.findCustomer(id).toString() + "</p>");
        } catch (NumberFormatException e) {
            // Handling invalid customer ID format
            logger.error("Invalid customer ID: " + id, e);
            out.println("<p style='color: red;'>Invalid customer ID. Please provide a valid number.</p>");
        } catch (CustomerNotFoundExceptin e) {
            // Handling customer not found exception
            logger.error("Customer not found: " + id);
            out.println("<p style='color: red;'>Customer not found. Please try another ID.</p>");
        } catch (Exception e) {
            // Handling any other unexpected errors
            logger.error("An unexpected error occurred", e);
            out.println("<p style='color: red;'>An unexpected error occurred. Please try again later.</p>");
        }

        out.println("</body>");
        out.println("</html>");


    }

    /**
     * Handles DELETE requests to delete a customer by ID.
     *
     * @param req  The HttpServletRequest object containing the request parameters.
     * @param resp The HttpServletResponse object used to send responses.
     * @throws IOException If an error occurs during I/O operations.
     */

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Received DELETE request to delete a customer.");
        // Retrieving the customer ID from the request parameters
        String id = req.getParameter("id");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        // HTML structure for the response
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Delete customer</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");
        try {
            // Attempting to delete the customer from the database or business logic
            logger.info("Attempting to delete customer with ID: " + id);
            iCustomerBean.deleteCustomer(id);
            out.println("<p style='color: green;'>The customer has been deleted.</p>");
        } catch (NumberFormatException e) {
            // Handling invalid customer ID format
            logger.error("Invalid customer ID: " + id, e);
            out.println("<p style='color: red;'>Invalid customer ID. Please provide a valid number.</p>");
        } catch (CustomerNotFoundExceptin e) {
            // Handling customer not found exception
            logger.error("Invalid customer ID: " + id, e);
            out.println("<p style='color: red;'>Customer not found. Please try another ID.</p>");
        } catch (Exception e) {
            // Handling any other unexpected errors
            logger.error("An unexpected error occurred", e);
            out.println("<p style='color: red;'>An unexpected error occurred. Please try again later.</p>");
        }

        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Handles PUT requests to update customer details (address and phone).
     *
     * @param req  The HttpServletRequest object containing the request parameters.
     * @param resp The HttpServletResponse object used to send responses.
     * @throws IOException If an error occurs during I/O operations.
     */

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Received PUT request to update a customer.");
        // Retrieving the customer ID, address, and phone from the request parameters
        String id = req.getParameter("customerID");
        String address = req.getParameter("customerAddress");
        String phone = req.getParameter("customerPhone");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        // HTML structure for the response
        out.println("<html>");
        out.println("<head>");
        out.println("<title>,Update customer</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");
        // Validating required fields for update
        if (id == null || id.isEmpty() || address == null || address.isEmpty() || phone == null || phone.isEmpty()) {
            logger.warn("Invalid input for update. Missing required fields.");
            // Respond with an error message if any of the parameters are invalid
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"All fields (id, address, phone) must be provided.\"}");
        } else {
            try {
                // Attempting to update the customer in the database or business logic
                logger.info("Updating customer with ID: " + id);
                iCustomerBean.updateCustomer(id, address, phone);
                out.println("<p style='color: green;'>The customer has been updated.</p>");
            } catch (NumberFormatException e) {
                // Handling invalid customer ID format
                logger.error("Invalid customer ID: " + id, e);
                out.println("<p style='color: red;'>Invalid customer ID. Please provide a valid number.</p>");

            } catch (CustomerNotFoundExceptin e) {
                // Handling customer not found exception
                logger.error("Customer not found for deletion {}", id);
                out.println("<p style='color: red;'>Customer not found. Please try another ID.</p>");

            } catch (Exception e) {
                // Handling any other unexpected errors
                logger.error("An unexpected error occurred", e);
                out.println("<p style='color: red;'>An unexpected error occurred. Please try again later.</p>");
            }

            out.println("</body>");
            out.println("</html>");

        }
    }
}
