package src.entities;

public class Theatre {
    private int theatreID;
    private String name;
    private String location;
    private int ScreenNo;

    public int getTheatreID() {
        return theatreID;
    }

    public void setTheatreID(int theatreID) {
        this.theatreID = theatreID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public int getScreenNo() {
        return ScreenNo;
    }

    public void setScreenNo(int screenNo) {
        ScreenNo = screenNo;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
