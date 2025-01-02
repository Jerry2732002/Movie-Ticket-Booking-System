package src.services.user;

import src.database.ScreenDatabase;
import src.database.ShowsDatabase;
import src.entities.Movie;
import src.entities.Screen;
import src.entities.Show;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ShowService {

    public static void listShows(int screenId) {
        List<Show> shows = ShowsDatabase.getAllShowByScreenID(screenId);
        System.out.println("Shows List for Screen ID: " + screenId);
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%-10s %-30s %-15s %-15s %-12s\n", "ShowID", "Movie Name", "Date", "StartTime", "EndTime");
        System.out.println("-----------------------------------------------------------------------------------");

        LocalDateTime currentDateTime = LocalDateTime.now();

        boolean foundFutureShow = false;

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Show show : shows) {
            Timestamp startTime = show.getStartTime();
            LocalDateTime showStartDateTime = startTime.toLocalDateTime();

            if (showStartDateTime.isAfter(currentDateTime) || showStartDateTime.isEqual(currentDateTime)) {
                String formattedDate = showStartDateTime.format(dateFormatter);
                String formattedStartTime = showStartDateTime.format(timeFormatter);
                LocalDateTime showEndDateTime = show.getEndTime().toLocalDateTime();
                String formattedEndTime = showEndDateTime.format(timeFormatter);

                System.out.printf("%-10d %-30s %-15s %-15s %-12s\n",
                        show.getShowID(),
                        show.getMovie().getName(),
                        formattedDate,
                        formattedStartTime,
                        formattedEndTime
                );
                foundFutureShow = true;
            }
        }
        if (!foundFutureShow) {
            System.out.println("No upcoming shows available.");
        }

        System.out.println("-----------------------------------------------------------------------------------");
    }


    public static void showService(int screenId,Scanner scanner) {
        String choice;
        while (true) {
            System.out.println("SHOW MENU :");
            System.out.println("Enter 'list' to list all shows\nEnter 'select' to select a show\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "list":
                    listShows(screenId);
                    break;

                case "select":
                    System.out.println("Enter the ShowID to select that show");
                    int showID = scanner.nextInt();

                    System.out.println("Would you like to see the details of the movie(y/n)");
                    if (scanner.next().equalsIgnoreCase("y")) {
                        System.out.println("--------------------------------------------------------------------------------------------");
                        System.out.printf("%-5s %-30s %-12s %-20s %-25s\n", "ID", "Name", "Duration", "Genre", "Director");
                        System.out.println("--------------------------------------------------------------------------------------------");
                        Show show = ShowsDatabase.getShowByShowID(showID);
                        if (show != null) {
                            Movie movie = show.getMovie();
                            System.out.printf(
                                    "%-5d %-30s %-12s %-20s %-25s\n",
                                    movie.getMovieID(),
                                    movie.getName(),
                                    movie.getDuration(),
                                    movie.getGenre(),
                                    movie.getDirector()
                            );
                        }else {
                            System.out.println("No show found with id : " + showID);
                        }
                    }
                    System.out.println("Enter 'book' to book the show");
                    if (scanner.next().equalsIgnoreCase("book")) {
                        SeatService.seatService(showID,scanner);
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
