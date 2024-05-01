package components.db;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConnectionTest {

    DatabaseManager mockedDatabaseManager = mock(DatabaseManager.class);
    components.db.Connection conn = new components.db.Connection(mockedDatabaseManager){
    };
    java.sql.Connection mockedConnection = mock(Connection.class);
    PreparedStatement mockedStatement = mock(PreparedStatement.class);
    ResultSet mockedResultSet = mock(ResultSet.class);
    Users mockedUser = mock(Users.class);

    @Test
    void testGetUserDetailsFromDB() throws SQLException {

        // Mocking the DatabaseManager to return the mocked connection
        when(mockedDatabaseManager.getConnection()).thenReturn(mockedConnection);

        // Mocking the result set to return a user record
        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(true); // Simulating a user found
        when(mockedResultSet.getString("user_id")).thenReturn("123");
        when(mockedResultSet.getFloat("amount")).thenReturn(100.00f);
        when(mockedResultSet.getString("currency")).thenReturn("USD");

        // Mocking the statement preparation
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);

        // Invoking the method under test
        Users user = conn.getUserDetailsFromDB("123");

        // Verifying the user details
        assertEquals(123, user.getId());
        assertEquals(100.00f, user.getAmount());
        assertEquals("USD", user.getCurrency());
    }

    @Test
    void testGetUserDetailsFromDB_UserNotFound() throws SQLException{

        // Mocking the DatabaseManager to return the mocked connection
        when(mockedDatabaseManager.getConnection()).thenReturn(mockedConnection);

        // Mocking the result set to return an empty result set
        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(false);

        // Mocking the statement preparation
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);

        // Invoking the method under test
        Users user = conn.getUserDetailsFromDB("123");

        // Verifying that null is returned when no user is found
        assertNull(user);
    }

    @Test
    void testUpdateUserDetailsInDB() throws SQLException {
        when(mockedDatabaseManager.getConnection()).thenReturn(mockedConnection);

        // Mocking the statement preparation
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);

        // Mocking the update query execution to return 1 row affected
        when(mockedStatement.executeUpdate()).thenReturn(1);

        // Creating a mock Users object
        when(mockedUser.getId()).thenReturn(1);
        when(mockedUser.getAmount()).thenReturn(100.00f);
        when(mockedUser.getCurrency()).thenReturn("USD");

        // Invoking the method under test
        boolean result = conn.updateUserDetailsInDB(mockedUser);

        // Verifying that the database update was successful
        assertTrue(result);
    }

    @Test
    void testUpdateUserDetailsInDB_failed() throws SQLException{

        // Mocking the DatabaseManager to return the mocked connection
        when(mockedDatabaseManager.getConnection()).thenReturn(mockedConnection);

        // Mocking the statement preparation
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);

        // Mocking the update query execution to return 0 rows affected
        when(mockedStatement.executeUpdate()).thenReturn(0);

        // Creating a mock Users object
        when(mockedUser.getAmount()).thenReturn(100.0f);
        when(mockedUser.getCurrency()).thenReturn("USD");
        when(mockedUser.getId()).thenReturn(1);

        // Invoking the method under test
        boolean result = conn.updateUserDetailsInDB(mockedUser);

        // Verifying that the database update failed
        assertFalse(result);
    }

    @Test
    void testCreateUserInDB() throws  SQLException{
        // Mocking the DatabaseManager to return the mocked connection
        when(mockedDatabaseManager.getConnection()).thenReturn(mockedConnection);

        // Mocking the statement preparation
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);

        // Mocking the insert query execution to return 1 row affected
        when(mockedStatement.executeUpdate()).thenReturn(1);

        // Creating a mock Users object
        when(mockedUser.getId()).thenReturn(123);
        when(mockedUser.getAmount()).thenReturn(100.0f);
        when(mockedUser.getCurrency()).thenReturn("USD");

        // Invoking the method under test
        boolean result = conn.createUserInDB(mockedUser);

        // Verifying that the database insertion was successful
        assertTrue(result);
    }

    @Test
    void testCreateUserInDB_Failure() throws SQLException{
        // Mocking the DatabaseManager to return the mocked connection
        when(mockedDatabaseManager.getConnection()).thenReturn(mockedConnection);

        // Mocking the statement preparation
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);

        // Mocking the insert query execution to return 0 rows affected
        when(mockedStatement.executeUpdate()).thenReturn(0);

        // Creating a mock Users object
        when(mockedUser.getId()).thenReturn(123);
        when(mockedUser.getAmount()).thenReturn(100.0f);
        when(mockedUser.getCurrency()).thenReturn("USD");

        // Invoking the method under test
        boolean result = conn.createUserInDB(mockedUser);

        // Verifying that the database insertion failed
        assertFalse(result);
    }

    @Test
    void testUpdateMessageDB() throws SQLException {
        // Mocking the DatabaseManager to return the mocked connection
        when(mockedDatabaseManager.getConnection()).thenReturn(mockedConnection);

        // Mocking the statement preparation
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);

        // Mocking the insert query execution to return 1 row affected
        when(mockedStatement.executeUpdate()).thenReturn(1);

        // Invoking the method under test
        boolean result = conn.updateMessageDB("1", "456", "CREDIT");

        // Verifying that the database insertion was successful
        assertTrue(result);
    }
}