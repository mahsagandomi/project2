package Client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import Entity.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import Exception.CustomerNotFoundExceptin;

/**
 * The CustomerSoapClient class sends SOAP requests to a web service for managing customer data.
 * It handles operations like creating, finding, updating, and deleting customer records via SOAP.
 * The SOAP messages are built as XML and sent to a specified endpoint using HTTP POST requests.
 * Responses from the web service are parsed, and results are logged.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class CustomerSoapClient {
    // Logger to log details of SOAP requests and responses
    private static final Logger logger = LogManager.getLogger(CustomerSoapClient.class);

    /**
     * Main method to demonstrate the SOAP client functionality.
     * It creates a customer, finds a customer, updates a customer's data, and deletes a customer.
     */
    public static void main(String[] args) {
        try {
            // Define the SOAP endpoint URL and create a sample customer
            String soapEndpointUrl = "http://localhost:7001/project2/CustomerSoapService";
            Customer customer = new Customer("18", "hassan", "hassani", "Shiraz", "09103456731", LocalDate.parse("1985-08-15"));
            // Perform SOAP operations (create, find, update, delete customer)
            createCustomer(soapEndpointUrl, customer);
            findCustomer(soapEndpointUrl, "12");
            updateCustomer(soapEndpointUrl, "12", "Tehran", "555-5678");
            deleteCustomer(soapEndpointUrl, "12");
        } catch (CustomerNotFoundExceptin e) {
            // Handle specific exception for customer not found
            logger.error("Customer not found: " + e.getMessage(), e);
        } catch (Exception e) {
            // Handle general exceptions and log the error
            logger.error("An error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Sends a SOAP request to create a new customer.
     *
     * @param endpointUrl the URL of the SOAP service
     * @param customer    the customer object to be created
     * @throws Exception if an error occurs during the request
     */
    public static void createCustomer(String endpointUrl, Customer customer) throws Exception {
        // SOAP action to identify the operation
        String soapAction = "http://CustomerService/createCustomer";
        // Build the SOAP message to create a customer
        String soapMessage =
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "    <soap:Body>" +
                        "        <createCustomer xmlns=\"http://CustomerService/\">" +
                        "            <customer>" +
                        "                <customerId>" + customer.getCustomerId() + "</customerId>" +
                        "                <customerName>" + customer.getCustomerName() + "</customerName>" +
                        "                <customerFamily>" + customer.getCustomerFamily() + "</customerFamily>" +
                        "                <customerAddress>" + customer.getCustomerAddress() + "</customerAddress>" +
                        "                <customerPhone>" + customer.getCustomerPhone() + "</customerPhone>" +
                        "                <customerBirthday>" + customer.getCustomerBirthday() + "</customerBirthday>" +
                        "            </customer>" +
                        "        </createCustomer>" +
                        "    </soap:Body>" +
                        "</soap:Envelope>";
        // Log the SOAP request
        logger.info("Request for createCustomer: " + soapMessage);
        // Send the SOAP request
        sendSoapRequest(endpointUrl, soapAction, soapMessage);
    }

    /**
     * Sends a SOAP request to find an existing customer by their ID.
     *
     * @param endpointUrl the URL of the SOAP service
     * @param customerId  the customer ID to search for
     * @throws Exception if an error occurs during the request
     */
    public static void findCustomer(String endpointUrl, String customerId) throws Exception {
        // SOAP action to identify the operation
        String soapAction = "http://CustomerService/findCustomer";
        // Build the SOAP message to find the customer
        String soapMessage =
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "    <soap:Body>" +
                        "        <findCustomer xmlns=\"http://CustomerService/\">" +
                        "            <id>" + customerId + "</id>" +
                        "        </findCustomer>" +
                        "    </soap:Body>" +
                        "</soap:Envelope>";
        // Log the request details
        logger.info("Request for findCustomer with ID: " + customerId);
        // Send the SOAP request
        sendSoapRequest(endpointUrl, soapAction, soapMessage);
    }

    /**
     * Sends a SOAP request to update an existing customer's address and phone number.
     *
     * @param endpointUrl the URL of the SOAP service
     * @param customerId  the customer ID to update
     * @param address     the new address of the customer
     * @param phone       the new phone number of the customer
     * @throws Exception if an error occurs during the request
     */
    public static void updateCustomer(String endpointUrl, String customerId, String address, String phone) throws Exception {
        // SOAP action to identify the operation
        String soapAction = "http://CustomerService/updateCustomer";
        // Build the SOAP message to update the customer
        String soapMessage =
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<soap:Body>" +
                        "    <updateCustomer xmlns=\"http://CustomerService/\">" +
                        "        <id>" + customerId + "</id>" +
                        "        <address>" + address + "</address>" +
                        "        <phone>" + phone + "</phone>" +
                        "    </updateCustomer>" +
                        "</soap:Body>" +
                        "</soap:Envelope>";
        // Log the request details
        logger.info("Request for updateCustomer with ID: " + customerId);
        // Send the SOAP request
        sendSoapRequest(endpointUrl, soapAction, soapMessage);
    }

    /**
     * Sends a SOAP request to delete an existing customer by their ID.
     *
     * @param endpointUrl the URL of the SOAP service
     * @param customerId  the customer ID to delete
     * @throws Exception if an error occurs during the request
     */

    public static void deleteCustomer(String endpointUrl, String customerId) throws Exception {
        // SOAP action to identify the operation
        String soapAction = "http://CustomerService/deleteCustomer";
        // Build the SOAP message to delete the customer
        String soapMessage =
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<soap:Body>" +
                        "    <deleteCustomer xmlns=\"http://CustomerService/\">" +
                        "        <id>" + customerId + "</id>" +
                        "    </deleteCustomer>" +
                        "</soap:Body>" +
                        "</soap:Envelope>";
        // Log the request details
        logger.info("Request for deleteCustomer with ID: " + customerId);
        // Send the SOAP request
        sendSoapRequest(endpointUrl, soapAction, soapMessage);
    }

    /**
     * Sends a SOAP request to the specified endpoint and logs the response.
     *
     * @param endpointUrl the URL of the SOAP service
     * @param soapAction  the SOAP action to identify the operation
     * @param soapMessage the SOAP message to send
     * @throws Exception if an error occurs during the request
     */
    public static void sendSoapRequest(String endpointUrl, String soapAction, String soapMessage) throws Exception {
        URL url = new URL(endpointUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // Set content type for SOAP
        connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        // Set SOAP action
        connection.setRequestProperty("SOAPAction", soapAction);
        // Allow output stream for sending the message
        connection.setDoOutput(true);
        // Write the SOAP message to the output stream
        try (OutputStream outputStream = connection.getOutputStream()) {
            // Write the message in byte format
            outputStream.write(soapMessage.getBytes());
            // Ensure the message is sent
            outputStream.flush();
            // Log the send message
            logger.info("SOAP message sent: " + soapMessage);
        }
        // Get the response code from the service
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Parse the response if the request is successful
            try (InputStream inputStream = connection.getInputStream()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(inputStream);
                // Extract and log the response content
                String responseContent = document.getElementsByTagName("return").item(0).getTextContent();
                logger.info("Response Content: " + responseContent);
            }
        } else {
            // Log the error if the response code is not OK
            logger.error("Error: HTTP " + responseCode);
        }
        // Close the connection
        connection.disconnect();
    }
}
