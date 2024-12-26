package src.entities;

public class Screen {
    private int screenID;
    private int theatreID;
    private int screenNo;
    private int noOfRows;

    public int getScreenID() {
        return screenID;
    }

    public void setScreenID(int screenID) {
        this.screenID = screenID;
    }

    public int getScreenNo() {
        return screenNo;
    }

    public void setScreenNo(int screenNo) {
        this.screenNo = screenNo;
    }

    public int getTheatreID() {
        return theatreID;
    }

    public void setTheatreID(int theatreID) {
        this.theatreID = theatreID;
    }

    public int getNoOfRows() {
        return noOfRows;
    }

    public void setNoOfRows(int noOfRows) {
        this.noOfRows = noOfRows;
    }

    @Override
    public String toString() {
        return "Screen{" +
                "screenID=" + screenID +
                ", theatreID=" + theatreID +
                ", screenNo=" + screenNo +
                ", totalSeats=" + noOfRows +
                '}';
    }
}
