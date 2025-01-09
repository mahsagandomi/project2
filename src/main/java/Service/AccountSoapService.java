package Service;

import Business.IAccountBean;
import Entity.Account;
import jakarta.ejb.*;
import jakarta.jws.*;
import jakarta.jws.WebService;
import Exception.AccountNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * SOAP Web Service for Account operations.
 * Provides methods for creating, finding, updating, and deleting accounts.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

@WebService(name = "AccountSoapService", targetNamespace = "http://AccountService/")
public class AccountSoapService {
    // Logger for tracking service events.
    private static final Logger logger = LogManager.getLogger(AccountRestService.class);
    // Injected EJB to handle account business logic.
    @EJB
    IAccountBean iAccountBean;

    /**
     * Creates a new account.
     *
     * @param account The account entity to be created.
     * @return Success or error message.
     */

    @WebMethod
    public String createAccount(Account account) {
        logger.info("Received request to create account: {}", account);
        try {
            // Delegate to business layer.
            iAccountBean.createAccount(account);
            logger.info("Account created successfully: {}", account);
            return "Account created successfully.";
        } catch (Exception e) {
            logger.error("Error while creating account", e);
            return "Error while creating account: " + e.getMessage();
        }
    }

    /**
     * Finds an account by its ID.
     *
     * @param id The ID of the account to find.
     * @return The account entity.
     * @throws AccountNotFoundException If the account does not exist.
     */

    @WebMethod
    public Account findAccount(int id) throws AccountNotFoundException {
        logger.info("Received request to find account with ID: {}", id);
        // Retrieve account from business logic.
        return iAccountBean.findAccount(id);
    }

    /**
     * Updates the account number of an existing account.
     *
     * @param id            The ID of the account to update.
     * @param accountNumber The new account number to set.
     * @return Success or error message.
     */

    @WebMethod
    public String updateAccount(int id, int accountNumber) {
        logger.info("Received request to update account with ID: {}, new account number: {}", id, accountNumber);
        try {
            // Update account via business logic.
            iAccountBean.updateAccount(id, accountNumber);
            logger.info("Account updated successfully: ID={}, new account number={}", id, accountNumber);
            return "Account updated successfully.";
        } catch (AccountNotFoundException e) {
            logger.warn("Account not found for update with ID: {}", id, e);
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            logger.error("Error updating customer with ID: {}", id, e);
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Deletes an account by its ID.
     *
     * @param id The ID of the account to delete.
     * @return Success or error message.
     */

    @WebMethod
    public String deleteAccount(int id) {
        logger.info("Received request to delete account with ID: {}", id);
        try {
            // Delegate deletion to business logic.
            iAccountBean.deleteAccount(id);
            logger.info("Account deleted successfully with ID: {}", id);
            return "Account deleted successfully.";
        } catch (AccountNotFoundException e) {
            logger.warn("Account not found for deletion with ID: {}", id, e);
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            logger.error("Error deleting customer with ID: {}", id, e);
            return "Error: " + e.getMessage();
        }
    }
}
