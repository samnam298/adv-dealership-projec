package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles writing contract information (sales and leases)
 * to the contracts.csv file.
 */
public class ContractFileManager {

    private static final String FILE_NAME = "contracts.csv";

    /**
     * Appends a contract to the contracts.csv file.
     * The output format depends on whether the contract
     * is a sale or a lease.
     */
    public void saveContract(Contract contract) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            Vehicle v = contract.getVehicleSold();

            // ---- If the contract is a SalesContract ----
            if (contract instanceof SalesContract) {
                SalesContract sc = (SalesContract) contract;

                writer.write("SALE|" +
                        sc.getDate() + "|" +
                        sc.getCustomerName() + "|" +
                        sc.getCustomerEmail() + "|" +
                        v.getVin() + "|" +
                        v.getYear() + "|" +
                        v.getMake() + "|" +
                        v.getModel() + "|" +
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

            // ---- If the contract is a LeaseContract ----
            else if (contract instanceof LeaseContract) {
                LeaseContract lc = (LeaseContract) contract;

                writer.write("LEASE|" +
                        lc.getDate() + "|" +
                        lc.getCustomerName() + "|" +
                        lc.getCustomerEmail() + "|" +
                        v.getVin() + "|" +
                        v.getYear() + "|" +
                        v.getMake() + "|" +
                        v.getModel() + "|" +
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
            // No error handling required per project instructions
        }
    }
}
