package src.database;

import src.entities.Movie;
import src.entities.MovieDetail;
import src.enums.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoviesDatabase {

    public static List<Movie> listAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies";

        try (Connection connection = CreateConnection.getConnection();
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
        String query = "INSERT INTO movies (Name, Duration, Genre, Director, Cast, Description) VALUES (?,?,?,?,?,?)\n";
        try (Connection connection = CreateConnection.getConnection();
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
        String query = "DELETE FROM movies WHERE MovieID = ?";
        try (Connection connection = CreateConnection.getConnection();
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


    public static int getMovieIDByName(String name) {
        int id = 0;
        String query = "SELECT * FROM movies WHERE Name = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            } else {
                System.err.println("No movie by name :" + name + " found in database");
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static boolean movieExistsByName(String name) {
        String query = "SELECT 1 FROM movies WHERE Name = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Error while checking if the movie exists: " + e.getMessage());
        }
    }

    public static List<MovieDetail> listAllMoviesDetails() {
        List<MovieDetail> moviesDetails = new ArrayList<>();
        String query = "SELECT m.Name AS MovieName,Duration,Genre,Cast,Director,Description,t.Name AS TheatreName, Location,ScreenNo,StartTime\n" +
                "FROM movies m \n" +
                "JOIN shows sh ON m.MovieID = sh.MovieID\n" +
                "JOIN screens s ON s.ScreenID = sh.ScreenID\n" +
                "JOIN theatres t ON t.TheatreID = s.TheatreID\n" +
                "WHERE sh.StartTime > NOW();";

        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                MovieDetail movieDetail = new MovieDetail(resultSet.getString("MovieName"), resultSet.getTime("Duration"), resultSet.getString("Genre"),
                        resultSet.getString("Cast"), resultSet.getString("Director"), resultSet.getString("Description"), resultSet.getString("TheatreName"),
                        resultSet.getString("Location"), resultSet.getInt("ScreenNo"), resultSet.getTimestamp("StartTime"));
                moviesDetails.add(movieDetail);
            }
        } catch (SQLException e) {
            System.err.println("Error while listing movies: " + e.getMessage());
        }
        return moviesDetails;
    }

    public static List<MovieDetail> searchMoviesDetails(String movieName) {
        List<MovieDetail> moviesDetails = new ArrayList<>();
        String query = "SELECT m.Name AS MovieName,Duration,Genre,Cast,Director,Description,t.Name AS TheatreName, Location,ScreenNo,StartTime\n" +
                "FROM movies m \n" +
                "JOIN shows sh ON m.MovieID = sh.MovieID\n" +
                "JOIN screens s ON s.ScreenID = sh.ScreenID\n" +
                "JOIN theatres t ON t.TheatreID = s.TheatreID\n" +
                "WHERE sh.StartTime > NOW(); AND m.Name = ?";

        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, movieName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MovieDetail movieDetail = new MovieDetail(resultSet.getString("MovieName"), resultSet.getTime("Duration"), resultSet.getString("Genre"),
                        resultSet.getString("Cast"), resultSet.getString("Director"), resultSet.getString("Description"), resultSet.getString("TheatreName"),
                        resultSet.getString("Location"), resultSet.getInt("ScreenNo"), resultSet.getTimestamp("StartTime"));
                moviesDetails.add(movieDetail);
            }
        } catch (SQLException e) {
            System.err.println("Error while listing movies: " + e.getMessage());
        }
        return moviesDetails;
    }
}