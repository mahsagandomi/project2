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
 * This class provides methods for sending HTTP requests (GET, POST, PUT, DELETE)
 * to a REST ful web service for managing account data.
 * It uses Apache HttpClient for communication with the REST API.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class AccountRestClient {
    // Logger instance to log HTTP requests and responses
    private static final Logger logger = LogManager.getLogger(AccountRestClient.class);
    // Base URL of the REST ful web service
    private static final String BASE_URL = "http://localhost:7001/project2/AccountRestService/";

    public static void main(String[] args) {
        // Example account data
        int accountId = 10;
        String accountJson = "{\"accountNumber\":123456, \"accountBalance\":1000, \"accountType\":\"savings\", \"customerId\":\"1\"}";
        String accountJson2 = "{\"accountNumber\":123456}";
        // Sending GET, POST, PUT, DELETE requests
        sendGetRequest(accountId);
        sendPostRequest(accountJson);
        sendPutRequest(accountId, accountJson2);
        sendDeleteRequest(accountId);
    }

    /**
     * Sends an HTTP GET request to retrieve account details by account ID.
     *
     * @param accountId the ID of the account to retrieve
     */


    public static void sendGetRequest(int accountId) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create GET request
            HttpGet getRequest = new HttpGet(BASE_URL + accountId);
            getRequest.addHeader("accept", "application/json");
            logger.info("Sending GET request to: " + BASE_URL + accountId);
            // Execute request and get response
            HttpResponse response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("Failed: HTTP error code: " + response.getStatusLine().getStatusCode());
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            // Read and log the response
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String output;
            logger.info("GET Response: ");
            while ((output = br.readLine()) != null) {
                logger.info(output);
            }
        } catch (IOException e) {
            logger.error("Error sending GET request: " + e.getMessage(), e);
        }
    }

    /**
     * Sends an HTTP POST request to create a new account with the given account details.
     *
     * @param accountJson JSON string representing the account to create
     */

    public static void sendPostRequest(String accountJson) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create POST request
            HttpPost postRequest = new HttpPost(BASE_URL);
            postRequest.addHeader("accept", "application/json");
            postRequest.addHeader("Content-Type", "application/json");
            postRequest.setEntity(new StringEntity(accountJson));
            logger.info("Sending POST request with JSON: " + accountJson);
            // Execute request and get response
            HttpResponse response = httpClient.execute(postRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("Failed: HTTP error code: " + response.getStatusLine().getStatusCode());
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            logger.info("POST Response Code: " + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            logger.error("Error sending POST request: " + e.getMessage(), e);
        }
    }

    /**
     * Sends an HTTP PUT request to update the details of an existing account.
     *
     * @param accountId   the ID of the account to update
     * @param accountJson JSON string representing the updated account details
     */


    public static void sendPutRequest(int accountId, String accountJson) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create PUT request
            HttpPut putRequest = new HttpPut(BASE_URL + accountId);
            putRequest.addHeader("accept", "application/json");
            putRequest.addHeader("Content-Type", "application/json");

            putRequest.setEntity(new StringEntity(accountJson));
            logger.info("Sending PUT request to: " + BASE_URL + accountId + " with JSON: " + accountJson);
            // Execute request and get response

            HttpResponse response = httpClient.execute(putRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("Failed: HTTP error code: " + response.getStatusLine().getStatusCode());
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            logger.info("PUT Response Code: " + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            logger.error("Error sending PUT request: " + e.getMessage(), e);
        }
    }

    /**
     * Sends an HTTP DELETE request to delete an account by account ID.
     *
     * @param accountId the ID of the account to delete
     */

    public static void sendDeleteRequest(int accountId) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create DELETE request
            HttpDelete deleteRequest = new HttpDelete(BASE_URL + accountId);
            logger.info("Sending DELETE request to: " + BASE_URL + accountId);
            // Execute request and get response

            HttpResponse response = httpClient.execute(deleteRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("Failed: HTTP error code: " + response.getStatusLine().getStatusCode());
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            logger.info("DELETE Response Code: " + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            logger.error("Error sending DELETE request: " + e.getMessage(), e);
        }
    }
}
