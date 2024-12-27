package src.database;

import src.entities.Screen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScreenDatabase {

    public static void addScreen(Screen screen) {
        String query = "INSERT INTO screens (TheatreID, ScreenNo, NoOfRows) VALUES (?,?,?)";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, screen.getTheatreID());
            statement.setInt(2, screen.getScreenNo());
            statement.setInt(3, screen.getNoOfRows());
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
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Screen screen = new Screen();
                    screen.setScreenID(resultSet.getInt("ScreenID"));
                    screen.setTheatreID(resultSet.getInt("TheatreID"));
                    screen.setScreenNo(resultSet.getInt("ScreenNo"));
                    screen.setNoOfRows(resultSet.getInt("NoOfRows"));
                    screens.add(screen);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving screens: " + e.getMessage());
        }
        return screens;
    }


    public static void deleteScreen(int screenNo) {
        String query = "DELETE FROM screens WHERE ScreenNo = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, screenNo);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Screen successfully deleted");
            } else {
                System.out.println("Failed to delete screen");
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting screen: " + e.getMessage());
        }
    }

    public static Screen getScreenByScreenNo(int screenNo) {
        String query = "SELECT * FROM screens WHERE ScreenNo = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, screenNo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Screen screen = new Screen();
                screen.setScreenID(resultSet.getInt("ScreenID"));
                screen.setTheatreID(resultSet.getInt("TheatreID"));
                screen.setScreenNo(resultSet.getInt("ScreenNo"));
                screen.setNoOfRows(resultSet.getInt("NoOfRows"));
                return screen;
            } else {
                throw new RuntimeException("Screen No." + screenNo + " not found");
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving screen: " + e.getMessage());
            return null;
        }
    }
}
