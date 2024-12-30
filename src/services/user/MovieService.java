package src.services.user;

import src.database.MoviesDatabase;
import src.entities.Movie;

import java.util.List;
import java.util.Scanner;

public class MovieService {
    public static void listAllMovies() {
        List<Movie> movies = MoviesDatabase.listAllMovies();
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.printf("%-5s %-30s %-12s %-20s %-25s\n", "ID", "Name", "Duration", "Genre", "Director");
        System.out.println("--------------------------------------------------------------------------------------------");
        for (Movie movie : movies) {
            System.out.printf(
                    "%-5d %-30s %-12s %-20s %-25s\n",
                    movie.getMovieID(),
                    movie.getName(),
                    movie.getDuration(),
                    movie.getGenre(),
                    movie.getDirector()
            );
        }
        System.out.println("--------------------------------------------------------------------------------------------");
    }

    public static void movieService() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.println("Enter 'list' to list all movies\nEnter 'book' to book movie\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "list":
                    listAllMovies();
                    break;
                case "book":

                    break;
                case "back":
                    scanner.close();
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }
}
