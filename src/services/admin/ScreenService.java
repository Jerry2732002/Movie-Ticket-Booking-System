package src.services.admin;

import src.database.ScreenDatabase;
import src.entities.Screen;
import src.services.user.ShowService;

import java.util.List;
import java.util.Scanner;

public class ScreenService {

    public static void addScreen(int theatreId) {
        Scanner scanner = new Scanner(System.in);
        Screen screen = new Screen();

        System.out.println("Enter the Screen No:");
        int screenNo = scanner.nextInt();
        scanner.nextLine();
        if (!ScreenDatabase.checkScreenExists(screenNo, theatreId)) {
            screen.setScreenNo(screenNo);
            screen.setTheatreID(theatreId);
            ScreenDatabase.addScreen(screen);
        } else {
            System.out.println("Screen No. already exists");
        }
    }

    public static void screenService(int theatreId) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.println("SCREEN MENU :");
            System.out.println("Enter 'list' to list all screens" +
                    "\nEnter 'select' to select a screen" +
                    "\nEnter 'add' to add a screen" +
                    "\nEnter 'remove' to remove screen" +
                    "\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "list":
                    List<Screen> screens = ScreenDatabase.listAllScreens(theatreId);
                    System.out.println("Screens List for Theatre ID: " + theatreId);
                    System.out.println("------------------------------------------------");
                    System.out.printf("%-10s %-10s\n", "ScreenID", "ScreenNo");
                    System.out.println("------------------------------------------------");
                    for (Screen screen : screens) {
                        System.out.printf("%-10d %-10d\n", screen.getScreenID(), screen.getScreenNo());
                    }
                    System.out.println("------------------------------------------------");
                    break;

                case "select":
                    System.out.println("Enter the ScreenID to select that screen");
                    int screenId = scanner.nextInt();
                    ShowService.showService(screenId);
                    break;

                case "add":
                    addScreen(theatreId);
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
