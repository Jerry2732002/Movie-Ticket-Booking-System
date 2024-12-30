package src.services.admin;

import src.database.AdminDatabase;

import java.util.Scanner;

public class AdminService {

    public static void adminServices() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 'theatre' to go to theatre operations\nEnter 'back' to go back");
        switch (scanner.next().toLowerCase()) {
            case "theatre":
                TheatreService.theatreService();
                break;
            case "back":
                scanner.close();
                break;
            default:
                System.out.println("Incorrect Input");
        }
    }

    public static void authenticateAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Admin name");
        String adminName = scanner.next();
        System.out.println("Enter Password");
        String password = scanner.next();
        if (AdminDatabase.authenticateAdmin(adminName, password)) {
            adminServices();
        } else {
            System.err.println("Incorrect Admin Name or Password");
        }
        scanner.close();
    }
}
