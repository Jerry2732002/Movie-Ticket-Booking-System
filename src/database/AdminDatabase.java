package src.database;

import src.entities.Admin;
import src.entities.User;

import java.sql.*;

public class AdminDatabase {

    public static void addAdmin(Admin admin) {
        String query = "INSERT INTO admins (AdminName,Password) VALUES (?,?)\n";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, admin.getAdminName());
            statement.setString(2, admin.getPassword());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Admin was successfully added.");
            } else {
                System.out.println("Failed to add new user");
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.err.println("Error: Username already exists.");
            } else {
                System.err.println("Error while adding user: " + e.getMessage());
            }
        }
    }
    public static void removeAdmin(String adminName) {
        String query = "DELETE FROM admins WHERE AdminName = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, adminName);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Admin successfully deleted.");
            } else {
                System.out.println("No admin found with the given admin name.");
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting user: " + e.getMessage());
        }
    }

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
