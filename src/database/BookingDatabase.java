package src.database;

import src.entities.Booking;
import src.entities.BookingDetails;
import src.entities.Seat;
import src.enums.Category;
import src.enums.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookingDatabase {
    public static void bookTicket(Booking booking) {
        String query = "INSERT INTO bookings (UserID, ShowID, SeatID, BookingDate, Status) VALUES (?,?,?,?,?)";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, booking.getUserID());
            statement.setInt(2, booking.getShowID());
            statement.setInt(3, booking.getSeatID());
            statement.setTimestamp(4, booking.getBookingDate());
            statement.setString(5, booking.getStatus().toString());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully booked");
            } else {
                System.out.println("Failed to book ticket");
            }
        } catch (SQLException e) {
            System.err.println("Error while adding user: " + e.getMessage());
        }
    }

    public static List<BookingDetails> getBookingHistory(int userID) {
        String query = "SELECT b.BookingID,s.SeatNo, sc.ScreenNo, sh.StartTime,t.Name,t.Location FROM bookings b\n" +
                "JOIN shows sh ON b.ShowID = sh.ShowID\n" +
                "JOIN seats s ON b.SeatID = s.SeatID\n" +
                "JOIN screens sc ON sc.ScreenID = sh.ScreenID\n" +
                "JOIN theatres t ON t.TheatreID = sc.TheatreID\n" +
                "WHERE b.Status != 'CANCELLED' AND b.UserID = ? ORDER BY BookingDate DESC";
        List<BookingDetails> bookingDetails = new ArrayList<>();
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookingId = resultSet.getInt("BookingID");
                String seatNo = resultSet.getString("SeatNo");
                int screenNo = resultSet.getInt("ScreenNo");
                Timestamp startTime = resultSet.getTimestamp("StartTime");
                String theatreName = resultSet.getString("Name");
                String location = resultSet.getString("Location");
                BookingDetails details = new BookingDetails(bookingId,seatNo, screenNo, startTime, theatreName, location);
                bookingDetails.add(details);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching booking history: " + e.getMessage());
        }
        return bookingDetails;
    }

    public static Timestamp getShowDateByBookingID(int bookingID) {
        String query = "SELECT StartTime FROM bookings b \n" +
                "JOIN shows s ON b.ShowID = s.ShowID WHERE BookingID = ?";
        Timestamp showDate = null;
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookingID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                showDate  = resultSet.getTimestamp("StartTime");
            }else {
                System.out.println("No Booking Found with id :" + bookingID);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching booking history: " + e.getMessage());
        }
        return showDate;
    }

    public static void cancelBooking(int bookingID) {
        String query = "UPDATE bookings SET Status = 'CANCELLED' WHERE BookingID = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookingID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Booking successfully cancelled.");
            } else {
                System.out.println("Failed to cancel the booking. Booking not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error while cancelling booking: " + e.getMessage());
        }
    }

    public static Map<String, List<Seat>> getAvailableSeats(int showId) {
        String query = "SELECT s.SeatID, s.SeatNo, s.ScreenID, s.Category, s.Price \n" +
                "FROM seats s\n" +
                "LEFT JOIN bookings b ON s.SeatID = b.SeatID AND b.Status != 'CANCELLED'\n" +
                "WHERE b.SeatID IS NULL AND s.ScreenID IN (SELECT ScreenID FROM shows WHERE ShowID = ?)\n" +
                "ORDER BY s.SeatNo;\n";
        Map<String, List<Seat>> availableSeats = new LinkedHashMap<>();

        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, showId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Seat seat = new Seat();
                seat.setSeatNo(resultSet.getString("SeatNo"));
                seat.setScreenID(resultSet.getInt("ScreenID"));
                seat.setCategory(Category.valueOf(resultSet.getString("Category").toUpperCase()));
                seat.setPrice(resultSet.getDouble("Price"));

                String row = seat.getSeatNo().substring(0, 1);
                if (availableSeats.containsKey(row)) {
                    availableSeats.get(row).add(seat);
                } else {
                    List<Seat> seatList = new ArrayList<>();
                    seatList.add(seat);
                    availableSeats.put(row, seatList);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving available seats: " + e.getMessage());
        }
        return availableSeats;
    }
}
