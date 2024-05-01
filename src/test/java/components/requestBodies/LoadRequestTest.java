package components.requestBodies;

import com.google.gson.Gson;
import components.db.Connection;
import components.db.Users;
import components.schemas.Error;
import components.schemas.LoadResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class LoadRequestTest {
    Connection mockedConnection = mock(Connection.class);
    LoadRequest loadRequest = new LoadRequest(mockedConnection);
    Gson gson = new Gson();
    Error errorResponse = new Error();
    @Test
    public void testLoadRequest(){
        // Mocking input request
        String req = "{\"userId\":\"1\",\"messageId\":\"5541wew62-e480-awwd-bd1b-e991ac67SAAC\",\"transactionAmount\":{\"amount\":10.0,\"currency\":\"USD\",\"debitOrCredit\":\"CREDIT\"}}";
        String expectedBalance = "210.00";

        // Mocking Connection class
        when(mockedConnection.getUserDetailsFromDB("1")).thenReturn(new Users(1, 200.00f,"USD"));
        when(mockedConnection.updateUserDetailsInDB(any())).thenReturn(true);
        when(mockedConnection.updateMessageDB("1", "5541wew62-e480-awwd-bd1b-e991ac67SAAC", "CREDIT")).thenReturn(true);

        // Call the method
        String result = loadRequest.handleLoadRequest(req);
        LoadResponse loadResponse = gson.fromJson(result, LoadResponse.class);

        verify(mockedConnection).getUserDetailsFromDB("1");
        Assertions.assertEquals("1", loadResponse.getUserId());
        Assertions.assertEquals("5541wew62-e480-awwd-bd1b-e991ac67SAAC", loadResponse.getMessageId());
        Assertions.assertEquals(expectedBalance, loadResponse.getBalance().getAmount());
    }

    @Test
    public void testInvalidAmount(){
        // Mocking input request
        String req = "{\"userId\":\"1\",\"messageId\":\"5541wew62-e480-awwd-bd1b-e991ac67SAAC\",\"transactionAmount\":{\"amount\":-10.0,\"currency\":\"USD\",\"debitOrCredit\":\"CREDIT\"}}";

        // Call the method
        LoadRequest loadRequest = new LoadRequest(mockedConnection);
        String result = loadRequest.handleLoadRequest(req);
        errorResponse = gson.fromJson(result, Error.class);

        Assertions.assertEquals("Amount must be non-negative", errorResponse.getMessage());
        Assertions.assertEquals("500", errorResponse.getCode());

    }

    @Test
    public void testDBConnectionFailed(){
        // Mocking input request
        String req = "{\"userId\":\"1\",\"messageId\":\"5541wew62-e480-awwd-bd1b-e991ac67SAAC\",\"transactionAmount\":{\"amount\":10.0,\"currency\":\"USD\",\"debitOrCredit\":\"CREDIT\"}}";

        // Mocking Connection class
        when(mockedConnection.getUserDetailsFromDB("1")).thenReturn(new Users(1, 200.00f,"USD"));
        when(mockedConnection.updateUserDetailsInDB(any())).thenReturn(false);

        // Call the method
        String result = loadRequest.handleLoadRequest(req);
        errorResponse = gson.fromJson(result, Error.class);

        verify(mockedConnection).getUserDetailsFromDB("1");
        Assertions.assertEquals("SQL Server Error Response", errorResponse.getMessage());
        Assertions.assertEquals("500", errorResponse.getCode());
    }
}