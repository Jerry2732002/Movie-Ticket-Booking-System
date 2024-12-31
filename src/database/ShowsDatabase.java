package src.database;

import src.entities.Movie;
import src.entities.Show;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowsDatabase {
    public static void addShow(Show show) {
        String query = "INSERT INTO shows (MovieID, StartTime, EndTime, Date) VALUES (?,?,?,?)";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, show.getScreenID());
            statement.setInt(2, show.getMovieID());
            statement.setTimestamp(3, show.getStartTime());
            statement.setTimestamp(4, show.getEndTime());
            statement.setDate(5, show.getDate());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Show successfully added");
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
                System.out.println("No show with ShowID:" + showId);
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting show: " + e.getMessage());
        }
    }


    public static List<Show> getAllShowByScreenID(int screenID) {
        String query = "SELECT * FROM shows JOIN movies ON shows.MovieID = movies.MovieID WHERE ScreenID = ?";
        List<Show> shows = new ArrayList<>();
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, screenID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Show show = new Show(resultSet.getInt("ShowID"),resultSet.getInt("ScreenID"),resultSet.getInt("MovieID"),
                        resultSet.getTimestamp("StartTime"),resultSet.getTimestamp("EndTime"));
                Movie movie = new Movie(resultSet.getInt("MovieID"),resultSet.getString("Name"),resultSet.getTime("Duration")
                        ,resultSet.getString("Genre"),resultSet.getString("Director"),resultSet.getString("Cast"),resultSet.getString("Description"));
                show.setMovie(movie);
                shows.add(show);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching show by ScreenID: " + e.getMessage());
        }
        return shows;
    }

    public static Show getShowByShowID(int showId) {
        String query = "SELECT * FROM shows JOIN movies ON shows.MovieID = movies.MovieID WHERE ShowID = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, showId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Show show = new Show(resultSet.getInt("ShowID"),resultSet.getInt("ScreenID"),resultSet.getInt("MovieID"),
                        resultSet.getTimestamp("StartTime"),resultSet.getTimestamp("EndTime"));
                Movie movie = new Movie(resultSet.getInt("MovieID"),resultSet.getString("Name"),resultSet.getTime("Duration")
                        ,resultSet.getString("Genre"),resultSet.getString("Director"),resultSet.getString("Cast"),resultSet.getString("Description"));
                show.setMovie(movie);
                return show;
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching show by show Id: " + e.getMessage());
        }
        return null;
    }

    public static int getNoOfBooking(int showID) {
        String query = "SELECT COUNT(*) FROM shows sh \n" +
                "JOIN bookings b ON sh.ShowID = b.ShowID\n" +
                "WHERE sh.ShowID = ? AND b.Status != 'CANCELLED'";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, showID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                System.out.println("No show with ID" + showID);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching show by ScreenID: " + e.getMessage());
        }
        return -1;
    }

    public static int getNoOfShows(int screenID) {
        String query = "SELECT COUNT(*) FROM shows\n" +
                "WHERE ScreenID = ?;";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, screenID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                System.out.println("No show with ID" + screenID);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching show by ScreenID: " + e.getMessage());
        }
        return -1;
    }

    public static boolean updateShow(Show show) {
        String query = "UPDATE shows SET StartTime = ?, EndTime = ?, Date = ? WHERE ShowID = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, show.getStartTime());
            statement.setTimestamp(2, show.getEndTime());
            statement.setDate(3, show.getDate());
            statement.setInt(4, show.getShowID());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error while updating show: " + e.getMessage());
        }
        return false;
    }
}
