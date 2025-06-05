package org.example;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class CheckOutTableModel extends AbstractTableModel {
    ArrayList<CheckoutData> checkoutDataList;
    String[] header = {"Batch #", "Brand Name", "Generic Name", "Quantity", "Price"};

    public CheckOutTableModel() {
        checkoutDataList = new ArrayList<>();
    }

    public void addCheckoutData(CheckoutData checkoutData){
        checkoutDataList.add(checkoutData);
        fireTableRowsInserted(checkoutDataList.size() - 1, checkoutDataList.size() - 1);
    }

    @Override
    public int getRowCount() {
        return checkoutDataList.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CheckoutData checkoutData = checkoutDataList.get(rowIndex);

        switch (columnIndex) {
            case 0: return checkoutData.getBranch_number();
            case 1: return checkoutData.getBrand_name();
            case 2: return checkoutData.getGeneric_name();
            case 3: return checkoutData.getQuantity();
            case 4: return checkoutData.getPrice();
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Integer.class;
            case 4:
                return Double.class;
            default:
                return Object.class;
        }
    }
}
