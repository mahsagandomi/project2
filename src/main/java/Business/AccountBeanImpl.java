package Business;

import Entity.Account;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import Exception.AccountNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Stateless session bean for managing Account entities.
 * Provides operations for creating, finding, updating, and deleting accounts.
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

@Stateless(name = "accountBean")
public class AccountBeanImpl implements IAccountBean {
    private static final Logger logger = LogManager.getLogger(AccountBeanImpl.class);
    // Persistence context for interacting with the database
    @PersistenceContext(unitName = "PersistenceUnit")
    public EntityManager entityManager;

    /**
     * Creates a new account in the database.
     *
     * @param account the Account entity to be persisted
     */
    @Override
    public void createAccount(Account account) {
        // Log the creation attempt
        logger.info("Creating account with ID: {}", account.getAccountId());
        // Persist the account entity
        entityManager.persist(account);
        // Log successful creation
        logger.info("Account created successfully with ID: {}", account.getAccountId());

    }
    /**
     * Finds an account by its ID.
     *
     * @param id the ID of the account to be retrieved
     * @return the Account entity with the specified ID
     * @throws AccountNotFoundException if no account is found with the given ID
     */

    @Override
    public Account findAccount(int id) throws AccountNotFoundException {
        try {
            // Log the search attempt
            logger.info("Searching for account with ID: {}", id);
            // Execute the named query to find the account
            Account account = (Account) entityManager.createNamedQuery("selectAccountQuery").setParameter("id", id).getSingleResult();
            // Log the successful retrieval
            logger.info("Account found: {}", account);
            return account;
        } catch (NoResultException e) {
            // Log the failure to find the account
            logger.warn("could not find account");
            throw new AccountNotFoundException("Account with id " + id + " not found.");

        }

    }
    /**
     * Updates the account number for a specific account.
     *
     * @param id the ID of the account to be updated
     * @param accountNumber the new account number to set
     * @throws AccountNotFoundException if no account is found with the given ID
     */

    @Override
    public void updateAccount(int id, int accountNumber) throws AccountNotFoundException{
        // Log the update attempt
        logger.info("Updating account with ID: {}", id);
        // Find the account before updating
        Account account = findAccount(id);
        // Update the account number
        account.setAccountNumber(accountNumber);
        // Merge changes into the database
        entityManager.merge(account);
        // Log successful update
        logger.info("Account with ID: {} updated successfully to accountNumber: {}", id, accountNumber);

    }
    /**
     * Deletes an account by its ID.
     *
     * @param id the ID of the account to be deleted
     * @throws AccountNotFoundException if no account is found with the given ID
     */

    @Override
    public void deleteAccount(int id)throws AccountNotFoundException {
        // Log the deletion attempt
        logger.info("Attempting to delete account with ID: {}", id);
        // Ensure the account exists before deletion
        findAccount(id);
        // Execute the named query to delete the account
        entityManager.createNamedQuery("deleteAccountQuery")
                .setParameter("id", id).executeUpdate();
        // Log successful deletion
        logger.info("Account with ID: {} deleted successfully.", id);
    }
}
