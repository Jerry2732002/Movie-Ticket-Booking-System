package src.services.user;

import src.database.BookingDatabase;
import src.database.SeatsDatabase;
import src.entities.Booking;
import src.entities.Seat;
import src.entities.User;
import src.enums.Status;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BookingService {

    public static void bookTicket( int showID, int seatID) {
        Booking booking = new Booking();
        booking.setUserID(UserService.userId);
        booking.setShowID(showID);
        booking.setSeatID(seatID);
        booking.setBookingDate(new Timestamp(System.currentTimeMillis()));
        booking.setStatus(Status.BOOKED);

        BookingDatabase.bookTicket(booking);
    }

    public static void bookingService(int showID) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true) {
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
                    bookTicket(showID,seatID);
                    break;

                case "back":
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }
}
