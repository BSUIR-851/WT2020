package com.devcolibri.servlet.database;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static final Logger log = Logger.getLogger(ConnectionPool.class);

    private static ConnectionPool instance = null;

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() {
        Context ctx;
        Connection conn = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PaymentServiceDBPool");
            conn = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            log.error(ex);
        }

        return conn;
    }
}
