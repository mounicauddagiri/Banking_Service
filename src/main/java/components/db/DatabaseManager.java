package components.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    private static final String SCHEMA_NAME = "banking_schema";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/banking_schema";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                System.out.println("SQL database connection successful");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void createSchemaIfNotExists() {
        try {
            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            String createSchemaQuery = "CREATE DATABASE IF NOT EXISTS " + SCHEMA_NAME;
            statement.executeUpdate(createSchemaQuery);
            System.out.println("Schema created or already exists: " + SCHEMA_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createTablesIfNotExists() {
        // Add your table creation queries here
        try {

            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            String createUserTableQuery = "CREATE TABLE IF NOT EXISTS users ("
                    + "user_id INT PRIMARY KEY NOT NULL,"
                    + "amount DECIMAL(10,2),"
                    + "currency VARCHAR(45))";
            statement.executeUpdate(createUserTableQuery);
            System.out.println("Users table created or already exist");
            String createMessageTableQuery = "CREATE TABLE IF NOT EXISTS messages ("
                    + "message_id VARCHAR(50) NOT NULL,"
                    + "user_id INT,"
                    + "action VARCHAR(45),"
                    + "FOREIGN KEY (user_id) REFERENCES users(id))";
            statement.executeUpdate(createUserTableQuery);
            System.out.println("Messages table created or already exist");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
