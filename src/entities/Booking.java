package src.entities;

import src.enums.Status;

import java.sql.Timestamp;

public class Booking {
    private int bookingID;
    private int userID;
    private int showID;
    private int seatID;
    private Timestamp bookingDate;
    private Status status;

    public Booking() {
    }

    public Booking(int bookingID, int userID, int showID, int seatID, Timestamp bookingDate, Status status) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.showID = showID;
        this.seatID = seatID;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getShowID() {
        return showID;
    }

    public void setShowID(int showID) {
        this.showID = showID;
    }

    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
