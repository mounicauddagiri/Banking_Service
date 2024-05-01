package dev.codescreen;
import components.db.DatabaseManager;
import controller.ApiController;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Hello");
        DatabaseManager dbManager = new DatabaseManager();
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/", "root", "123456");
        dbManager.createSchemaIfNotExists(conn);
        dbManager.createTablesIfNotExists(conn);

        ApiController controller = new ApiController();
        controller.setupRoutes();
    }
}
