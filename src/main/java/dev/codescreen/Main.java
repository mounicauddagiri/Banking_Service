package dev.codescreen;
import components.db.DatabaseManager;
import controller.ApiController;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Hello");
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.createSchemaIfNotExists();
        dbManager.createTablesIfNotExists();

        ApiController controller = new ApiController();
        controller.setupRoutes();
    }
}
