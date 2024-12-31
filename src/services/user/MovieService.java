package src.services.user;

import src.database.MoviesDatabase;
import src.entities.Movie;
import src.entities.MovieDetail;

import java.util.List;
import java.util.Scanner;

public class MovieService {
    public static void listAllMovies() {
        List<MovieDetail> moviesDetails = MoviesDatabase.listAllMoviesDetails();

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.printf("%-30s %-12s %-20s %-25s %-25s %-30s %-20s %-10s %-15s %-10s\n",
                "Movie Name", "Duration", "Genre", "Director", "Description", "Theatre Name", "Location", "Screen No", "Date", "Time");
        System.out.println("--------------------------------------------------------------------------------------------");
        if (!moviesDetails.isEmpty()) {
            for (MovieDetail movie : moviesDetails) {
                System.out.printf("%-30s %-12s %-20s %-25s %-25s %-30s %-20s %-10d %-15s %-10s\n",
                        movie.getName(),
                        movie.getDuration(),
                        movie.getGenre(),
                        movie.getDirector(),
                        movie.getDescription(),
                        movie.getTheatreName(),
                        movie.getLocation(),
                        movie.getScreenNo(),
                        movie.getDate() != null ? movie.getDate().toString() : "N/A",
                        movie.getTime() != null ? movie.getTime().toString() : "N/A"
                );
            }
        }else {
            System.out.println("No movies available");
        }
        System.out.println("--------------------------------------------------------------------------------------------");
    }

    public static void movieService(Scanner scanner) {
        String choice;
        while (true) {
            System.out.println("Enter 'list' to list all movies\nEnter 'search' to find movie shows\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "list":
                    listAllMovies();
                    break;
                case "search":
                    System.out.println("Enter the movie name");
                    String name = scanner.nextLine();

                    MoviesDatabase.searchMoviesDetails(name);
                    break;
                case "back":
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }
}
