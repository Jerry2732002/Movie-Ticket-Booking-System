package src.database;

import src.entities.Show;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowsDatabase {
    public static void addShow(Show show) {
        String query = "INSERT INTO shows (ScreenID, MovieID, StartTime, EndTime) VALUES (?,?,?,?)";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, show.getScreenID());
            statement.setInt(2, show.getMovieID());
            statement.setTime(3,show.getStartTime());
            statement.setTime(4,show.getEndTime());
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

    public static void deleteShowByShowID(int showId) {
        String query = "DELETE FROM shows WHERE ShowID = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, showId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Show deleted successfully.");
            } else {
                System.out.println("Failed to delete show.");
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting show: " + e.getMessage());
        }
    }


    public static Show getShowByScreenID(int screenID) {
        String query = "SELECT * FROM shows WHERE ScreenID = ?";
        Show show = null;
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, screenID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                show = new Show();
                show.setShowID(resultSet.getInt("ShowID"));
                show.setScreenID(resultSet.getInt("ScreenID"));
                show.setMovieID(resultSet.getInt("MovieID"));
                show.setStartTime(resultSet.getTime("StartTime"));
                show.setEndTime(resultSet.getTime("EndTime"));
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching show by ScreenID: " + e.getMessage());
        }
        return show;
    }

    public static Show getShowByMovieID(int movieID) {
        String query = "SELECT * FROM shows WHERE MovieID = ?";
        Show show = null;
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, movieID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                show = new Show();
                show.setShowID(resultSet.getInt("ShowID"));
                show.setScreenID(resultSet.getInt("ScreenID"));
                show.setMovieID(resultSet.getInt("MovieID"));
                show.setStartTime(resultSet.getTime("StartTime"));
                show.setEndTime(resultSet.getTime("EndTime"));
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching show by ScreenID: " + e.getMessage());
        }
        return show;
    }

    public static List<Show> getAllShowByScreenID(int screenID) {
        String query = "SELECT * FROM shows WHERE ScreenID = ?";
        List<Show> shows = new ArrayList<>();
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, screenID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Show show = new Show();
                show.setShowID(resultSet.getInt("ShowID"));
                show.setScreenID(resultSet.getInt("ScreenID"));
                show.setMovieID(resultSet.getInt("MovieID"));
                show.setStartTime(resultSet.getTime("StartTime"));
                show.setEndTime(resultSet.getTime("EndTime"));
                shows.add(show);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching show by ScreenID: " + e.getMessage());
        }
        return shows;
    }


}
