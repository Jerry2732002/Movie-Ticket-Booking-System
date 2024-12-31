package src.services.admin;

import src.database.AdminDatabase;

import java.util.Scanner;

public class AdminService {

    public static void adminServices(Scanner scanner) {
        while (true) {
            System.out.println("Enter 'theatre' to go to theatre operations" +
                    "\nEnter 'movie' to go to movies operation" +
                    "\nEnter 'back' to go back");
            switch (scanner.next().toLowerCase()) {
                case "theatre":
                    TheatreService.theatreService(scanner);
                    break;

                case "movie":
                    MovieService.movieService(scanner);
                    break;

                case "back":
                    return;
                default:
                    System.out.println("Incorrect Input");
            }
        }
    }

    public static void authenticateAdmin(Scanner scanner) {
        System.out.println("Enter Admin name");
        String adminName = scanner.next();
        System.out.println("Enter Password");
        String password = scanner.next();
        if (AdminDatabase.authenticateAdmin(adminName, password)) {
            adminServices(scanner);
        } else {
            System.err.println("Incorrect Admin Name or Password");
        }
    }
}
