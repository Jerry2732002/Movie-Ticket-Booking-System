package src.services.user;

import src.database.TheatreDatabase;
import src.entities.Theatre;
import src.services.user.ScreenService;

import java.util.List;
import java.util.Scanner;

public class TheatreService {

    public static void theatreService(String movieName, Scanner scanner) {
        String choice;
        while (true) {
            System.out.println("THEATRE MENU :");
            System.out.println("Enter 'list' to list all theatres\nEnter 'search' to search theatre by Location\nEnter 'select' to select a theatre\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "list":
                    List<Theatre> theatres = TheatreDatabase.listAllTheatresWithMovie(movieName);
                    System.out.println("Theatres List:");
                    System.out.println("----------------------------------------------------------------------------------------");
                    System.out.printf("%-5s %-30s %-30s %-10s\n", "ID", "Name", "Location", "Screen No.");
                    System.out.println("----------------------------------------------------------------------------------------");
                    for (Theatre theatre : theatres) {
                        System.out.printf("%-5d %-30s %-30s %-10s\n", theatre.getTheatreID(), theatre.getName(), theatre.getLocation(), theatre.getScreenNo());
                    }
                    System.out.println("----------------------------------------------------------------------------------------");
                    break;

                case "search":
                    System.out.println("Enter theatre location to search");
                    scanner.nextLine();
                    List<Theatre> theatresByLocation = TheatreDatabase.getTheatresByLocation(scanner.nextLine());
                    System.out.println("Theatres List:");
                    System.out.println("------------------------------------------------");
                    System.out.printf("%-5s %-30s %-50s\n", "ID", "Name", "Location");
                    System.out.println("------------------------------------------------");
                    for (Theatre theatre : theatresByLocation) {
                        System.out.printf("%-5d %-30s %-50s\n", theatre.getTheatreID(), theatre.getName(), theatre.getLocation());
                    }
                    System.out.println("------------------------------------------------");
                    break;

                case "select":
                    System.out.println("Enter the TheatreID to select that theatre");
                    int theatreId = scanner.nextInt();
                    if (TheatreDatabase.checkTheatreExist(theatreId)) {
                        ScreenService.screenService(theatreId, scanner);
                    } else {
                        System.out.println("Theater with ID:" + theatreId + " does not exist check again");
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
