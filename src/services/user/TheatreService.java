package src.services.user;

import src.database.TheatreDatabase;
import src.entities.Theatre;

import java.util.List;
import java.util.Scanner;

public class TheatreService {

    public static void theatreService() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.println("Enter 'list' to list all theatres\nEnter 'search' to search theatre by Location\nEnter 'select' to select a theatre\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "list":
                    List<Theatre> theatres = TheatreDatabase.listAllTheatres();
                    System.out.println("Theatres List:");
                    System.out.println("------------------------------------------------");
                    System.out.printf("%-5s %-30s %-50s\n", "ID", "Name", "Location");
                    System.out.println("------------------------------------------------");
                    for (Theatre theatre : theatres) {
                        System.out.printf("%-5d %-30s %-50s\n", theatre.getTheatreID(), theatre.getName(), theatre.getLocation());
                    }
                    System.out.println("------------------------------------------------");
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
                    ScreenService.screenService(theatreId);
                    break;

                case "back":
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }
}
