import java.util.*;

public class Problem8 {

    // Inner class for Car
    static class Car {
        String licensePlate;
        long entryTime;

        Car(String lp) {
            this.licensePlate = lp;
            this.entryTime = System.currentTimeMillis();
        }
    }

    private final int CAPACITY = 500;
    private Car[] spots = new Car[CAPACITY];
    private int occupiedCount = 0;

    // Custom Hash Function
    private int hash(String licensePlate) {
        return Math.abs(licensePlate.hashCode()) % CAPACITY;
    }

    public void parkVehicle(String licensePlate) {
        if (occupiedCount >= CAPACITY) {
            System.out.println("Lot Full!");
            return;
        }

        int preferredSpot = hash(licensePlate);
        int currentSpot = preferredSpot;
        int probes = 0;

        // Linear Probing: spot, spot+1, spot+2...
        while (spots[currentSpot] != null) {
            currentSpot = (currentSpot + 1) % CAPACITY;
            probes++;
        }

        spots[currentSpot] = new Car(licensePlate);
        occupiedCount++;
        System.out.println("parkVehicle(\"" + licensePlate + "\") -> Assigned spot #"
                + currentSpot + " (" + probes + " probes)");
    }

    public void exitVehicle(String licensePlate) {
        for (int i = 0; i < CAPACITY; i++) {
            if (spots[i] != null && spots[i].licensePlate.equals(licensePlate)) {
                long duration = (System.currentTimeMillis() - spots[i].entryTime) / 1000;
                double fee = duration * 0.05; // Sample rate
                spots[i] = null;
                occupiedCount--;
                System.out.printf("exitVehicle(\"%s\") -> Spot #%d freed, Fee: $%.2f\n",
                        licensePlate, i, fee);
                return;
            }
        }
        System.out.println("exitVehicle(\"" + licensePlate + "\") -> Vehicle not found!");
    }

    // Main method to test
    public static void main(String[] args) throws InterruptedException {
        Problem8 parkingLot = new Problem8();

        parkingLot.parkVehicle("TN01AB1234");
        parkingLot.parkVehicle("TN02XY5678");

        Thread.sleep(2000); // simulate time passing

        parkingLot.exitVehicle("TN01AB1234");
        parkingLot.exitVehicle("TN09ZZ9999"); // not found case
    }
}