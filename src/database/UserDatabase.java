package src.database;

import src.entities.User;

import java.sql.*;

public class UserDatabase {


    public static void addUser(User user) {
        String query = "INSERT INTO users (Username,Password,FirstName,LastName) VALUES (?,?,?,?)\n";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User was successfully added.");
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

    public static boolean userExists(String username) {
        String query = "SELECT 1 FROM users WHERE Username = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Error while checking user existence: " + e.getMessage());
            return false;
        }
    }

    public static User findUserByName(String username) {
        String query = "SELECT * FROM users WHERE Username = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUserID(resultSet.getInt(1));
                    user.setUsername(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setFirstName(resultSet.getString(4));
                    user.setLastName(resultSet.getString(5));
                    return user;
                }
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error while checking user existence: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static User findUserById(int userId) {
        String query = "SELECT * FROM users WHERE UserID = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUserID(resultSet.getInt(1));
                    user.setUsername(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setFirstName(resultSet.getString(4));
                    user.setLastName(resultSet.getString(5));
                    return user;
                }
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching user by ID: " + e.getMessage());
            return null;
        }
    }

    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT 1 FROM users WHERE Username = ? AND Password = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Error while authenticating user: " + e.getMessage());
            return false;
        }
    }

    public static void removeUser(String username) {
        String query = "DELETE FROM users WHERE Username = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User successfully deleted.");
            } else {
                System.out.println("No user found with the given username.");
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting user: " + e.getMessage());
        }
    }

}
