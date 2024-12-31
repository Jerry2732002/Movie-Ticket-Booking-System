package src.services.admin;

import src.database.MoviesDatabase;
import src.database.ShowsDatabase;
import src.entities.Show;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Scanner;

public class ShowService {

    public static void addShow(int screenId, Scanner scanner) {
        System.out.println("Enter movie name:");
        String movieName = scanner.nextLine();
        int movieID = MoviesDatabase.getMovieIDByName(movieName);

        System.out.println("Enter start date (YYYY-MM-DD):");
        String startDateInput = scanner.nextLine();

        System.out.println("Enter start time (HH:mm):");
        String startTimeInput = scanner.nextLine();

        System.out.println("Enter end time (HH:mm):");
        String endTimeInput = scanner.nextLine();

        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date startDate = dateTimeFormat.parse(startDateInput + " " + startTimeInput);
            java.util.Date endDate = dateTimeFormat.parse(startDateInput + " " + endTimeInput);

            Timestamp startTimestamp = new Timestamp(startDate.getTime());
            Timestamp endTimestamp = new Timestamp(endDate.getTime());

            if (isShowOverlapping(screenId, startTimestamp, endTimestamp)) {
                System.err.println("The new show overlaps with an existing show on the same screen please try again with correct time");
                return;
            }

            Show newShow = new Show();
            newShow.setScreenID(screenId);
            newShow.setMovieID(movieID);
            newShow.setStartTime(startTimestamp);
            newShow.setEndTime(endTimestamp);
            newShow.setDate(new Date(startTimestamp.getTime()));

            ShowsDatabase.addShow(newShow);
            System.out.println("Show added successfully.");

        } catch (ParseException e) {
            System.out.println("Invalid date/time format. Please try again.");
        }
    }

    private static boolean isShowOverlapping(int screenId, Timestamp newStartTime, Timestamp newEndTime) {
        List<Show> existingShows = ShowsDatabase.getAllShowByScreenID(screenId);

        for (Show existingShow : existingShows) {
            Timestamp existingStartTime = existingShow.getStartTime();
            Timestamp existingEndTime = existingShow.getEndTime();

            if (newStartTime.before(existingEndTime) && newEndTime.after(existingStartTime)) {
                return true;
            }
        }
        return false;
    }

    public static void removeShow(Scanner scanner) {
        System.out.println("Enter ShowID to remove:");
        int showID = Integer.parseInt(scanner.nextLine());

        System.out.println("Are you sure you want to remove ShowID: " + showID + " (yes/no)?");
        String confirmation = scanner.nextLine().toLowerCase();

        if (confirmation.equals("yes")) {
            ShowsDatabase.deleteShowByShowID(showID);
        } else {
            System.out.println("Show removal canceled.");
        }
    }

    public static void showService(int screenId, Scanner scanner) {
        String choice;
        while (true) {
            System.out.println("SHOW MENU :");
            System.out.println("Enter 'list' to list all shows\n" +
                    "Enter 'add' to add a show\n" +
                    "Enter 'remove' to remove a show" +
                    "Enter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "list":
                    src.services.user.ShowService.listShows(screenId);
                    break;

                case "add":
                        addShow(screenId,scanner);
                    break;

                case "remove":
                    removeShow(screenId,scanner);
                    break;
                case "back":
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }
}
