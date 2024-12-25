package src.services;

import java.util.Scanner;

public class UserService {
    public static void userService() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.println("Enter 'movies' to go to movie operation\nEnter 'theatre' to go to theatre operation\nEnter 'booking' to go to booking operation\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "movies" : MovieServices.movieServices();
                case "theatres" : break;
                case "bookings" : break;
                case "back" : return;
                default :
                    System.out.println("Incorrect input");
            }
        }
    }
}
