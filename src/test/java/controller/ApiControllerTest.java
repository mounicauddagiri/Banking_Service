package controller;


import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import components.requestBodies.AuthorizationRequest;
import components.requestBodies.LoadRequest;
import components.schemas.*;
import org.junit.Before;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import org.junit.Test;
import spark.Spark;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiControllerTest {

    private ApiController apiController;

    Gson gson = new Gson();
    @Before
    public void setUp() {
        apiController = new ApiController();
        apiController.setupRoutes();
        Spark.awaitInitialization();
        apiController.loadRequest = mock(LoadRequest.class);
        apiController.authorizationRequest = mock(AuthorizationRequest.class);
    }

    @Test
    public void testPingEndpoint() throws IOException {
        URL url = new URL("http://localhost:4567/ping");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
    }

    @Test
    public void testAuthorizationEndpoint() throws IOException{
        String requestBody = "{\"userId\":\"1\",\"messageId\":\"5541wew62-e480-awwd-bd1b-e991ac67SAAC\",\"transactionAmount\":{\"amount\":100.0,\"currency\":\"USD\",\"debitOrCredit\": \"DEBIT\"}}";
        Amount balance = new Amount("20.00", "USD", DebitCredit.DEBIT);
        AuthorizationResponse res = new AuthorizationResponse("5541wew62-e480-awwd-bd1b-e991ac67SAAC", "1", ResponseCode.DECLINED, balance);
        String response = gson.toJson(res);

        when(apiController.authorizationRequest.handleAuthorizationRequest(any())).thenReturn(response);

        URL url = new URL("http://localhost:4567/authorization/5541wew61-e480-awwd-bd1b-e991ac67SAAC");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(requestBody.getBytes());

        assertEquals(HttpURLConnection.HTTP_CREATED, connection.getResponseCode());

    }

    @Test
    public void testAuthorizationEndpoint_Error() throws IOException{
        String requestBody = "{\"userId\":\"1\",\"messageId\":\"5541wew62-e480-awwd-bd1b-e991ac67SAAC\",\"transactionAmount\":{\"amount\":100.0,\"currency\":\"USD\",\"debitOrCredit\": \"DEBIT\"}}";

        when(apiController.authorizationRequest.handleAuthorizationRequest(requestBody)).thenReturn(null);

        URL url = new URL("http://localhost:4567/authorization/5541wew62-e480-awwd-bd1b-e991ac67SAAC");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(requestBody.getBytes());

        assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, connection.getResponseCode());
    }

    @Test
    public void testLoadEndpoint() throws IOException{
        String requestBody = "{\"userId\":\"1\",\"messageId\":\"5541wew62-e480-awwd-bd1b-e991ac67SAAC\",\"transactionAmount\":{\"amount\":100.0,\"currency\":\"USD\",\"debitOrCredit\": \"CREDIT\"}}";

        Amount balance = new Amount("120.00", "USD", DebitCredit.CREDIT);
        LoadResponse res = new LoadResponse("5541wew62-e480-awwd-bd1b-e991ac67SAAC", "1", balance);
        String response = gson.toJson(res);

        when(apiController.loadRequest.handleLoadRequest(any())).thenReturn(response);

        URL url = new URL("http://localhost:4567/load/5541wew63-e480-awwd-bd1b-e991ac67SAAC");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(requestBody.getBytes());

        assertEquals(HttpURLConnection.HTTP_CREATED, connection.getResponseCode());
    }

    @Test
    public void testLoadEndpoint_Error() throws IOException{
        String requestBody = "{\"userId\":\"221\",\"messageId\":\"5541wew62-e480-awwd-bd1b-e991ac67SAAC\",\"transactionAmount\":{\"amount\":100.0,\"currency\":\"USD\",\"debitOrCredit\": \"CREDIT\"}}";
//        LoadRequest loadRequestMock = mock(LoadRequest.class);
        String response = "{\"message\": \"User Not Found\",\"code\": \"500\"}";

//        apiController.loadRequest = loadRequestMock;
        when(apiController.loadRequest.handleLoadRequest(requestBody)).thenReturn(response);

        URL url = new URL("http://localhost:4567/load/5541wew64-e480-awwd-bd1b-e991ac67SAAC");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(requestBody.getBytes());

        assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, connection.getResponseCode());
    }
}