package components.db;

import components.schemas.Error;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Connection {

    private static java.sql.Connection conn;

    public static Users getUserDetailsFromDB(String user_id) {
        Users user = new Users();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            System.out.println(user_id);
            conn = DatabaseManager.getConnection();
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
                // Handle case where no user with the given ID was found
                System.out.println("No user found with ID: " + user_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static void updateUserDetailsInDB(Users user){
        PreparedStatement statement = null;
        try {
            System.out.println("User new balance amount is " + user.getAmount());
            conn = DatabaseManager.getConnection();
            String query = "UPDATE users SET amount = ?, currency = ? where user_id = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, String.valueOf(user.getAmount()));
            statement.setString(2, user.getCurrency());
            statement.setString(3, String.valueOf(user.getId()));
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Database update successful.");
            } else {
                System.out.println("No user found with ID: " + user.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Error errorResponse = new Error();
            errorResponse.setMessage("SQL Server Error Response");
            errorResponse.setCode("500");
        }
    }


}
