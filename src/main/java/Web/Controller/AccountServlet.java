package Web.Controller;

import Business.IAccountBean;
import Entity.Account;
import Model.AccountType;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Exception.AccountNotFoundException;
import Exception.AccountTypeException;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * AccountServlet handles HTTP requests related to account operations.
 * It supports CRUD operations: Create, Read, Update, and Delete for accounts.
 * Each operation logs the request and its results, and returns a corresponding HTML response.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

@WebServlet(name = "AccountServlet", urlPatterns = "/account")
public class AccountServlet extends HttpServlet {
    // Logger for tracking events in the servlet
    private static final Logger logger = LogManager.getLogger(AccountServlet.class);
    // EJB to interact with the business logic for accounts
    @EJB
    IAccountBean iAccountBean;

    /**
     * Handles POST requests to create a new account.
     * It parses the account data from the request, validates the input,
     * and creates the account if the data is valid.
     *
     * @param req  HttpServletRequest containing the account data from the client.
     * @param resp HttpServletResponse for sending the response back to the client.
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Received POST request to create an account.");
        int accountId = -1;
        int accountNumber = -1;
        double accountBalance = -1;
        AccountType accountType = AccountType.valueOf(req.getParameter("accountType"));
        // Attempt to parse account data from the request
        try {
            accountId = Integer.parseInt(req.getParameter("accountId"));
            accountNumber = Integer.parseInt(req.getParameter("accountNumber"));
            accountBalance = Double.parseDouble(req.getParameter("accountBalance"));
            logger.debug("Parsed input data: accountId={}, accountNumber={}, accountBalance={}, accountType={}", accountId, accountNumber, accountBalance, accountType);
        } catch (NumberFormatException e) {
            logger.error("Invalid input data.", e);
        }
        // Prepare HTML response for client
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head><title>Create Account</title></head>");
        out.println("<body bgcolor=\"white\">");
        // Validate if required data is available
        if (accountId == -1 || accountNumber == -1 || accountBalance == -1 || accountType == null) {
            out.println("<h1 style='color: red;'>Invalid input data. Please check the values provided.</h1>");
            logger.warn("Invalid input data received for account creation.");
        } else {
            // Attempt to create account using the parsed data
            try {
                Account account = new Account(accountId, accountNumber, accountBalance, accountType);
                // Call the business logic to create account
                iAccountBean.createAccount(account);
                out.println("<h1 style='color: green;'>Account created successfully</h1>");
                logger.info("Account created successfully: {}", account);
            } catch (AccountTypeException e) {
                // Handle invalid account type exception
                logger.error("Invalid account type", e);
                out.println("<p style='color: red;'>Invalid account type</p>");
            } catch (Exception e) {
                // Handle other exceptions during account creation
                logger.error("Error creating account", e);
                out.println("<p style='color: red;'>Error creating account. Please try again.</p>");
            }
        }

        // End of HTML response
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Handles GET requests to fetch account details.
     * It parses the account ID from the request, fetches the account details,
     * and returns the result in an HTML format.
     *
     * @param req  HttpServletRequest containing the account ID from the client.
     * @param resp HttpServletResponse for sending the account details back to the client.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Received GET request to fetch account details.");
        int accountId = -1;
        // Attempt to parse account ID from the request
        try {
            accountId = Integer.parseInt(req.getParameter("id"));
            logger.debug("Parsed accountId from request: {}", accountId);
        } catch (NumberFormatException e) {
            logger.error("Invalid account ID", e);
        }
        // Prepare HTML response for client
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head><title>Account Info</title></head>");
        out.println("<body bgcolor=\"white\">");
        // Validate account ID
        if (accountId == -1) {
            out.println("<h1 style='color: red;'>Invalid account ID</h1>");
            logger.warn("Invalid account ID received in GET request.");
        } else {
            try {
                // Fetch account details from business logic
                Account account = iAccountBean.findAccount(accountId);
                out.println("<h1>Account Details</h1>");
                out.println("<p>Account Info is: " + account + "</p>");
                logger.info("Fetched account details successfully: {}", account);
            } catch (AccountNotFoundException e) {
                // Handle the case where the account is not found
                logger.error("Account not found for ID: {}", accountId, e);
                out.println("<h1 style='color: red;'>Account not found.</h1>");
            }
        }

        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Handles PUT requests to update an existing account.
     * It parses the account data from the request, validates the input,
     * and updates the account if the data is valid.
     *
     * @param req  HttpServletRequest containing the updated account data from the client.
     * @param resp HttpServletResponse for sending the result back to the client.
     */


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Received PUT request to update an account.");
        int accountId = -1;
        int accountNumber = -1;
        // Attempt to parse account data from the request
        try {
            accountId = Integer.parseInt(req.getParameter("accountId"));
            accountNumber = Integer.parseInt(req.getParameter("accountNumber"));
            logger.debug("Parsed input data: accountId={}, accountNumber={}", accountId, accountNumber);
        } catch (NumberFormatException e) {
            logger.error("Invalid input data for account update.", e);
        }
        // Prepare HTML response for client
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head><title>Update Account</title></head>");
        out.println("<body bgcolor=\"white\">");
        // Validate account data
        if (accountId == -1 || accountNumber == -1) {
            out.println("<h1 style='color: red;'>Invalid input data for update.</h1>");
            logger.warn("Invalid input data received for account update.");
        } else {
            try {
                // Call the business logic to update the account
                iAccountBean.updateAccount(accountId, accountNumber);
                out.println("<h1 style='color: green;'>Account updated successfully</h1>");
                logger.info("Account updated successfully: accountId={}, accountNumber={}", accountId, accountNumber);
            } catch (AccountNotFoundException e) {
                // Handle case where account is not found for update
                logger.error("Account not found for update: {}", accountId, e);
                out.println("<h1 style='color: red;'>Account not found for update.</h1>");
            }
        }

        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Handles DELETE requests to delete an existing account.
     * It parses the account ID from the request and attempts to delete the account.
     *
     * @param req  HttpServletRequest containing the account ID from the client.
     * @param resp HttpServletResponse for sending the result back to the client.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Received DELETE request to delete an account.");
        int accountId = -1;
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        // Attempt to parse account ID from the request
        try {
            accountId = Integer.parseInt(req.getParameter("id"));
            logger.debug("Parsed accountId from request: {}", accountId);
        } catch (NumberFormatException e) {
            logger.error("Invalid account ID for deletion.", e);
        }

        out.println("<html>");
        out.println("<head><title>Delete Account</title></head>");
        out.println("<body bgcolor=\"white\">");
        // Validate account ID
        if (accountId == -1) {
            out.println("<h1 style='color: red;'>Invalid account ID for deletion.</h1>");
            logger.warn("Invalid account ID received for deletion.");
        } else {
            try {
                // Call the business logic to delete the account
                iAccountBean.deleteAccount(accountId);
                out.println("<h1 style='color: green;'>Account deleted successfully</h1>");
                logger.info("Account deleted successfully: {}", accountId);
            } catch (AccountNotFoundException e) {
                // Handle the case where account is not found for deletion
                logger.error("Account not found for deletion: {}", accountId, e);
                out.println("<h1 style='color: red;'>Account not found for deletion.</h1>");
            }
        }

        out.println("</body>");
        out.println("</html>");
    }
}

