package src.services.user;

import src.database.ScreenDatabase;
import src.entities.Screen;

import java.util.List;
import java.util.Scanner;

public class ScreenService {
    public static void screenService(int theatreId, Scanner scanner) {

        String choice;
        while (true) {
            System.out.println("SCREEN MENU :");
            System.out.println("Enter 'list' to list all screens\nEnter 'select' to select a screen\nEnter 'back' to go back");
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
                    if (ScreenDatabase.checkScreenExists(screenId)) {
                        ShowService.showService(screenId, scanner);
                    } else {
                        System.out.println("Screen with ID:" + screenId + " does not exist check again");
                    }
                    break;

                case "back":
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }
}
