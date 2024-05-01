package components.requestBodies;


import com.google.gson.Gson;
import components.db.Connection;

import components.db.Users;
import components.schemas.*;
import components.schemas.Error;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;

import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
class AuthorizationRequestTest {

    Connection mockedConnection = mock(Connection.class);
    AuthorizationRequest authorizationRequest = new AuthorizationRequest(mockedConnection);
    Gson gson = new Gson();
    Error errorResponse = new Error();

    @Test
    void testValidAuthorizationRequest_Debit_Approved() throws Exception {

        // Mocking input request
        String req = "{\"userId\":\"1\",\"messageId\":\"5541wew62-e480-awwd-bd1b-e991ac67SAAC\",\"transactionAmount\":{\"amount\":10.0,\"currency\":\"USD\",\"debitOrCredit\":\"DEBIT\"}}";
        String expectedBalance = "290.00";
        ResponseCode expectedCode = ResponseCode.APPROVED;

        // Mocking Connection class
        when(mockedConnection.getUserDetailsFromDB("1")).thenReturn(new Users(1, 300.00f,"USD"));
        when(mockedConnection.updateUserDetailsInDB(any())).thenReturn(true);
        when(mockedConnection.updateMessageDB("1","5541wew62-e480-awwd-bd1b-e991ac67SAAC", "DEBIT" )).thenReturn(true);

        // Call the method
        String result = authorizationRequest.handleAuthorizationRequest(req);
        AuthorizationResponse authorizationResponse = gson.fromJson(result, AuthorizationResponse.class);
        verify(mockedConnection).getUserDetailsFromDB("1");
        Assertions.assertEquals("1",authorizationResponse.getUserId());
        Assertions.assertEquals("5541wew62-e480-awwd-bd1b-e991ac67SAAC",authorizationResponse.getMessageId());
        Assertions.assertEquals(expectedCode, authorizationResponse.getResponseCode());
        Assertions.assertEquals(expectedBalance,authorizationResponse.getBalance().getAmount());
    }

    @Test
    void testValidAuthorizationRequest_Debit_Declined() throws Exception {

        // Mocking input request
        String req = "{\"userId\":\"1\",\"messageId\":\"5541wew62-e480-awwd-bd1b-e991ac67SAAC\",\"transactionAmount\":{\"amount\":400.00,\"currency\":\"USD\",\"debitOrCredit\":\"DEBIT\"}}";
        String expectedBalance = "300.00";
        ResponseCode expectedCode = ResponseCode.DECLINED;

        // Mocking Connection class
        when(mockedConnection.getUserDetailsFromDB("1")).thenReturn(new Users(1, 300.00f,"USD"));
        when(mockedConnection.updateUserDetailsInDB(any())).thenReturn(true);
        when(mockedConnection.updateMessageDB("1","5541wew62-e480-awwd-bd1b-e991ac67SAAC", "DEBIT" )).thenReturn(true);

        // Call the method
        String result = authorizationRequest.handleAuthorizationRequest(req);
        AuthorizationResponse authorizationResponse = gson.fromJson(result, AuthorizationResponse.class);

        verify(mockedConnection).getUserDetailsFromDB("1");
        Assertions.assertEquals("1",authorizationResponse.getUserId());
        Assertions.assertEquals("5541wew62-e480-awwd-bd1b-e991ac67SAAC",authorizationResponse.getMessageId());
        Assertions.assertEquals(expectedBalance,authorizationResponse.getBalance().getAmount());
        Assertions.assertEquals(expectedCode, authorizationResponse.getResponseCode());
    }

    @Test
    void testUserNotFound() throws Exception {
        // Mocking input request
        String req = "{\"userId\": \"123\", \"messageId\": \"5541wew62-e480-awwd-bd1b-e991ac67SAAC\", \"transactionAmount\": {\"amount\": 100.00, \"currency\": \"USD\", \"debitOrCredit\": \"CREDIT\"}}";

        // Mocking Connection class
        when(mockedConnection.getUserDetailsFromDB("123")).thenReturn(null);
        String result = authorizationRequest.handleAuthorizationRequest(req);
        errorResponse = gson.fromJson(result, Error.class);

        verify(mockedConnection).getUserDetailsFromDB("123");
        Assertions.assertEquals("User Not Found", errorResponse.getMessage());
        Assertions.assertEquals("500", errorResponse.getCode());

    }
    @Test
    void testDBConnectionFailed(){
        // Mocking input request
        String req = "{\"userId\":\"1\",\"messageId\":\"5541wew62-e480-awwd-bd1b-e991ac67SAAC\",\"transactionAmount\":{\"amount\":10.0,\"currency\":\"USD\",\"debitOrCredit\":\"DEBIT\"}}";
        when(mockedConnection.getUserDetailsFromDB("1")).thenReturn(new Users(1, 200.00f,"USD"));
        when(mockedConnection.updateUserDetailsInDB(any())).thenReturn(false);

        // Call the method
        String result = authorizationRequest.handleAuthorizationRequest(req);

        verify(mockedConnection).getUserDetailsFromDB("1");
        errorResponse = gson.fromJson(result, Error.class);
        Assertions.assertEquals("SQL Server Error Response", errorResponse.getMessage());
        Assertions.assertEquals("500", errorResponse.getCode());
    }
}