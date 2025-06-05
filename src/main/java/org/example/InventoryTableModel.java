package org.example;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryTableModel extends AbstractTableModel {
    ArrayList<DrugData> drugDataList;
    String[] header = {"Batch #", "Vendor Name", "C/P", "Brand Name", "Generic Name", "Quantity", "Unit of Measure",
                            "Exp Date", "Purchase Cost", "Cost/Unit", "Selling Price", "Suggested Selling Price", "Margin"};

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    public InventoryTableModel() {
        this.drugDataList = new ArrayList<>();
    }
    public void addDrugData(DrugData drug) {
        drugDataList.add(drug);
        fireTableRowsInserted(drugDataList.size() - 1, drugDataList.size() - 1);
    }

    public void decrementQuantityByBatch(int batchNumber, int quantity){
        for(int i = 0; i < drugDataList.size();i++){
            if(drugDataList.get(i).getBatch_number() == batchNumber){
                int initialQuantiy = drugDataList.get(i).getQuantity();
                drugDataList.get(i).setQuantity(initialQuantiy - quantity);
            }
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return drugDataList.size();
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

        DrugData drug = drugDataList.get(rowIndex);

        switch (columnIndex) {
            case 0: return drug.getBatch_number();
            case 1: return drug.getVendor_name();
            case 2: return drug.getC_p();
            case 3: return drug.getBrand_name();
            case 4: return drug.getGeneric_name();
            case 5: return drug.getQuantity();
            case 6: return drug.getUnit_of_measure();
            case 7: return drug.getExpirationDateAsString();
            case 8: return drug.getPurchase_cost();
            case 9: return drug.getCostPunit();
            case 10: return drug.getSelling_price();
            case 11: return drug.getSuggested_price();
            case 12: return drug.getMargin();
            default: return null;
        }
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {

        switch (columnIndex) {
            case 0:
                return Integer.class;       // Batch #
            case 1:
                return String.class;        // Vendor Name
            case 2:
                return String.class;        // Product Origin
            case 3:
                return String.class;        // Brand Name
            case 4:
                return String.class;        // Generic Name
            case 5:
                return Integer.class;       // Quantity
            case 6:
                return String.class;        // Unit Of Measure
            case 7:
                return String.class;          // EXTREMELY IMPORTANT: Tell sorter it's a Date
            case 8:
                return Double.class;        // Purchase Cost
            case 9:
                return Double.class;        // Cost / UOM
            case 10:
                return Double.class;        // Selling Price
            case 11:
                return Double.class;
            case 12:
                return Double.class;
            default:
                return Object.class;
        }
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 1: // Vendor Name
            case 2: // C/P
            case 3: // Brand Name
            case 4: // Generic Name
            case 5: // Quantity
            case 6: // Unit of Measure
            case 7: // Exp Date
            case 8: // Purchase Cost
            case 9: // Cost/Unit
            case 10: // Selling Price
            case 11: // Suggested Selling Price
                return true;
            default:
                return false; // Batch # (0) and Margin (12) not editable
        }
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex >= 0 && rowIndex < drugDataList.size()) {
            DrugData drug = drugDataList.get(rowIndex);
            try {
                switch (columnIndex) {
                    case 1: // Vendor Name
                        drug.setVendor_name((String) aValue);
                        break;
                    case 2: // C/P (assuming String)
                        drug.setC_p((String) aValue);
                        break;
                    case 3: // Brand Name
                        drug.setBrand_name((String) aValue);
                        break;
                    case 4: // Generic Name
                        drug.setGeneric_name((String) aValue);
                        break;
                    case 5: // Quantity
                        int newQuantity = Integer.parseInt(aValue.toString());
                        if (newQuantity < 0) { // Disallow negative quantity
                            JOptionPane.showMessageDialog(null, "Quantity cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        drug.setQuantity(newQuantity);
                        break;
                    case 6: // Unit of Measure
                        drug.setUnit_of_measure((String) aValue);
                        break;
                    case 7: // Exp Date (String to Date conversion)
                        drug.setExp_date(DATE_FORMAT.parse(aValue.toString()));
                        break;
                    case 8: // Purchase Cost
                        drug.setPurchase_cost(Double.parseDouble(aValue.toString()));
                        break;
                    case 9: // Cost/Unit
                        drug.setCostPunit(Double.parseDouble(aValue.toString()));
                        break;
                    case 10: // Selling Price
                        drug.setSelling_price(Double.parseDouble(aValue.toString()));
                        break;
                    case 11: // Suggested Selling Price
                        drug.setSuggested_price(Double.parseDouble(aValue.toString()));
                        break;
                    default:
                        return; // Not an editable column
                }

                drug.calculateMargin();


                fireTableCellUpdated(rowIndex, columnIndex);

                if (columnIndex >= 8 && columnIndex <= 11) {
                    fireTableCellUpdated(rowIndex, 12);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number format for input. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Invalid date format for Expiration Date. Use MM/dd/yyyy.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassCastException e) {
                JOptionPane.showMessageDialog(null, "Invalid data type for input. Please check the value.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
