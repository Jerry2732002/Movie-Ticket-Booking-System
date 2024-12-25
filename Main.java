import src.database.MoviesDatabase;
import src.entities.Movie;
import src.enums.Genre;
import src.services.MovieServices;
import src.services.UserService;

import java.sql.Time;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Choose role : Enter 'admin' or 'user'. Enter 'exit' to stop");
            choice = scanner.next();
            switch (choice.toLowerCase()) {
                case "admin":
                    break;
                case "user":
                    UserService.userService();
                    break;
                case "exit": break;
                default:
                    System.out.println("Incorrect input");
            }
        } while (!choice.equalsIgnoreCase("exit"));
    }
}
