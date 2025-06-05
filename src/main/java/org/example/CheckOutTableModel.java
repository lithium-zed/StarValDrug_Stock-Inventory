// CheckOutTableModel.java
package org.example;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CheckOutTableModel extends AbstractTableModel {
    // Made public as your SalesFrame code was directly accessing `checkoutDataList.size()` etc.
    public List<CheckoutData> checkoutDataList;

    // Adjusted column names to reflect CheckoutData structure (e.g., "Total Price" vs "Selling Price")
    private String[] columnNames = {"Batch #", "Brand Name", "Generic Name", "Quantity", "Total Price"};

    public CheckOutTableModel() {
        this.checkoutDataList = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return checkoutDataList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CheckoutData data = checkoutDataList.get(rowIndex);
        switch (columnIndex) {
            case 0: return data.getBatch_number();
            case 1: return data.getBrand_name();
            case 2: return data.getGeneric_name();
            case 3: return data.getQuantity();
            case 4: return data.getPrice(); // This is the total price for this line item
            default: return null;
        }
    }

    /**
     * Adds new CheckoutData or updates existing item's quantity and price.
     * Assumes items are identified by Batch Number and Brand Name.
     */
    public void addCheckoutData(CheckoutData newData) {
        int existingIndex = -1;
        for (int i = 0; i < checkoutDataList.size(); i++) {
            CheckoutData existingItem = checkoutDataList.get(i);
            if (existingItem.getBatch_number() == newData.getBatch_number() &&
                    existingItem.getBrand_name().equals(newData.getBrand_name())) {
                existingIndex = i;
                break;
            }
        }

        if (existingIndex != -1) {
            // Update quantity and price for existing item
            CheckoutData existingData = checkoutDataList.get(existingIndex);
            int updatedQuantity = existingData.getQuantity() + newData.getQuantity();
            double updatedPrice = existingData.getPrice() + newData.getPrice(); // Summing total prices

            // Replace the old CheckoutData object with an updated one
            checkoutDataList.set(existingIndex, new CheckoutData(
                    existingData.getBatch_number(), existingData.getBrand_name(), existingData.getGeneric_name(),
                    updatedQuantity, updatedPrice
            ));
            fireTableRowsUpdated(existingIndex, existingIndex); // Notify table that row data changed
        } else {
            // Add new item
            checkoutDataList.add(newData);
            fireTableRowsInserted(checkoutDataList.size() - 1, checkoutDataList.size() - 1); // Notify table that new row inserted
        }
    }

    /**
     * Removes an item completely from the checkout table.
     * If you want to decrement quantity instead, you'll need to modify this method.
     */
    public void removeItem(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < checkoutDataList.size()) {
            checkoutDataList.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex); // Notify table that row deleted
        }
    }

    /**
     * Calculates the total amount of all items currently in the checkout table.
     */
    public double getTotalCheckoutAmount() {
        double total = 0.0;
        for (CheckoutData data : checkoutDataList) {
            total += data.getPrice(); // Summing the line item total prices
        }
        return total;
    }

    /**
     * Clears all items from the checkout table.
     */
    public void clearAllItems() {
        int rowCount = checkoutDataList.size();
        checkoutDataList.clear();
        if (rowCount > 0) {
            fireTableRowsDeleted(0, rowCount - 1); // Notify table that rows deleted
        }
    }

    /**
     * Returns a defensive copy of the current list of checkout items.
     * Useful for creating a SaleTransaction without external modification issues.
     */
    public ArrayList<CheckoutData> getCurrentCheckoutItems() {
        return new ArrayList<>(checkoutDataList);
    }
}