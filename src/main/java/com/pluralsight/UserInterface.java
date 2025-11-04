package com.pluralsight;

import java.util.List;
import java.util.Scanner;

/**
 * Handles user interaction with the dealership system.
 * Updated for Workshop 5w: adds SELL/LEASE option.
 */
public class UserInterface {

    private Dealership dealership;

    private void init() {
        DealershipFileManager dfm = new DealershipFileManager();
        dealership = dfm.getDealership();
    }

    public void display() {
        init();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===============================");
            System.out.println(" Welcome to " + dealership.getName());
            System.out.println("===============================");
            System.out.println("1. Find vehicles within a price range");
            System.out.println("2. Find vehicles by make/model");
            System.out.println("3. Find vehicles by year range");
            System.out.println("4. Find vehicles by color");
            System.out.println("5. Find vehicles by mileage range");
            System.out.println("6. Find vehicles by type");
            System.out.println("7. List all vehicles");
            System.out.println("8. Add a vehicle");
            System.out.println("9. Remove a vehicle");
            System.out.println("10. Sell/Lease a vehicle");
            System.out.println("99. Quit");
            System.out.print("\nEnter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> processGetByPriceRequest(scanner);
                case 2 -> processGetByMakeModelRequest(scanner);
                case 3 -> processGetByYearRequest(scanner);
                case 4 -> processGetByColorRequest(scanner);
                case 5 -> processGetByMileageRequest(scanner);
                case 6 -> processGetByTypeRequest(scanner);
                case 7 -> processAllVehiclesRequest();
                case 8 -> processAddVehicleRequest(scanner);
                case 9 -> processRemoveVehicleRequest(scanner);
                case 10 -> processSellOrLeaseRequest(scanner);
                case 99 -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ---------- Display Helper ----------
    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            for (Vehicle v : vehicles) System.out.println(v);
        }
    }

    private void processAllVehiclesRequest() {
        displayVehicles(dealership.getAllVehicles());
    }

    // ---------- Sell or Lease ----------
    private void processSellOrLeaseRequest(Scanner scanner) {
        System.out.print("Enter contract date (YYYYMMDD): ");
        String date = scanner.nextLine();

        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();

        System.out.print("Enter VIN of vehicle: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        Vehicle vehicle = null;
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vin) {
                vehicle = v;
                break;
            }
        }

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.print("Is this a SALE or LEASE? (S/L): ");
        String type = scanner.nextLine().trim().toUpperCase();

        Contract contract = null;
        if (type.equals("S")) {
            System.out.print("Would you like to finance? (Y/N): ");
            boolean financed = scanner.nextLine().equalsIgnoreCase("Y");
            contract = new SalesContract(date, name, email, vehicle, financed);
        } else if (type.equals("L")) {
            // Can only lease vehicles 3 years old or newer
            int currentYear = java.time.Year.now().getValue();
            if (currentYear - vehicle.getYear() > 3) {
                System.out.println("This vehicle is too old to lease.");
                return;
            }
            contract = new LeaseContract(date, name, email, vehicle);
        } else {
            System.out.println("Invalid selection.");
            return;
        }

        // Save contract
        ContractFileManager cfm = new ContractFileManager();
        cfm.saveContract(contract);

        // Remove sold/leased vehicle from inventory
        dealership.removeVehicle(vehicle);
        new DealershipFileManager().saveDealership(dealership);

        System.out.println("Contract recorded and vehicle removed from inventory.");
    }

    // ---------- (existing search/add/remove methods here, unchanged) ----------
    private void processGetByPriceRequest(Scanner scanner) { /* ... */ }
    private void processGetByMakeModelRequest(Scanner scanner) { /* ... */ }
    private void processGetByYearRequest(Scanner scanner) { /* ... */ }
    private void processGetByColorRequest(Scanner scanner) { /* ... */ }
    private void processGetByMileageRequest(Scanner scanner) { /* ... */ }
    private void processGetByTypeRequest(Scanner scanner) { /* ... */ }
    private void processAddVehicleRequest(Scanner scanner) { /* ... */ }
    private void processRemoveVehicleRequest(Scanner scanner) { /* ... */ }
}
