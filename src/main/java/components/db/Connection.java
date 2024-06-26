package components.db;

import components.schemas.Error;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Connection {

    public DatabaseManager db;

    public Connection () {
        if (this.db == null) {
            this.db = new DatabaseManager(); // Lazy initialization
        }
    }

    public Connection(DatabaseManager db){
        this.db = db;
    }

    public java.sql.Connection conn;

    public Users getUserDetailsFromDB(String user_id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Users user = new Users();
        try {
            System.out.println(user_id);
            conn = db.getConnection();
            String query = "SELECT * FROM users WHERE user_id = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, user_id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Process the result set and populate 'user' object
                user.setId(Integer.parseInt(resultSet.getString("user_id")));
                user.setAmount(resultSet.getFloat("amount"));
                user.setCurrency(resultSet.getString("currency"));
            } else {
                user = null;
                System.out.println("No user found with ID: " + user_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean updateUserDetailsInDB(Users user){
        PreparedStatement statement = null;
        try {
            System.out.println("User new balance amount is " + user.getAmount());
            conn = db.getConnection();
            String query = "UPDATE users SET amount = ?, currency = ? where user_id = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, String.valueOf(user.getAmount()));
            statement.setString(2, user.getCurrency());
            statement.setString(3, String.valueOf(user.getId()));
            int rowsAffected = statement.executeUpdate();
            System.out.println(statement);
            if (rowsAffected > 0) {
                System.out.println("Database update successful.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Error errorResponse = new Error();
            errorResponse.setMessage("SQL Server Error Response");
            errorResponse.setCode("500");
            return false;
        }
        return false;
    }

    public boolean createUserInDB(Users user) {
        PreparedStatement statement = null;
        try {
            conn = db.getConnection();
            String query = "INSERT into users set user_id = ?, amount = ?, currency = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, String.valueOf(user.getId()));
            statement.setString(2, String.valueOf(user.getAmount()));
            statement.setString(3, user.getCurrency());
            int rowsAffected = statement.executeUpdate();
            System.out.println(statement);
            if (rowsAffected > 0) {
                System.out.println("Database update successful.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Error errorResponse = new Error();
            errorResponse.setMessage("SQL Server Error Response");
            errorResponse.setCode("500");
            return false;
        }
        return false;
    }

    public boolean updateMessageDB(String user_id, String messageId, String DebitCredit){
        PreparedStatement statement = null;
        try{
            conn = db.getConnection();
            String query = "INSERT INTO messages SET message_id = ?, user_id = ?, action = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, messageId);
            statement.setString(2, user_id);
            statement.setString(3, DebitCredit);
            int rowsAffected = statement.executeUpdate();
            System.out.println(statement);
            if (rowsAffected > 0) {
                System.out.println("Database update successful.");
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
