package Entity;

import Model.CustomerValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import Exception.BirthdateException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a customer.
 * <p>
 * This class maps to the "customer" table in the database and provides fields for
 * customer details such as ID, name, family name, address, phone, and birthday.
 * It also defines named queries for fetching and updating customer information.
 * </p>
 */
// Indicates that this class is a JPA entity and should be mapped to a table
@Entity
// Maps the class to the "customer" table in the database
@Table(name = "customer")
@NamedQueries({@NamedQuery(query = "SELECT c FROM Customer c WHERE c.customerId = :id", name = "selectCustomerQuery"),
        @NamedQuery(query = "UPDATE Customer c SET c.customerAddress = :customerAddress,c.customerPhone=:customerPhone WHERE c.customerId = :id", name = "updateCustomerQuery"),
        @NamedQuery(query = "DELETE FROM Customer c WHERE c.customerId = :id", name = "deleteCustomerQuery"),
        @NamedQuery(query = "SELECT c FROM Customer c JOIN c.accounts a WHERE a.accountBalance > :balance", name = "findCustomersWithBalance")
})
public class Customer {
    // Primary key for the Customer entity
    @Id
    @Column(name = "customerId")
    // Validates that customerId cannot be null
    @NotNull(message = "Customer ID cannot be null")
    // Validates customerId must be exactly
    @Pattern(regexp = "\\d{10}", message = "Customer ID must be exactly 10 digits")
    private String customerId;
    // Basic field for storing the customer's first name
    @Basic
    @Column(name = "customerName")
    // Validates that customer name cannot be null
    @NotNull(message = "Customer name cannot be null")
    // Validates customer name length
    @Size(min = 2, max = 50, message = "Customer name must be between 2 and 50 characters")
    private String customerName;
    // Basic field for storing the customer's family name
    @Basic
    @Column(name = "customerFamily")
    // Validates that customer family cannot be null
    @NotNull(message = "Customer family cannot be null")
    // Validates customer family length
    @Size(min = 2, max = 50, message = "Customer family must be between 2 and 50 characters")
    private String customerFamily;
    // Basic field for storing the customer's address
    @Basic
    @Column(name = "customerAddress")
    // Validates maximum address length
    @Size(max = 255, message = "Customer address must be less than 255 characters")
    private String customerAddress;
    // Basic field for storing the customer's phone number
    @Basic
    @Column(name = "customerPhone")
    // Validates phone number format
    @Pattern(regexp = "0\\d{10}", message = "Customer phone must be 11 digits and start with '0'")
    private String customerPhone;
    // Basic field for storing the customer's birthdate
    @Basic
    @Column(name = "customerBirthday")
    private LocalDate customerBirthday;
    // One-to-many relationship with the Account entity (Lazy fetching for performance)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Account> accounts;

    // Default constructor for JPA
    public Customer() {
    }

    /**
     * Parameterized constructor to initialize a customer.
     *
     * @param customerId       the unique identifier of the customer
     * @param customerName     the first name of the customer
     * @param customerFamily   the family name of the customer
     * @param customerAddress  the address of the customer
     * @param customerPhone    the phone number of the customer
     * @param customerBirthday the birthdate of the customer
     * @throws BirthdateException if the provided birthdate is invalid
     */
    public Customer(String customerId, String customerName, String customerFamily, String customerAddress, String customerPhone, LocalDate customerBirthday) throws BirthdateException {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerFamily = customerFamily;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        // Validate the birthdate using CustomerValidation class
        CustomerValidation.customerBirthDateValidation(customerBirthday);
        this.customerBirthday = customerBirthday;
    }

    // Getter and setter for properties.
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerFamily() {
        return customerFamily;
    }

    public void setCustomerFamily(String customerFamily) {
        this.customerFamily = customerFamily;
    }

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

    public LocalDate getCustomerBirthday() {
        return customerBirthday;
    }

    public void setCustomerBirthday(LocalDate customerBirthday) throws BirthdateException {
        CustomerValidation.customerBirthDateValidation(customerBirthday);
        this.customerBirthday = customerBirthday;
    }

    public List<Account> getAccount() {
        return accounts;
    }

    public void setAccount(List<Account> account) {
        this.accounts = account;
    }

    // Method to add an account to the customer
    public void addAccount(Account account) {
        if (this.accounts == null) {
            this.accounts = new ArrayList<>();
        }
        this.accounts.add(account);
        account.setCustomer(this);
    }

    /**
     * Overridden toString method to provide a string representation of the customer object.
     *
     * @return a string representation of the customer with its fields
     */

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerFamily='" + customerFamily + '\'' +
                '}';
    }
}
