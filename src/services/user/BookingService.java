package src.services.user;

import src.database.BookingDatabase;
import src.entities.Seat;

import java.util.List;
import java.util.Scanner;

public class BookingService {
    public static void bookingService(int showID) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.println("Enter 'list' to list all available seats\nEnter 'select' to select a seat\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "list":
                    List<Seat> seats = BookingDatabase.getAvailableSeats(showID);
                    for (int i = 0; i < ScreenService.noOfRows; i++) {

                    }
                case "select":
                    System.out.println("Enter the SeatNo to select that screen");
                    int seatNo = scanner.nextInt();
                    break;

                case "back":
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }
}
