package components.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/banking_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
//                System.out.println("SQL database connection successful");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("SQL database connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
