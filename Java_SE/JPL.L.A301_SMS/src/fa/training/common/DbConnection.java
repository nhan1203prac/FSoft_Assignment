package fa.training.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String DB_URL = "jdbc:sqlserver://DESKTOP-U2GTCVE:1433;databaseName=SMS;encrypt=true;trustServerCertificate=true;";
    private static String userName = "sa";
    private static String password = "123456789";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, userName, password);
    }
}
