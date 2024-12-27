package src.database;

import src.entities.Theatre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TheatreDatabase {

    public static void addTheatre(Theatre theatre) {
        String query = "INSERT INTO theatres (Name, Location) VALUES (?,?)";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, theatre.getName());
            statement.setString(2, theatre.getLocation());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Theatre successfully added");
            } else {
                System.out.println("Failed to add new theatre ");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding theatre : " + e.getMessage());
        }
    }

    public static List<Theatre> listAllTheatres() {
        String query = "SELECT * FROM theatres";
        List<Theatre> theatres = new ArrayList<>();
        try (Connection connection = CreateConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                Theatre theatre = new Theatre();
                theatre.setTheatreID(resultSet.getInt("ID"));
                theatre.setName(resultSet.getString("Name"));
                theatre.setLocation(resultSet.getString("Location"));
                theatres.add(theatre);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving theatres: " + e.getMessage());
        }
        return theatres;
    }

    public static void deleteTheatre(String theatreName) {
        String query = "DELETE FROM theatres WHERE Name = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, theatreName);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Theatre successfully deleted");
            } else {
                System.out.println("Failed to delete theatre");
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting theatre: " + e.getMessage());
        }
    }

    public static boolean theatreExists(String name) {
        String query = "SELECT COUNT(*) FROM theatres WHERE Name = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error while checking if theatre exists: " + e.getMessage());
        }
        return false;
    }

    public static List<Theatre> getTheatresByLocation(String location) {
        String query = "SELECT * FROM theatres WHERE Location = ?";
        List<Theatre> theatres = new ArrayList<>();
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, location);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Theatre theatre = new Theatre();
                    theatre.setTheatreID(resultSet.getInt("ID"));
                    theatre.setName(resultSet.getString("Name"));
                    theatre.setLocation(resultSet.getString("Location"));
                    theatres.add(theatre);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving theatres: " + e.getMessage());
        }
        return theatres;
    }
}