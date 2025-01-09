package Business;

import Entity.Account;
import Exception.AccountNotFoundException;


/**
 * Interface IAccountBean
 * <p>
 * This interface defines the core operations for managing {@link Account} entities, including
 * creation, retrieval, and updates. It serves as a blueprint for any class
 * implementing account-related business logic. Classes implementing this interface are
 * responsible for defining the actual behavior for managing accounts in the business layer.
 * </p>
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public interface IAccountBean {

    /**
     * Creates a new {@link Account} in the database.
     *
     * @param account The {@link Account} object that needs to be created.
     * @throws IllegalArgumentException if the provided account is invalid.
     */
    void createAccount(Account account);

    /**
     * Finds and retrieves an account by its unique identifier.
     *
     * @param id the unique identifier of the account.
     * @return the {@link Account} object if found.
     * @throws AccountNotFoundException if no account is found with the given ID.
     */
    Account findAccount(int id) throws AccountNotFoundException;

    /**
     * Updates the details of an existing account, specifically the account number.
     *
     * @param id            the unique identifier of the account to update.
     * @param accountNumber the new account number to set.
     * @throws AccountNotFoundException if no account is found with the given ID.
     */
    void updateAccount(int id, int accountNumber) throws AccountNotFoundException;

    /**
     * Deletes an account by its unique identifier.
     *
     * @param id the unique identifier of the account to delete.
     * @throws AccountNotFoundException if no account is found with the given ID.
     */
    void deleteAccount(int id) throws AccountNotFoundException;

}

