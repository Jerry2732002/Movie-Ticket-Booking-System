package src.database;

import java.sql.*;

public class AdminDatabase {

    public static boolean authenticateAdmin(String adminName, String password) {
        String query = "SELECT 1 FROM admins WHERE AdminName = ? AND Password = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, adminName);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Error while authenticating user: " + e.getMessage());
            return false;
        }
    }
}
