package src.services.admin;

import src.database.ScreenDatabase;
import src.entities.Screen;
import src.services.admin.ShowService;

import java.util.List;
import java.util.Scanner;

public class ScreenService {

    public static void screenService(int theatreId, Scanner scanner) {
        String choice;
        while (true) {
            System.out.println("SCREEN MENU:");
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
                    if (!screens.isEmpty()) {
                        for (Screen screen : screens) {
                            System.out.printf("%-10d %-10d\n", screen.getScreenID(), screen.getScreenNo());
                        }
                    } else {
                        System.out.println(" No Screens in this theatre");
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

                case "add":
                    Screen screen = new Screen();
                    System.out.println("Enter the Screen No:");
                    int screenNo = scanner.nextInt();
                    scanner.nextLine();
                    if (!ScreenDatabase.checkScreenExists(screenNo, theatreId)) {
                        screen.setScreenNo(screenNo);
                        screen.setTheatreID(theatreId);
                        ScreenDatabase.addScreen(screen);

                        System.out.println("Add seats to the screen----> Going to seats operation");
                        int screenID = ScreenDatabase.getScreenID(theatreId, screenNo);
                        SeatService.addSeats(screenID, scanner);

                        System.out.println("Mandatory for new screen to have atleast 4 shows. \nPlease Enter 4 shows");
                        for (int i = 0; i < 4; i++) {
                            ShowService.addShow(screenID, scanner);
                        }
                    } else {
                        System.out.println("Screen No. already exists");
                    }
                    break;

                case "remove":
                    System.out.println("Enter ScreenID to remove");
                    int screenID = scanner.nextInt();
                    scanner.nextLine();
                    if (ScreenDatabase.hasFutureShows(screenID)) {
                        System.out.println("This Theatre has show for future are you sure you want to delete it(y/n)");
                        if (!scanner.nextLine().equalsIgnoreCase("y")) {
                            break;
                        }
                    }
                    ScreenDatabase.deleteScreen(screenID);
                    break;

                case "back":
                    return;

                default:
                    System.out.println("Incorrect input");
            }
        }
    }
}
