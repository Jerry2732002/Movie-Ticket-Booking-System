package src.entities;

import src.enums.Category;

public class Seat {
    private int seatID;
    private String seatNo;
    private int screenID;
    private Category category;
    private double price;

    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public int getScreenID() {
        return screenID;
    }

    public void setScreenID(int screenID) {
        this.screenID = screenID;
    }

    public String getCategory() {
        return category.toString();
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatID=" + seatID +
                ", seatNo='" + seatNo + '\'' +
                ", screenID=" + screenID +
                ", category=" + category +
                ", price=" + price +
                '}';
    }
}
