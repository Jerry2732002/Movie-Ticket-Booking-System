package src.database;

import src.entities.Booking;
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

    public static List<Booking> getBookingHistory(int userID) {
        String query = "SELECT * FROM bookings WHERE UserID = ? AND Status != 'CANCELLED' ORDER BY BookingDate DESC";
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookingID = resultSet.getInt("BookingID");
                int showID = resultSet.getInt("ShowID");
                int seatID = resultSet.getInt("SeatID");
                Timestamp bookingDate = resultSet.getTimestamp("BookingDate");
                String status = resultSet.getString("Status");
                Booking booking = new Booking(bookingID, userID, showID, seatID, bookingDate, Status.valueOf(status));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching booking history: " + e.getMessage());
        }
        return bookings;
    }

    public static int getBookingCountForShow(int showID) {
        String query = "SELECT COUNT(*) FROM bookings WHERE ShowID = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, showID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching booking count: " + e.getMessage());
        }
        return 0;
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

    public static int getNoOfAvailableSeats(int showId) {
        String query = "SELECT count(SeatID) AS AvailableSeats FROM seats WHERE SeatID NOT IN (SELECT SeatID FROM bookings) AND ShowID = ?";
        int noOfRowsAvailable = 0;
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, showId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                noOfRowsAvailable = resultSet.getInt("AvailableSeats");
            }
        } catch (SQLException e) {
            System.err.println("Error while cancelling booking: " + e.getMessage());
        }
        return noOfRowsAvailable;
    }

    public static Map<String, List<Seat>> getAvailableSeats(int showId) {
        String query = "SELECT s.SeatID, s.SeatNo, s.ScreenID, s.Category, s.Price \n" +
                "FROM seats s \n" +
                "LEFT JOIN bookings b ON s.SeatID = b.SeatID \n" +
                "WHERE b.SeatID IS NULL AND s.ScreenID IN (SELECT ScreenID FROM shows WHERE ShowID = ?) \n" +
                "ORDER BY s.SeatNo;\n";
        Map<String, List<Seat>> availableSeats = new LinkedHashMap<>();

        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, showId);  // Set the show ID parameter
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Seat seat = new Seat();
                seat.setSeatNo(resultSet.getString("SeatNo"));
                seat.setScreenID(resultSet.getInt("ScreenID"));
                seat.setCategory(Category.valueOf(resultSet.getString("Category")));
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
