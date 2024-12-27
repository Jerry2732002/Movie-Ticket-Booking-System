package src.services.user;

import src.database.UserDatabase;

import java.util.Scanner;

public class UserService {
    public static int userId;
    public static void userService() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.println("Enter 'movies' to go to movie operation\nEnter 'theatre' to go to theatre operation\nEnter 'booking' to go to booking operation\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "movies" : MovieService.movieService(); break;
                case "theatres" : TheatreService.theatreService(); break;
                case "bookings" : break;
                case "back" : return;
                default :
                    System.out.println("Incorrect input");
            }
        }
    }
    public static void authenticateUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username");
        String username = scanner.next();
        System.out.println("Enter password");
        String password = scanner.next();
        if (UserDatabase.authenticateUser(username,password)) {
            UserService.userId = UserDatabase.getUserID(username);
            userService();
        }else {
            System.err.println("Incorrect username or password");
        }
    }
}