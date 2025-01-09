package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The CustomerRestClient class demonstrates how to send HTTP requests to a REST ful service
 * to manage customer information. It uses Apache HttpClient to send GET, POST, PUT, and DELETE requests.
 * The class interacts with a REST service for customer CRUD operations.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class CustomerRestClient {
    // Logger for logging request/response details
    private static final Logger logger = LogManager.getLogger(CustomerRestClient.class);
    // Base URL of the REST service
    private static final String BASE_URL = "http://localhost:7001/project2/CustomerRestService/";

    /**
     * Main method to demonstrate the use of the HTTP client for CRUD operations on customers.
     * It calls methods to send GET, POST, PUT, and DELETE requests to the REST service.
     */

    public static void main(String[] args) {
        String customerId = "C123456";
        String customerJson = "{\"customerId\":\"12\", \"customerAddress\":\"Tehran-niyavaran\", \"customerPhone\":\"09123456789\"}";
        String customerJson2 = "{\"customerAddress\":\"Tehran-pasdaran\", \"customerPhone\":\"09123456789\"}";
        // Send GET, POST, PUT, DELETE requests
        sendGetRequest(customerId);
        sendPostRequest(customerJson);
        sendPutRequest(customerId, customerJson2);
        sendDeleteRequest(customerId);
    }

    /**
     * Sends an HTTP GET request to retrieve a customer's information.
     *
     * @param customerId the customer ID to retrieve information for
     */

    public static void sendGetRequest(String customerId) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create the GET request for the specified customer ID
            HttpGet getRequest = new HttpGet(BASE_URL + customerId);
            getRequest.addHeader("accept", "application/json");
            // Execute the request
            HttpResponse response = httpClient.execute(getRequest);
            // Check if the response status is OK (200)
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            // Read and log the response content
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String output;
            logger.info("GET Response: ");
            while ((output = br.readLine()) != null) {
                logger.info(output);
            }
        } catch (IOException e) {
            logger.error("Error occurred while sending GET request: " + e.getMessage(), e);
        }
    }

    /**
     * Sends an HTTP POST request to create a new customer.
     *
     * @param customerJson the JSON string representing the new customer
     */

    public static void sendPostRequest(String customerJson) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create the POST request to create a new customer
            HttpPost postRequest = new HttpPost(BASE_URL);
            postRequest.addHeader("accept", "application/json");
            postRequest.addHeader("Content-Type", "application/json");
            postRequest.setEntity(new StringEntity(customerJson));
            // Execute the request
            HttpResponse response = httpClient.execute(postRequest);
            // Check if the response status is OK (200)
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            // Log the response code
            logger.info("POST Response Code: " + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            logger.error("Error occurred while sending POST request: " + e.getMessage(), e);
        }
    }

    /**
     * Sends an HTTP PUT request to update a customer's information.
     *
     * @param customerId   the customer ID to update
     * @param customerJson the updated customer information in JSON format
     */

    public static void sendPutRequest(String customerId, String customerJson) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create the PUT request to update the customer
            HttpPut putRequest = new HttpPut(BASE_URL + customerId);
            putRequest.addHeader("accept", "application/json");
            putRequest.addHeader("Content-Type", "application/json");
            putRequest.setEntity(new StringEntity(customerJson));
            // Execute the request
            HttpResponse response = httpClient.execute(putRequest);
            // Check if the response status is OK (200)
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            // Log the response code
            logger.info("PUT Response Code: " + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            logger.error("Error occurred while sending PUT request: " + e.getMessage(), e);
        }
    }

    /**
     * Sends an HTTP DELETE request to delete a customer by their ID.
     *
     * @param customerId the customer ID to delete
     */
    public static void sendDeleteRequest(String customerId) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create the DELETE request to remove the customer
            HttpDelete deleteRequest = new HttpDelete(BASE_URL + customerId);
            // Execute the request
            HttpResponse response = httpClient.execute(deleteRequest);
            // Check if the response status is OK (200)
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            // Log the response code
            logger.info("DELETE Response Code: " + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            logger.error("Error occurred while sending DELETE request: " + e.getMessage(), e);
        }
    }
}
