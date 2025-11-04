package com.pluralsight;
public class LeaseContract extends Contract {

    // ---- Fields ----
    private double expectedEndingValue;  // 50% of vehicle price
    private double leaseFee;             // 7% of vehicle price

    // ---- Constructor ----
    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);

        double price = vehicleSold.getPrice();
        this.expectedEndingValue = price * 0.50;
        this.leaseFee = price * 0.07;
    }

    // ---- Getters ----
    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    // ---- Total Price Calculation ----
    @Override
    public double getTotalPrice() {
        // Total lease cost = vehicle price + lease fee
        return getVehicleSold().getPrice() + leaseFee;
    }

    // ---- Monthly Payment Calculation ----
    @Override
    public double getMonthlyPayment() {
        double price = getVehicleSold().getPrice();
        double rate = 0.04 / 12;   // 4% annual rate
        int months = 36;           // 36-month lease term

        // Financed portion = (price - expected ending value)
        double loanAmount = price - expectedEndingValue;

        // Standard monthly payment formula
        return (loanAmount * rate) / (1 - Math.pow(1 + rate, -months));
    }
}
