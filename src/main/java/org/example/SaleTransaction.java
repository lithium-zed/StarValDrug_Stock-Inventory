// SaleTransaction.java
package org.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID; // For generating unique transaction IDs

public class SaleTransaction {
    private String transactionId;
    private Date transactionDate;
    private ArrayList<CheckoutData> items; // List of items sold in this specific transaction
    private double totalAmount;

    /**
     * Constructs a new SaleTransaction.
     * @param transactionDate The date and time the transaction occurred.
     * @param items A list of CheckoutData items that were part of this transaction.
     * A defensive copy is made, so the original list can be cleared.
     * @param totalAmount The total monetary amount of this transaction.
     */
    public SaleTransaction(Date transactionDate, ArrayList<CheckoutData> items, double totalAmount) {
        // Generate a simple unique ID for each transaction
        this.transactionId = UUID.randomUUID().toString().substring(0, 8).toUpperCase(); // Short unique ID
        this.transactionDate = transactionDate;
        this.items = new ArrayList<>(items); // IMPORTANT: Make a defensive copy of the items list
        this.totalAmount = totalAmount;
    }

    // --- Getters ---
    public String getTransactionId() {
        return transactionId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public ArrayList<CheckoutData> getItems() {
        return new ArrayList<>(items); // Return a defensive copy when requested
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "SaleTransaction{" +
                "transactionId='" + transactionId + '\'' +
                ", transactionDate=" + transactionDate +
                ", totalAmount=" + totalAmount +
                ", items count=" + items.size() +
                '}';
    }
}