package src.services.admin;

import src.database.SeatsDatabase;
import src.entities.Seat;
import src.enums.Category;

import java.util.Scanner;

public class SeatService {
    public static void addSeats(int screenID, Scanner scanner) {
        System.out.println("Enter the number of rows");
        int row = scanner.nextInt();
        scanner.nextLine();
        Seat seat = new Seat();
        for (int i = 0; i < row; i++) {
            System.out.println("Enter the category of row: " + (char)(65 + i));
            String categoryInput = scanner.nextLine();
            Category category = null;
            try {
                category = Category.valueOf(categoryInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid category. Please enter a valid category.");
                continue;
            }

            System.out.println("Enter price for that category/row");
            double price = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Enter the number of seats in that row");
            int noOfSeats = scanner.nextInt();
            scanner.nextLine();

            String prefix = String.valueOf((char)(65 + i));
            seat.setScreenID(screenID);
            seat.setCategory(category);
            seat.setPrice(price);
            for (int j = 1; j <= noOfSeats; j++) {

                String seatNo = prefix + String.valueOf(j);
                seat.setSeatNo(seatNo);
                SeatsDatabase.addSeat(seat);
            }
        }
    }
}
