package Entity;

import Model.AccountType;
import Model.AccountValidation;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import Exception.AccountTypeException;

/**
 * Entity class representing an account.
 * <p>
 * This class maps to the "Account" table in the database and provides fields for
 * account details such as ID, number, balance, and type. It also defines named queries
 * for fetching and updating accounts.
 * </p>
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
// Indicates that this class is a JPA entity and should be mapped to a table
@Entity
// Maps the class to the "account" table in the database
@Table(name = "account")
@NamedQueries({@NamedQuery(query = "SELECT a FROM Account a WHERE a.accountId = :id", name = "selectAccountQuery"),
        @NamedQuery(query = "UPDATE Account a SET a.accountNumber = :accountNumber WHERE a.accountId = :id", name = "updateAaccountQuery"),
        @NamedQuery(query = "DELETE FROM Account a WHERE a.accountId = :id", name = "deleteAccountQuery"),
})
public class Account {
    // Primary key for the Account entity.
    @Id
    @Column(name = "accountId")
    @NotNull(message = "Account ID cannot be null")
    private int accountId;
    // Basic field for storing the account number.
    @Basic
    @Column(name = "accountNumber")
    @NotNull(message = "Account number cannot be null")
    @Min(value = 1000, message = "Account number must be at least 1000")
    @Max(value = 999999999, message = "Account number must be less than 1 billion")
    private int accountNumber;
    // Basic field for storing the account balance.
    @Basic
    @Column(name = "accountBalance")
    @NotNull(message = "Account balance cannot be null")
    @DecimalMin(value = "0.0", message = "Account balance must be greater than or equal to 0.0")
    @Positive(message = "Account balance must be positive")
    private double accountBalance;
    // Basic field for storing the type of account.
    @Basic
    @Column(name = "accountType")
    @NotNull(message = "Account type cannot be null")
    private AccountType accountType;
    // Many-to-one relationship with the Customer entity (Lazy fetching for performance)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", referencedColumnName = "customerId")
    private Customer customer;

    /**
     * Parameterized constructor to initialize an account.
     *
     * @param accountId      the unique identifier of the account
     * @param number         the account number
     * @param accountBalance the balance of the account
     * @param accountType    the type of the account (e.g., savings, current)
     */

    public Account(int accountId, int number, double accountBalance, AccountType accountType) throws AccountTypeException {
        this.accountId = accountId;
        this.accountNumber = number;
        this.accountBalance = accountBalance;
        // Validate the account type using AccountValidation class
        AccountValidation.accountTypeValidation(accountType);
        this.accountType = accountType;
    }

    /**
     * Default constructor for JPA.
     */

    public Account() {

    }

    // Getter and setter for properties.
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int number) {
        this.accountNumber = number;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) throws AccountTypeException {
        AccountValidation.accountTypeValidation(accountType);
        this.accountType = accountType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Overridden toString method to provide a string representation of the account object.
     *
     * @return a string representation of the account with its fields
     */
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", number=" + accountNumber +
                ", accountBalance=" + accountBalance +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}


