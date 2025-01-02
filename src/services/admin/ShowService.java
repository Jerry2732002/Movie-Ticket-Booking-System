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
        while (true) {
            scanner.nextLine();
            System.out.println("Enter movie name:");
            String movieName = scanner.nextLine();
            if (!MoviesDatabase.movieExistsByName(movieName)) {
                System.out.println("Movie not found in database");
                continue;
            }
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
                    continue;
                }

                Show newShow = new Show();
                newShow.setScreenID(screenId);
                newShow.setMovieID(movieID);
                newShow.setStartTime(startTimestamp);
                newShow.setEndTime(endTimestamp);
                newShow.setDate(new Date(startTimestamp.getTime()));

                ShowsDatabase.addShow(newShow);
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date/time format. Please try again.");
            }
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

    public static void removeShow(int screenId, Scanner scanner) {
        System.out.println("Enter ShowID to remove:");
        scanner.nextLine();
        int showID = Integer.parseInt(scanner.nextLine());

        if (ShowsDatabase.checkShowExists(showID)) {
            if (ShowsDatabase.getNoOfShows(screenId) > 4) {
                System.out.println("There are " + ShowsDatabase.getNoOfBooking(showID) + " booking for this show");
                System.out.println("Are you sure you want to remove ShowID: " + showID + " (y/n)");
                String confirmation = scanner.nextLine().toLowerCase();

                if (confirmation.equalsIgnoreCase("y")) {
                    ShowsDatabase.deleteShowByShowID(showID);
                } else {
                    System.out.println("Show removal cancelled.");
                }
            } else {
                System.out.println("Cannot delete show as only 4 shows left");
            }
        } else {
            System.out.println("Show with ID:" + showID + " does not exist check again");
        }
    }

    public static void updateShow(int screenId, Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter ShowID to update:");
        int showID = Integer.parseInt(scanner.nextLine());

        Show existingShow = ShowsDatabase.getShowByShowID(showID);
        if (existingShow == null) {
            System.out.println("ShowID not found.");
            return;
        }

        System.out.println("Existing Show Details:");
        System.out.println("Movie: " + existingShow.getMovie().getName());
        System.out.println("Start Time: " + existingShow.getStartTime());
        System.out.println("End Time: " + existingShow.getEndTime());

        System.out.println("Enter new start date (YYYY-MM-DD):");
        String startDateInput = scanner.nextLine();
        System.out.println("Enter new start time (HH:mm):");
        String startTimeInput = scanner.nextLine();
        System.out.println("Enter new end time (HH:mm):");
        String endTimeInput = scanner.nextLine();

        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date startDate = dateTimeFormat.parse(startDateInput + " " + startTimeInput);
            java.util.Date endDate = dateTimeFormat.parse(startDateInput + " " + endTimeInput);

            Timestamp newStartTimestamp = new Timestamp(startDate.getTime());
            Timestamp newEndTimestamp = new Timestamp(endDate.getTime());

            if (isShowOverlapping(screenId, newStartTimestamp, newEndTimestamp)) {
                System.err.println("The new show time overlaps with an existing show. Please try again.");
                return;
            }

            existingShow.setStartTime(newStartTimestamp);
            existingShow.setEndTime(newEndTimestamp);
            existingShow.setDate(new Date(newStartTimestamp.getTime()));

            if (ShowsDatabase.updateShow(existingShow)) {
                System.out.println("Show updated successfully.");
            } else {
                System.err.println("Failed to update the show.");
            }
        } catch (ParseException e) {
            System.out.println("Invalid date/time format. Please try again.");
        }
    }


    public static void showService(int screenId, Scanner scanner) {
        String choice;
        while (true) {

            System.out.println("SHOW MENU :");
            System.out.println("Enter 'list' to list all shows\n" +
                    "Enter 'add' to add a show\n" +
                    "Enter 'remove' to remove a show\n" +
                    "Enter 'update' to update a show" +
                    "Enter 'back' to go back");

            choice = scanner.next().toLowerCase();

            switch (choice) {
                case "list":
                    src.services.user.ShowService.listShows(screenId);
                    break;

                case "add":
                    addShow(screenId, scanner);
                    break;

                case "remove":
                    removeShow(screenId, scanner);
                    break;

                case "update":
                    updateShow(screenId, scanner);
                    break;

                case "back":
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }
}
