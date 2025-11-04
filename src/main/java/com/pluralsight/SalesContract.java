package com.pluralsight;
public class SalesContract extends Contract {
    private double salesTaxAmount;
    private final double recordingFee = 100.00;
    private double processingFee;
    private boolean isFinanced;

    public SalesContract(String date, String customerName, String customerEmail,
                         Vehicle vehicleSold, boolean isFinanced) {
        super(date, customerName, customerEmail, vehicleSold);
        this.isFinanced = isFinanced;
    }

    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public boolean isFinanced() {
        return isFinanced;
    }
    @Override
    public double getTotalPrice() {
        Vehicle v = getVehicleSold();
        double price = v.getPrice();

        // 5% sales tax
        salesTaxAmount = price * 0.05;

        // Processing fee depends on price
        processingFee = (price < 10000) ? 295.00 : 495.00;

        // Total price = vehicle + tax + fees
        return price + salesTaxAmount + recordingFee + processingFee;
    }

    // ---- Monthly Payment Calculation ----
    @Override
    public double getMonthlyPayment() {
        if (!isFinanced) {
            return 0; // no loan
        }

        double totalPrice = getTotalPrice();
        double rate;
        int months;

        // Financing rules
        if (getVehicleSold().getPrice() >= 10000) {
            rate = 0.0425 / 12; // 4.25% annual rate
            months = 48;
        } else {
            rate = 0.0525 / 12; // 5.25% annual rate
            months = 24;
        }

        // Monthly payment formula for loans
        return (totalPrice * rate) / (1 - Math.pow(1 + rate, -months));
    }
}
