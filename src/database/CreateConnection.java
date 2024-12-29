package src.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ticket_booking";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Jerry@686670";

    public static Connection getConnection () throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
