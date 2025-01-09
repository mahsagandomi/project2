package Client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import Entity.Account;
import Model.AccountType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import Exception.AccountNotFoundException;

/**
 * The AccountSoapClient class is responsible for sending SOAP requests to a SOAP web service
 * for managing accounts. It supports operations like creating, finding, updating, and deleting
 * accounts by interacting with the AccountSoapService.
 * This client uses the HttpURLConnection class to send SOAP messages and handle responses.
 * It also uses logging to provide insight into the request/response process.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class AccountSoapClient {
    // Logger instance for logging request/response details
    private static final Logger logger = LogManager.getLogger(AccountSoapClient.class);

    /**
     * Main method that demonstrates how to use the SOAP client for various account operations.
     * It creates an account, finds an account, updates an account, and deletes an account by
     * sending SOAP requests to the AccountSoapService.
     */

    public static void main(String[] args) {
        try {
            String soapEndpointUrl = "http://localhost:7001/project2/AccountSoapService";
            Account account = new Account(123, 7891011, 1000.00, AccountType.CHECKING);
            // Demonstrating SOAP requests for various operations
            createAccount(soapEndpointUrl, account);
            findAccount(soapEndpointUrl, 12);
            updateAccount(soapEndpointUrl, 12, 45);
            deleteAccount(soapEndpointUrl, 12);
        } catch (AccountNotFoundException e) {
            logger.error("Account not found: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Sends a SOAP request to create a new account.
     *
     * @param endpointUrl the URL of the SOAP service
     * @param account     the account object to be created
     * @throws Exception if an error occurs while sending the request
     */

    public static void createAccount(String endpointUrl, Account account) throws Exception {
        String soapAction = "http://AccountService/createAccount";
        // Construct the SOAP request message
        String soapMessage =
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "    <soap:Body>" +
                        "        <createAccount xmlns=\"http://AccountService/\">" +
                        "            <account>" +
                        "                <id>" + account.getAccountId() + "</id>" +
                        "                <accountNumber>" + account.getAccountNumber() + "</accountNumber>" +
                        "                <balance>" + account.getAccountBalance() + "</balance>" +
                        "                <accountType>" + account.getAccountType() + "</accountType>" +
                        "            </account>" +
                        "        </createAccount>" +
                        "    </soap:Body>" +
                        "</soap:Envelope>";

        logger.info("Sending createAccount SOAP request: " + soapMessage);
        sendSoapRequest(endpointUrl, soapAction, soapMessage);
    }

    /**
     * Sends a SOAP request to find an account by its ID.
     *
     * @param endpointUrl the URL of the SOAP service
     * @param accountId   the ID of the account to find
     * @throws Exception if an error occurs while sending the request
     */

    public static void findAccount(String endpointUrl, int accountId) throws Exception {
        String soapAction = "http://AccountService/findAccount";
        // Construct the SOAP request message
        String soapMessage =
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "    <soap:Body>" +
                        "        <findAccount xmlns=\"http://AccountService/\">" +
                        "            <id>" + accountId + "</id>" +
                        "        </findAccount>" +
                        "    </soap:Body>" +
                        "</soap:Envelope>";
        logger.info("Sending findAccount SOAP request for accountId: " + accountId);
        sendSoapRequest(endpointUrl, soapAction, soapMessage);
    }

    /**
     * Sends a SOAP request to update an existing account.
     *
     * @param endpointUrl   the URL of the SOAP service
     * @param accountId     the ID of the account to update
     * @param accountNumber the new account number to set
     * @throws Exception if an error occurs while sending the request
     */

    public static void updateAccount(String endpointUrl, int accountId, int accountNumber) throws Exception {
        String soapAction = "http://AccountService/updateAccount";
        // Construct the SOAP request message
        String soapMessage =
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<soap:Body>" +
                        "    <updateAccount xmlns=\"http://AccountService/\">" +
                        "        <id>" + accountId + "</id>" +
                        "        <accountNumber>" + accountNumber + "</accountNumber>" +
                        "    </updateAccount>" +
                        "</soap:Body>" +
                        "</soap:Envelope>";
        logger.info("Sending updateAccount SOAP request for accountId: " + accountId + " with new account number: " + accountNumber);
        sendSoapRequest(endpointUrl, soapAction, soapMessage);
    }

    /**
     * Sends a SOAP request to delete an account by its ID.
     *
     * @param endpointUrl the URL of the SOAP service
     * @param accountId   the ID of the account to delete
     * @throws Exception if an error occurs while sending the request
     */

    public static void deleteAccount(String endpointUrl, int accountId) throws Exception {
        String soapAction = "http://AccountService/deleteAccount";
        // Construct the SOAP request message
        String soapMessage =
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<soap:Body>" +
                        "    <deleteAccount xmlns=\"http://AccountService/\">" +
                        "        <id>" + accountId + "</id>" +
                        "    </deleteAccount>" +
                        "</soap:Body>" +
                        "</soap:Envelope>";
        logger.info("Sending deleteAccount SOAP request for accountId: " + accountId);
        sendSoapRequest(endpointUrl, soapAction, soapMessage);
    }

    /**
     * Sends a SOAP request to the specified SOAP endpoint with the provided SOAP action and message.
     *
     * @param endpointUrl the URL of the SOAP service
     * @param soapAction  the SOAP action to be executed
     * @param soapMessage the SOAP message to be sent
     * @throws Exception if an error occurs while sending or receiving the SOAP request
     */

    public static void sendSoapRequest(String endpointUrl, String soapAction, String soapMessage) throws Exception {
        URL url = new URL(endpointUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        connection.setRequestProperty("SOAPAction", soapAction);
        connection.setDoOutput(true);

        logger.info("Sending SOAP message: " + soapMessage);
        // Send the SOAP message
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(soapMessage.getBytes());
            outputStream.flush();
        }

        int responseCode = connection.getResponseCode();
        // If the response is OK (HTTP 200)
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Parse and log the SOAP response
            try (InputStream inputStream = connection.getInputStream()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(inputStream);

                String responseContent = document.getElementsByTagName("return").item(0).getTextContent();
                logger.info("Received response: " + responseContent);
            }
        } else {
            logger.error("Error: HTTP " + responseCode);
        }
        // Disconnect the connection after the response is processed
        connection.disconnect();
    }
}
