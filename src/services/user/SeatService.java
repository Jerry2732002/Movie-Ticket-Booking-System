package src.services.user;

import src.database.BookingDatabase;
import src.database.SeatsDatabase;
import src.entities.Seat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SeatService {

    public static void seatService(int showID) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.println("SEAT MENU :");
            System.out.println("Enter 'list' to list all available seats\nEnter 'select' to select a seat\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "list":
                    Map<String, List<Seat>> availableSeats = BookingDatabase.getAvailableSeats(showID);

                    for (Map.Entry<String, List<Seat>> entry : availableSeats.entrySet()) {
                        String row = entry.getKey();
                        List<Seat> seatsInRow = entry.getValue();
                        StringBuilder seatRow = new StringBuilder();
                        for (Seat seat : seatsInRow) {
                            seatRow.append(seat.getSeatNo()).append(" ");
                        }

                        if (!seatsInRow.isEmpty()) {
                            Seat firstSeat = seatsInRow.get(0);
                            seatRow.append(String.format("[%s %.2f/-]", firstSeat.getCategory(), firstSeat.getPrice()));
                        }
                        System.out.println(seatRow);
                    }
                    break;
                case "select":
                    System.out.println("Enter the SeatNo to select that seat to book");
                    String seatNo = scanner.next();
                    int seatID = SeatsDatabase.getSeatIDBySeatNo(seatNo);
                    if (seatID > 0) {
                        System.out.println("Are you sure to book, Enter 'y' for confirmation");
                        if (scanner.next().equalsIgnoreCase("y")){
                            BookingService.bookTicket(showID,seatID);
                        }
                    }else {
                        System.out.println("No seat with Seat No. :" + seatNo);
                    }

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

