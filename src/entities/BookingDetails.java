package src.entities;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingDetails {
    private int bookingId;
    private String seatNo;
    private int screenNo;
    private LocalDate date;
    private String time;
    private String theatreName;
    private String location;

    public BookingDetails(int bookingId,String seatNo, int screenNo, Timestamp startTime, String theatreName, String location) {
        this.bookingId = bookingId;
        this.seatNo = seatNo;
        this.screenNo = screenNo;
        this.date = startTime.toLocalDateTime().toLocalDate();
        this.time = startTime.toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.theatreName = theatreName;
        this.location = location;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public int getScreenNo() {
        return screenNo;
    }

    public void setScreenNo(int screenNo) {
        this.screenNo = screenNo;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
}
