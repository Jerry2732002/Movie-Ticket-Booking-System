package src.entities;

import src.enums.Status;

import java.sql.Date;

public class Bookings {
    private int bookingID;
    private int userID;
    private int showID;
    private int seatID;
    private Date bookingDate;
    private Status status;
}
