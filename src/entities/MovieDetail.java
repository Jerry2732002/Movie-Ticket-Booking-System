package src.entities;

import src.enums.Genre;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MovieDetail {
    private String name;
    private Time duration;
    private Genre genre;
    private String cast;
    private String director;
    private String description;
    private String theatreName;
    private String location;
    private int screenNo;
    private LocalDate date;
    private Time time;

    public String getName() {
        return name;
    }

    public Time getDuration() {
        return duration;
    }

    public String getCast() {
        return cast;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public int getScreenNo() {
        return screenNo;
    }

    public LocalDate getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public MovieDetail(String name, Time duration, String genre, String cast, String director, String description, String theatreName, String location, int screenNo, Timestamp startTime) {
        this.name = name;
        this.duration = duration;
        this.genre = Genre.valueOf(genre);
        this.cast = cast;
        this.director = director;
        this.description = description;
        this.theatreName = theatreName;
        this.location = location;
        this.screenNo = screenNo;
        this.date = startTime.toLocalDateTime().toLocalDate();
        this.time = Time.valueOf(startTime.toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
