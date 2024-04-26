package dev.codescreen;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import components.db.Connection;
import components.db.Users;
import controller.ApiController;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
//        Dao<Users,String> userDao = DaoManager.createDao(connectionSource, Users.class);
        System.out.println("Hello");
        ApiController.setupRoutes();
    }
}
