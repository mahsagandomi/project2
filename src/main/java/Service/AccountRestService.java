package Service;

import Business.IAccountBean;
import Entity.Account;
import Exception.AccountNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * REST service for managing account operations.
 * <p>
 * This service provides endpoints for creating, finding, updating, and deleting accounts.
 * It uses an EJB to interact with the account business logic and returns responses in plain text format.
 * </p>
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

@Path("/AccountRestService")
public class AccountRestService {
    // Logger for tracking service events.
    private static final Logger logger = LogManager.getLogger(AccountRestService.class);
    // Injected business bean for account operations.
    @EJB
    IAccountBean iAccountBean;

    /**
     * Creates a new account.
     *
     * @param account the account entity to be created
     * @return a plain text message indicating the success or failure of the operation
     */

    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public String createAccount(Account account) {
        // Log the received request.
        logger.info("Received request to create account: {}", account);
        try {
            // Delegate to business layer.
            iAccountBean.createAccount(account);
            // Log the success.
            logger.info("Account created successfully: {}", account);
            return "Account created successfully: " + account.toString();
        } catch (Exception e) {
            // Log the exception.
            logger.error("Error creating account", e);
            return "Error creating account: " + e.getMessage();
        }
    }

    /**
     * Retrieves an account by ID.
     *
     * @param id The unique ID of the account.
     * @return A success message with account details or an error message if not found.
     */

    @GET
    @Path("/{id}")
    @Produces("text/plain")
    public String findAccount(@PathParam("id") int id) {
        // Log the request.
        logger.info("Received request to find account with ID: {}", id);
        try {
            // Retrieve account by ID.
            Account account = iAccountBean.findAccount(id);
            // Log the success.
            logger.info("Account found: {}", account);
            return "Account found: " + account.toString();
        } catch (AccountNotFoundException e) {
            // Log the failure.
            logger.warn("Account not found with ID: {}", id, e);
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Updates an account's details.
     *
     * @param id          The unique ID of the account to update.
     * @param accountJson The updated account data in JSON format.
     * @return A success or error message as plain text.
     */

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("text/plain")
    public String updateAccount(@PathParam("id") int id, String accountJson) {
        // Log the request.
        logger.info("Received request to update account with ID: {}, Data: {}", id, accountJson);
        try {
            // Create an ObjectMapper instance.
            ObjectMapper objectMapper = new ObjectMapper();
            // Convert JSON to Account object.
            Account updatedAccount = objectMapper.readValue(accountJson, Account.class);

            // Call business logic to update the account.
            iAccountBean.updateAccount(id, updatedAccount.getAccountNumber());
            // Log the success.
            logger.info("Account with ID {} updated successfully with account number: {}", id, updatedAccount.getAccountNumber());
            return "Account with ID " + id + " updated successfully with account number: " + updatedAccount.getAccountNumber();
        } catch (AccountNotFoundException e) {
            // Log account not found.
            logger.warn("Account not found for update with ID: {}", id, e);
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            // Log generic error.
            logger.error("Error updating account with ID: {}", id, e);
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Deletes an account by ID.
     *
     * @param id The unique ID of the account to delete.
     * @return A success or error message as plain text.
     */

    @DELETE
    @Path("/{id}")
    @Produces("text/plain") // پاسخ به صورت رشته
    public String deleteAccount(@PathParam("id") int id) {
        // Log the request.
        logger.info("Received request to delete account with ID: {}", id);
        try {
            // Call business logic to delete the account.
            iAccountBean.deleteAccount(id);
            // Log the success.
            logger.info("Account deleted successfully with ID: {}", id);
            return "Account deleted successfully: " + id;
        } catch (AccountNotFoundException e) {
            logger.warn("Account not found for deletion with ID: {}", id, e);
            // Log account not found.
            return "Error: " + e.getMessage();
        }
    }
}
