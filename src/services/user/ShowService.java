package src.services.user;

import src.database.ScreenDatabase;
import src.database.ShowsDatabase;
import src.entities.Movie;
import src.entities.Screen;
import src.entities.Show;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ShowService {
    public static void showService(int screenId) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.println("Enter 'list' to list all shows\nEnter 'select' to select a show\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "list":
                    List<Show> shows = ShowsDatabase.getAllShowByScreenID(screenId);
                    System.out.println("Shows List for Screen ID: " + screenId);
                    System.out.println("------------------------------------------------");
                    System.out.printf("%-10s %-30s %-15s %-12s %-12s\n", "ShowID", "Movie Name", "StartTime", "EndTime", "Date");
                    System.out.println("------------------------------------------------");

                    LocalDateTime currentDateTime = LocalDateTime.now();

                    boolean foundFutureShow = false;

                    for (Show show : shows) {
                        LocalDateTime showStartDateTime = LocalDateTime.of(show.getDate().toLocalDate(), show.getStartTime().toLocalTime());

                        if (showStartDateTime.isAfter(currentDateTime) || showStartDateTime.isEqual(currentDateTime)) {
                            System.out.printf("%-10d %-30s %-15s %-12s %-12s\n",
                                    show.getShowID(),
                                    show.getMovie().getName(),
                                    show.getStartTime().toString(),
                                    show.getEndTime().toString(),
                                    show.getDate().toString()
                            );
                            foundFutureShow = true;
                        }
                    }
                    if (!foundFutureShow) {
                        System.out.println("No upcoming shows available.");
                    }

                    System.out.println("------------------------------------------------");
                    break;

                case "select":
                    System.out.println("Enter the ShowID to select that show");
                    int showID = scanner.nextInt();
                    System.out.println("Enter 'details' if you like display of the details of the movie");
                    if (scanner.next().equalsIgnoreCase("details")) {
                        System.out.println("--------------------------------------------------------------------------------------------");
                        System.out.printf("%-5s %-30s %-12s %-20s %-25s\n", "ID", "Name", "Duration", "Genre", "Director");
                        System.out.println("--------------------------------------------------------------------------------------------");
                        Movie movie = ShowsDatabase.getShowByShowID(showID).getMovie();
                        System.out.printf(
                                "%-5d %-30s %-12s %-20s %-25s\n",
                                movie.getMovieID(),
                                movie.getName(),
                                movie.getDuration(),
                                movie.getGenre(),
                                movie.getDirector()
                        );
                    }
                    System.out.println("Enter 'book' to book the show");
                    if (scanner.next().equalsIgnoreCase("book")) {
                        BookingService.bookingService(showID);
                    }
                    break;
                case "back":
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }
}
