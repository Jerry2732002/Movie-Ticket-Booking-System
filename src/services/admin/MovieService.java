package src.services.admin;

import src.database.MoviesDatabase;
import src.entities.Movie;
import src.enums.Genre;

import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class MovieService {

    public static void movieService(Scanner scanner) {
        String choice;
        while (true) {
            System.out.println("Enter 'list' to list all movies" +
                    "\nEnter 'add' to add movie" +
                    "\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "list":
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
                    break;
                case "add":
                    System.out.println("Enter the movie name:");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    if (!MoviesDatabase.movieExistsByName(name)){
                        System.out.println("Enter the movie duration (HH:MM:SS):");
                        String durationStr = scanner.nextLine();
                        Time duration = Time.valueOf(durationStr);

                        System.out.println("Enter the genre (e.g., ACTION, COMEDY, DRAMA, FANTASY, HORROR, MYSTERY, ROMANCE, SCIENCE_FICTION, THRILLER, WESTERN, DOCUMENTARY, NA):");
                        String genreInput = scanner.nextLine().toUpperCase();

                        Genre genre;
                        try {
                            genre = Genre.valueOf(genreInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid genre entered. Defaulting to 'NA'.");
                            genre = Genre.NA;
                        }

                        System.out.println("Enter the director's name:");
                        String director = scanner.nextLine();
                        System.out.println("Enter the cast (separate names with commas if multiple):");
                        String cast = scanner.nextLine();
                        System.out.println("Enter a brief description of the movie:");
                        String description = scanner.nextLine();

                        Movie movie = new Movie(name, duration, genre.toString(), director, cast, description);
                        MoviesDatabase.addMovie(movie);
                    }else {
                        System.out.println("Movie already exists");
                    }
                    break;
                case "remove" :
                    System.out.println("Enter the movie id to remove that movie");
                    int movieID = scanner.nextInt();
                    scanner.nextLine();
                    MoviesDatabase.removeMovie(movieID);
                    break;
                case "back":
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }
}

