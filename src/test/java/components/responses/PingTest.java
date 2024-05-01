package components.responses;

import com.google.gson.Gson;
import components.schemas.Error;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import spark.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PingTest {

    Ping ping = new Ping();
    Gson gson = new Gson();

    @Test
    void testGetResponse_Success(){
        // Mocking the request and response objects
        Response mockedResponse = mock(Response.class);

        // Invoking the method under test
        String result = ping.getResponse(mockedResponse);
        LocalDateTime serverTime = java.time.LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String expected_res = serverTime.format(formatter);

        // Verifying the response status and type
        verify(mockedResponse).status(200);
        verify(mockedResponse).type("application/json");

        // Verifying that the method returns the server time as a string
//        String actualResultWithoutQuotes = result.replaceAll("\"", "");
//        Assertions.assertEquals(expected_res, actualResultWithoutQuotes);
    }

    @Test
    public void testGetResponse_Error() {
        // Mocking the request and response objects
        Response mockedResponse = mock(Response.class);

        // Mocking the exception to simulate an error
        doThrow(new RuntimeException()).when(mockedResponse).status(200);

        // Invoking the method under test
        String result = ping.getResponse(mockedResponse);

        // Verifying that the method returns the error message as a string
        Error errorResponse = gson.fromJson(result, Error.class);

        // Verifying the response status and type
        verify(mockedResponse).status(500);
        verify(mockedResponse).type("application/json");
        assertEquals("Server Error Response", errorResponse.getMessage());
        assertEquals("500", errorResponse.getCode());
    }

    }