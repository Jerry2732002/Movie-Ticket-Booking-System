package src.services.admin;

import src.database.TheatreDatabase;
import src.entities.Theatre;

import java.util.List;
import java.util.Scanner;

public class TheatreService {

    public static void addTheatre() {
        Scanner scanner = new Scanner(System.in);
        Theatre theatre = new Theatre();
        System.out.println("Enter Theatre Name");
        theatre.setName(scanner.nextLine());
        System.out.println("Enter Location");
        theatre.setLocation(scanner.nextLine());
        if (!TheatreDatabase.theatreExists(theatre.getName(), theatre.getLocation())){
            TheatreDatabase.addTheatre(theatre);
        }else {
            System.out.println("Theatre already exists");
        }
        System.out.println("Add 4 screen(MUST)");
        for (int i = 0; i < 4; i++) {
            ScreenService.addScreen(theatre.getTheatreID());
        }
    }

    public static void theatreService() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.println("THEATRE MENU :");
            System.out.println("Enter 'list' to list all theatres" +
                    "\nEnter 'search' to search theatre by Location" +
                    "\nEnter 'select' to select a theatre" +
                    "\nEnter 'add' to add new theatre" +
                    "\nEnter 'remove' to remove theatre" +
                    "\nEnter 'back' to go back");
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

                case "add":
                    addTheatre();
                    break;

                case "remove":
                    System.out.println("Enter TheatreID to remove");
                    int theatreID = scanner.nextInt();
                    scanner.nextLine();
                    if (TheatreDatabase.hasFutureShows(theatreID)){
                        System.out.println("This Theatre has show for future are you sure you want to delete it(y/n)");
                        if (!scanner.nextLine().equalsIgnoreCase("y")){
                            break;
                        }
                    }
                    TheatreDatabase.deleteTheatre(theatreID);
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
