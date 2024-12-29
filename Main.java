
import src.database.CreateConnection;
import src.services.user.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Choose role : Enter 'admin' or 'user'. Enter 'exit' to stop");
            choice = scanner.next();
            switch (choice.toLowerCase()) {
                case "admin":
                    break;
                case "user":
                    UserService.authenticateUser();
                    break;
                case "exit": break;
                default:
                    System.out.println("Incorrect input");
            }
        } while (!choice.equalsIgnoreCase("exit"));
    }
}
