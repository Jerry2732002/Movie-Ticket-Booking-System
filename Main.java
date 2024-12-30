import src.services.admin.AdminService;
import src.services.user.UserService;
import java.util.Scanner;


public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Choose role : Enter 'admin' or 'user'. Enter 'exit' to stop");
            choice = scanner.next();
            switch (choice.toLowerCase()) {
                case "admin":
                    AdminService.authenticateAdmin();
                    break;
                case "user":
                    System.out.println("Enter 'login' to login\nEnter 'register' to register");
                    switch (scanner.next().toLowerCase()) {
                        case "login":
                            UserService.authenticateUser();
                            break;
                        case "register":
                            UserService.register();
                            break;
                    }
                    break;
                case "exit":
                    scanner.close();
                    break;
                default:
                    System.out.println("Incorrect input");
            }
        } while (!choice.equalsIgnoreCase("exit"));
    }
}
