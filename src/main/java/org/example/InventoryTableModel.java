package org.example;

import javax.swing.table.AbstractTableModel;
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
            case 7: return drug.getExp_date();
            case 8: return drug.getPurchase_cost();
            case 9: return drug.getCostPunit();
            case 10: return drug.getSelling_price();
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
                return Date.class;          // EXTREMELY IMPORTANT: Tell sorter it's a Date
            case 8:
                return Double.class;        // Purchase Cost
            case 9:
                return Double.class;        // Cost / UOM
            case 10:
                return Double.class;       // Selling Price
            default:
                return Object.class;
        }
    }
}
