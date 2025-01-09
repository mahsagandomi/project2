import Entity.Account;
import Entity.Customer;
import Model.AccountType;
import jakarta.persistence.*;

import java.time.LocalDate;

import Exception.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main class to demonstrate customer and account creation process.
 * This class connects to a database using JPA (Java Persistence API)
 * and creates multiple customers, each having multiple accounts.
 * The process is logged and transaction handling is used to ensure consistency.
 *
 * @author mahsa
 */
public class Main {
    // Logger instance for logging information and errors during execution
    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * Main method that initializes the EntityManager, begins a transaction,
     * and creates customers along with their accounts in the database.
     * The customers and accounts are persisted to the database, and any errors are logged.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create an EntityManagerFactory using the specified persistence unit
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceUnit");
        // Create an EntityManager from the factory to interact with the database
        EntityManager em = emf.createEntityManager();
        // Begin a transaction to ensure atomicity of database operations
        em.getTransaction().begin();

        try {
            // Log that the customer and account creation process has started
            logger.info("Starting the customer and account creation process...");


            // Each customer has multiple accounts of different types

            // Customer 1
            Customer customer1 = new Customer("0440888451", "Ali", "Rezaei", "Tehran, Khiaban Enghelab", "09121234567", LocalDate.of(1990, 5, 15));
            Account account1 = new Account(101, 123456, 5000000, AccountType.CHECKING);
            Account account2 = new Account(102, 654321, 2000000, AccountType.SAVING);
            customer1.addAccount(account1);
            customer1.addAccount(account2);
            // Customer 2
            Customer customer2 = new Customer("0764749432", "Maryam", "Kazemi", "Esfahan, Meydan Naghshe Jahan", "09351234567", LocalDate.of(1985, 8, 20));
            Account account3 = new Account(103, 987654, 3000000, AccountType.BUSINESS);
            customer2.addAccount(account3);
            // Customer 3
            Customer customer3 = new Customer("0438429751", "Hossein", "Ahmadi", "Mashhad, Khiaban Imam Reza", "09131234567", LocalDate.of(1995, 12, 10));
            Account account4 = new Account(104, 112233, 7000000, AccountType.CURRENT);
            Account account5 = new Account(105, 334455, 1500000, AccountType.CHECKING);
            customer3.addAccount(account4);
            customer3.addAccount(account5);
            // Customer 4
            Customer customer4 = new Customer("9876543791", "Sara", "Mohammadi", "Shiraz, Khiaban Zand", "09221234567", LocalDate.of(1992, 7, 5));
            Account account6 = new Account(106, 445566, 2500000, AccountType.SAVING);
            customer4.addAccount(account6);
            // Customer 5
            Customer customer5 = new Customer("0983421975", "Reza", "Ebrahimi", "Tabriz, Khiaban Shariati", "09141234567", LocalDate.of(1988, 3, 15));
            Account account7 = new Account(107, 556677, 4000000, AccountType.CHECKING);
            Account account8 = new Account(108, 667788, 3000000, AccountType.CURRENT);
            customer5.addAccount(account7);
            customer5.addAccount(account8);
            // Customer 6
            Customer customer6 = new Customer("0043721899", "Neda", "Karimi", "Karaj, Khiaban Azadi", "09361234567", LocalDate.of(1991, 11, 22));
            Account account9 = new Account(109, 778899, 6000000, AccountType.SAVING);
            customer6.addAccount(account9);
            // Customer 7
            Customer customer7 = new Customer("9845321789", "Hamed", "Rahmani", "Qom, Khiaban Saheli", "09151234567", LocalDate.of(1987, 4, 18));
            Account account10 = new Account(110, 889900, 3500000, AccountType.CHECKING);
            customer7.addAccount(account10);
            // Customer 8
            Customer customer8 = new Customer("1237895490", "Zahra", "Shahbazi", "Yazd, Khiaban Jomhouri", "09181234567", LocalDate.of(1993, 9, 9));
            Account account11 = new Account(111, 990011, 8000000, AccountType.CHECKING);
            Account account12 = new Account(112, 111222, 2000000, AccountType.SAVING);
            customer8.addAccount(account11);
            customer8.addAccount(account12);
            // Customer 9
            Customer customer9 = new Customer("3487307519", "Amir", "Najafi", "Rasht, Khiaban Golsar", "09191234567", LocalDate.of(1996, 6, 30));
            Account account13 = new Account(113, 222333, 4500000, AccountType.CURRENT);
            customer9.addAccount(account13);
            // Customer 10
            Customer customer10 = new Customer("0843196530", "Fatemeh", "Bahrami", "Kerman, Khiaban Ferdowsi", "09421234567", LocalDate.of(1989, 2, 14));
            Account account14 = new Account(114, 333444, 10000000, AccountType.SAVING);
            Account account15 = new Account(115, 444555, 5000000, AccountType.CHECKING);
            customer10.addAccount(account14);
            customer10.addAccount(account15);

            // Persist customers and accounts in the database
            em.persist(customer1);
            em.persist(customer2);
            em.persist(customer3);
            em.persist(customer4);
            em.persist(customer5);
            em.persist(customer6);
            em.persist(customer7);
            em.persist(customer8);
            em.persist(customer9);
            em.persist(customer10);

            // Commit transaction
            em.getTransaction().commit();
            logger.info("10 records have been successfully added.");
            System.out.println("10 records have been successfully added.");
        } catch (BirthdateException e) {
            // Handle the exception when the birthdate is invalid
            em.getTransaction().rollback();
            logger.error("Invalid birthdate: " + e.getMessage());
            System.out.println("Invalid birthdate: " + e.getMessage());
        } catch (AccountTypeException e) {
            // Handle the exception when the account type is invalid
            em.getTransaction().rollback();
            System.out.println("Invalid account type: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            em.getTransaction().rollback();
            logger.error("Invalid account type: " + e.getMessage());
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            // Close EntityManager and EntityManagerFactory to release resources
            em.close();
            emf.close();
            // Log that the resources are closed
            logger.info("EntityManager and EntityManagerFactory closed.");
        }
    }
}
