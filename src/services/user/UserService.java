package src.services.user;

import src.database.UserDatabase;
import src.entities.User;

import java.util.Scanner;

public class UserService {
    public static int userId;

    public static void userService() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.println("USER MENU :");
            System.out.println("Enter 'theatre' to go to theatre operation\nEnter 'booking' to go to booking operation\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "theatre":
                    TheatreService.theatreService();
                    break;
                case "booking":
                    BookingService.bookingService();
                    break;
                case "back":
                    scanner.close();
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }

    public static void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Username");
        String username = scanner.next();
        if (!UserDatabase.userExists(username)) {
            User user = new User();
            user.setUsername(username);
            System.out.println("Enter Password");
            user.setPassword(scanner.next());
            System.out.println("Enter First Name");
            user.setFirstName(scanner.next());
            System.out.println("Enter Last Name");
            user.setLastName(scanner.next());
            UserDatabase.addUser(user);
        } else {
            System.out.println("Username already exists try another name");
            register();
        }
        scanner.close();
    }

    public static void authenticateUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username");
        String username = scanner.next();
        System.out.println("Enter password");
        String password = scanner.next();
        if (UserDatabase.authenticateUser(username, password)) {
            UserService.userId = UserDatabase.getUserID(username);
            userService();
        } else {
            System.err.println("Incorrect username or password");
        }
        scanner.close();
    }
}