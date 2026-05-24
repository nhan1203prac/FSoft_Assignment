package com.cms.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static final String DB_URL = "jdbc:sqlserver://DESKTOP-U2GTCVE:1433;databaseName=cms_db;encrypt=true;trustServerCertificate=true;";
    private static String userName = "sa";
    private static String password = "123456789";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("SQL Server Driver not found!");
        }
        return DriverManager.getConnection(DB_URL, userName, password);
    }

}