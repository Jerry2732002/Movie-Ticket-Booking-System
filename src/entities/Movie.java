package src.entities;

import src.enums.Genre;

import java.sql.Time;

public class Movie {
    private int movieID;
    private String name;
    private Time duration;
    private Genre genre = Genre.NA;
    private String cast = "N/A";
    private String director = "Unknown Director";
    private String description = "N/A";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre.toString();
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Movie(){}

    public Movie(String name, Time duration, String genre, String director, String cast, String description) {
        this.name = name;
        this.duration = duration;
        this.genre = Genre.valueOf(genre);
        this.cast = cast;
        this.director = director;
        this.description = description;
    }

    public Movie(int movieID, String name, Time duration, String genre, String director, String cast, String description) {
        this.movieID = movieID;
        this.name = name;
        this.duration = duration;
        this.genre = Genre.valueOf(genre);
        this.cast = cast;
        this.director = director;
        this.description = description;
    }
}