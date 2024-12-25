package src.database;

import src.entities.Movie;
import src.enums.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoviesDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/ticket_booking";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Jerry@686670";

    public static List<Movie> listAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM Movies";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setMovieID(resultSet.getInt("MovieID"));
                movie.setName(resultSet.getString("Name"));
                movie.setDuration(resultSet.getTime("Duration"));
                movie.setGenre(Genre.valueOf(resultSet.getString("Genre")));
                movie.setDirector(resultSet.getString("Director"));
                movie.setCast(resultSet.getString("Cast"));
                movie.setDescription(resultSet.getString("Description"));
                movies.add(movie);
            }

        } catch (SQLException e) {
            System.err.println("Error while listing movies: " + e.getMessage());
        }

        return movies;
    }

    public static void addMovie(Movie movie) {
        String query = "INSERT INTO Movies (Name, Duration, Genre, Director, Cast, Description) VALUES (?,?,?,?,?,?)\n";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, movie.getName());
            statement.setTime(2, movie.getDuration());
            statement.setString(3, movie.getGenre());
            statement.setString(4, movie.getDirector());
            statement.setString(5, movie.getCast());
            statement.setString(6, movie.getDescription());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie was successfully added.");
            } else {
                System.out.println("Failed to add new movie");
            }
        } catch (SQLException e) {
            System.err.println("Error while listing movies: " + e.getMessage());
        }
    }

    public static void removeMovie(int movieID) {
        String query = "DELETE FROM Movies WHERE MovieID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, movieID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie with ID " + movieID + " was successfully removed.");
            } else {
                System.out.println("No movie found with ID " + movieID + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error while removing the movie: " + e.getMessage());
        }
    }

    public static Movie findMovieByName(String name) {
        Movie movie = new Movie();
        String query = "SELECT * FROM Movies WHERE Name = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                movie.setMovieID(resultSet.getInt(1));
                movie.setName(resultSet.getString(2));
                movie.setDuration(resultSet.getTime(3));
                movie.setGenre(Genre.valueOf(resultSet.getString(4)));
                movie.setDirector(resultSet.getString(5));
                movie.setCast(resultSet.getString(6));
                movie.setDescription(resultSet.getString(7));
            }
            return movie;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static boolean movieExistsByName(String name) {
        String query = "SELECT 1 FROM Movies WHERE Name = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Error while checking if the movie exists: " + e.getMessage());
        }
    }

}