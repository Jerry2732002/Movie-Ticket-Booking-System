package src.services.user;

import src.database.BookingDatabase;
import src.database.SeatsDatabase;
import src.entities.Seat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SeatService {

    public static void seatService(int showID, Scanner scanner) {
        String choice;
        while (true) {
            System.out.println("SEAT MENU :");
            System.out.println("Enter 'list' to list all available seats\nEnter 'select' to select a seat\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "list":
                    Map<String, List<Seat>> availableSeats = BookingDatabase.getAvailableSeats(showID);

                    for (Map.Entry<String, List<Seat>> entry : availableSeats.entrySet()) {

                        List<Seat> seatsInRow = entry.getValue();

                        StringBuilder seatRow = new StringBuilder();

                        for (Seat seat : seatsInRow) {
                            seatRow.append(seat.getSeatNo()).append(" ");
                        }

                        if (!seatsInRow.isEmpty()) {
                            Seat firstSeat = seatsInRow.getFirst();
                            seatRow.append(String.format("[%s %.2f/-]", firstSeat.getCategory(), firstSeat.getPrice()));
                        }

                        System.out.println(seatRow);
                    }
                    break;
                case "select":
                    System.out.println("Enter the SeatNo(s) to select (comma-separated) for booking:");
                    String seatNos = scanner.next();

                    String[] seatNoArray = seatNos.split(",");

                    for (String seatNo : seatNoArray) {
                        seatNo = seatNo.trim();

                        System.out.println(seatNo);

                        int seatID = SeatsDatabase.getSeatIDBySeatNo(seatNo, showID);

                        if (seatID > 0) {
                            System.out.println("You selected Seat No: " + seatNo + ". Do you want to book this seat? Enter 'y' to confirm.");
                            scanner.nextLine();
                            if (scanner.next().equalsIgnoreCase("y")) {
                                BookingService.bookTicket(showID, seatID);
                                System.out.println("Seat " + seatNo + " booked successfully.");
                            } else {
                                System.out.println("Booking for Seat No " + seatNo + " was not confirmed.");
                            }
                        } else {
                            System.out.println("No seat found with Seat No: " + seatNo);
                        }
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

