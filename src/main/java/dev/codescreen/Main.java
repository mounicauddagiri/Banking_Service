package dev.codescreen;
import components.db.DatabaseManager;
import controller.ApiController;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Hello");
        DatabaseManager.createSchemaIfNotExists();
        DatabaseManager.createTablesIfNotExists();
        ApiController.setupRoutes();
    }
}
