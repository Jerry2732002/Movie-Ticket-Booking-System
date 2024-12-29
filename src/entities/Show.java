package src.entities;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Show {
    private int showID;
    private int screenID;
    private int movieID;
    private Timestamp startTime;
    private Timestamp endTime;
    private Date date;
    private Movie movie;

    public int getShowID() {
        return showID;
    }

    public void setShowID(int showID) {
        this.showID = showID;
    }

    public int getScreenID() {
        return screenID;
    }

    public void setScreenID(int screenID) {
        this.screenID = screenID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public Show() {
    }

    public Show(int showID, int screenID, int movieID, Timestamp startTime, Timestamp endTime) {
        this.showID = showID;
        this.screenID = screenID;
        this.movieID = movieID;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
