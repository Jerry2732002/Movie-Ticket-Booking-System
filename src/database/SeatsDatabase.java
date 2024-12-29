package src.database;

import src.entities.Seat;
import src.enums.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatsDatabase {

    public static void addSeat(Seat seat) {
        String query = "INSERT INTO seats (SeatNo, ScreenID, Category, Price) VALUES (?,?,?,?)";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, seat.getSeatNo());
            statement.setInt(2, seat.getScreenID());
            statement.setString(3, seat.getCategory());
            statement.setDouble(4, seat.getPrice());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Seat " + seat.getSeatNo() + "  was successfully added.");
            } else {
                System.out.println("Failed to add new user");
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.err.println("Error: Username already exists.");
            } else {
                System.err.println("Error while adding user: " + e.getMessage());
            }
        }
    }

    public static void deleteSeat(String seatNo) {
        String query = "DELETE FROM seats WHERE SeatNo = ?";
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, seatNo);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Seat deleted successfully.");
            } else {
                System.out.println("Failed to delete seat.");
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting seat: " + e.getMessage());
        }
    }

    public static List<Seat> getAllSeats() {
        String query = "SELECT * FROM seats";
        List<Seat> seats = new ArrayList<>();
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Seat seat = new Seat();
                seat.setSeatNo(resultSet.getString("SeatNo"));
                seat.setScreenID(resultSet.getInt("ScreenID"));
                seat.setCategory(Category.valueOf(resultSet.getString("Category")));
                seat.setPrice(resultSet.getDouble("Price"));
                seats.add(seat);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching all seats: " + e.getMessage());
        }
        return seats;
    }

    public static List<Integer> getAllSeatIDs() {
        String query = "SELECT SeatID FROM seats";
        List<Integer> seatIDs = new ArrayList<>();
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                seatIDs.add(resultSet.getInt("SeatID"));
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching all seats: " + e.getMessage());
        }
        return seatIDs;
    }

    public static int getSeatIDBySeatNo(String seatNo) {
        String query = "SELECT SeatID FROM seats WHERE SeatNo = ?";
        int seatID = -1;
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,seatNo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                seatID = resultSet.getInt("SeatID");
            }else {
                throw new RuntimeException("No seat by seat no found");
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching all seats: " + e.getMessage());
        }
        return seatID;
    }

    public static List<String> getAllSeatNos() {
        String query = "SELECT SeatNo FROM seats";
        List<String> seatNos = new ArrayList<>();
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                seatNos.add(resultSet.getString("SeatNo"));
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching all seats: " + e.getMessage());
        }
        return seatNos;
    }

    public static Seat getSeatBySeatNo(String seatNo) {
        String query = "SELECT * FROM seats WHERE SeatNo = ?";
        Seat seat = null;
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, seatNo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                seat = new Seat();
                seat.setSeatNo(resultSet.getString("SeatNo"));
                seat.setScreenID(resultSet.getInt("ScreenID"));
                seat.setCategory(Category.valueOf(resultSet.getString("Category")));
                seat.setPrice(resultSet.getDouble("Price"));
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching seat by SeatNo: " + e.getMessage());
        }
        return seat;
    }

    public static int getNoOfSeats() {
        String query = "SELECT COUNT(*) AS NoOfSeats  FROM seats";
        int noOfSeats = 0;
        try (Connection connection = CreateConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                noOfSeats = resultSet.getInt("NoOfSeats");
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching all seats: " + e.getMessage());
        }
        return noOfSeats;
    }
}
