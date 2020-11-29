package com.devcolibri.servlet.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static final String url = "jdbc:mysql://localhost:3306/payment_service";
    private static final String username = "root";
    private static final String password = "rootroot";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;

        } catch (Exception ex) {
            return null;
        }
    }

    public static void close(Connection conn) {
        try {
            conn.close();
        } catch (Exception ex) {
        }
    }

    /*
    public static Connection getConnection() {
        return ConnectionPool.getInstance().getConnection();
    }

    public static void close(Connection conn) {
        try {
            conn.close();
        } catch (Exception ex) {
        }
    }
     */
}