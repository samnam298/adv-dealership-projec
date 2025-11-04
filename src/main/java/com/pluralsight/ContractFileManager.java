package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ContractFileManager {

    private static final String FILE_NAME = "contracts.csv";

    public void saveContract(Contract contract) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            Vehicle v = contract.getVehicleSold();

            if (contract instanceof SalesContract sc) {
                writer.write("SALE|" +
                        sc.getDate() + "|" +
                        sc.getCustomerName() + "|" +
                        sc.getCustomerEmail() + "|" +
                        v.getVin() + "|" +
                        v.getYear() + "|" +
                        v.getMake() + "|" +
                        v.getModel() + "|" +
                        v.getType() + "|" +
                        v.getColor() + "|" +
                        v.getOdometer() + "|" +
                        v.getPrice() + "|" +
                        sc.getSalesTaxAmount() + "|" +
                        sc.getRecordingFee() + "|" +
                        sc.getProcessingFee() + "|" +
                        sc.getTotalPrice() + "|" +
                        (sc.isFinanced() ? "YES" : "NO") + "|" +
                        sc.getMonthlyPayment());
            }
            else if (contract instanceof LeaseContract lc) {
                writer.write("LEASE|" +
                        lc.getDate() + "|" +
                        lc.getCustomerName() + "|" +
                        lc.getCustomerEmail() + "|" +
                        v.getVin() + "|" +
                        v.getYear() + "|" +
                        v.getMake() + "|" +
                        v.getModel() + "|" +
                        v.getType() + "|" +
                        v.getColor() + "|" +
                        v.getOdometer() + "|" +
                        v.getPrice() + "|" +
                        lc.getExpectedEndingValue() + "|" +
                        lc.getLeaseFee() + "|" +
                        lc.getTotalPrice() + "|" +
                        lc.getMonthlyPayment());
            }

            writer.newLine();

        } catch (IOException e) {
        }
    }
}
