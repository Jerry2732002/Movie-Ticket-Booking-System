import src.services.admin.AdminService;
import src.services.admin.SeatService;
import src.services.user.UserService;
import java.util.Scanner;


public class Main {
    public static void main(String[] args){
//        Scanner scanner = new Scanner(System.in);
//        String choice;
//        do {
//            System.out.println("Choose role : Enter 'admin' or 'user'. Enter 'exit' to stop");
//            choice = scanner.next();
//            switch (choice.toLowerCase()) {
//                case "admin":
//                    AdminService.authenticateAdmin(scanner);
//                    break;
//                case "user":
//                    System.out.println("Enter 'login' to login\nEnter 'register' to register");
//                    switch (scanner.next().toLowerCase()) {
//                        case "login":
//                            UserService.authenticateUser(scanner);
//                            break;
//                        case "register":
//                            UserService.register(scanner);
//                            break;
//                    }
//                    break;
//                case "exit":
//                    break;
//                default:
//                    System.out.println("Incorrect input");
//            }
//        } while (!choice.equalsIgnoreCase("exit"));
//        scanner.close();
        SeatService.addSeats(1,new Scanner(System.in));
    }
}
