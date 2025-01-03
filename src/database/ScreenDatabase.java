package src.database;

import src.entities.Screen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScreenDatabase {
    public static void addScreen(Screen screen) {
        String query = "INSERT INTO screens (TheatreID, ScreenNo) VALUES (?,?)";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, screen.getTheatreID());
            statement.setInt(2, screen.getScreenNo());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Screen successfully added");
            } else {
                System.out.println("Failed to add new theatre ");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding theatre : " + e.getMessage());
        }
    }

    public static List<Screen> listAllScreens(int theatreID) {
        String query = "SELECT * FROM screens WHERE TheatreID = ?";
        List<Screen> screens = new ArrayList<>();
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, theatreID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Screen screen = new Screen();
                    screen.setScreenID(resultSet.getInt("ScreenID"));
                    screen.setTheatreID(resultSet.getInt("TheatreID"));
                    screen.setScreenNo(resultSet.getInt("ScreenNo"));
                    screens.add(screen);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving screens: " + e.getMessage());
        }
        return screens;
    }


    public static void deleteScreen(int screenID) {
        String query = "DELETE FROM screens WHERE ScreenID = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, screenID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Screen successfully deleted");
            } else {
                System.out.println("Failed to delete screen");
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting screen: " + e.getMessage());
        }
    }

    public static boolean checkScreenExists(int screenNo, int theatreID) {
        String query = "SELECT 1 FROM screens WHERE ScreenNo = ? AND TheatreID = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, screenNo);
            statement.setInt(2, theatreID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("Error while checking screen exists: " + e.getMessage());
        }
        return false;
    }

    public static boolean checkScreenExists(int screenID) {
        String query = "SELECT 1 FROM screens WHERE ScreenID = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, screenID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error while checking screen exists: " + e.getMessage());
        }
        return false;
    }

    public static int getScreenID(int theatreID, int screenNo) {
        String query = "SELECT ScreenID FROM screens \n" +
                "WHERE ScreenNo = ? AND TheatreID = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, screenNo);
            statement.setInt(2, theatreID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("ScreenID");
            }
        } catch (SQLException e) {
            System.out.println("Error while checking if theatre exists: " + e.getMessage());
        }
        return -1;
    }

    public static boolean hasFutureShows(int screenID) {
        String query = "SELECT * FROM screens s\n" +
                "JOIN shows sh ON s.ScreenID = sh.ScreenID\n" +
                "WHERE s.ScreenID = ?; ";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, screenID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error while checking if theatre exists: " + e.getMessage());
        }
        return false;
    }
}
