package src.services.user;

import src.database.BookingDatabase;
import src.entities.Booking;
import src.entities.BookingDetails;
import src.enums.Status;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class BookingService {

    public static void bookTicket(int showID, int seatID) {
        Booking booking = new Booking();
        booking.setUserID(UserService.userId);
        booking.setShowID(showID);
        booking.setSeatID(seatID);
        booking.setBookingDate(new Timestamp(System.currentTimeMillis()));
        booking.setStatus(Status.BOOKED);

        BookingDatabase.bookTicket(booking);
    }

    public static void bookingService(Scanner scanner) {
        String choice;
        while (true) {
            System.out.println("Booking MENU :");
            System.out.println("Enter 'list' to list all booking\nEnter 'cancel' to cancel a show\nEnter 'back' to go back");
            choice = scanner.next().toLowerCase();
            switch (choice) {
                case "list":
                    List<BookingDetails> bookingDetails = BookingDatabase.getBookingHistory(UserService.userId);
                    System.out.println("------------------------------------------------------------");
                    System.out.printf("%-8s %-8s %-10s %-12s %-12s %-20s %-30s\n", "BookingID", "SeatNo", "ScreenNo", "Date", "Time", "Theatre Name", "Theatre Location");
                    System.out.println("------------------------------------------------------------");

                    for (BookingDetails details : bookingDetails) {
                        System.out.printf("%-8s %-10s %-8d %-12s %-12s %-20s %-30s\n",
                                details.getBookingId(),
                                details.getSeatNo(),
                                details.getScreenNo(),
                                details.getDate(),
                                details.getTime(),
                                details.getTheatreName(),
                                details.getLocation());
                    }

                    System.out.println("------------------------------------------------------------");
                    break;
                case "cancel":
                    System.out.println("Enter the Booking id to cancel booking");
                    int bookingId = scanner.nextInt();
                    Timestamp showDate = BookingDatabase.getShowDateByBookingID(bookingId);
                    if (showDate != null) {
                        LocalDateTime showDateTime = showDate.toLocalDateTime();

                        LocalDateTime currentTime = LocalDateTime.now();

                        Duration duration = Duration.between(currentTime, showDateTime);

                        if (duration.toMinutes() > 30) {
                            BookingDatabase.cancelBooking(bookingId);
                            System.out.println("Booking ID " + bookingId + " has been successfully cancelled.");
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
